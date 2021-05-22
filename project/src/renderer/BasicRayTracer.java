package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

public class BasicRayTracer extends RayTracerBase {
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     *  * whether the geopoint is unshaded or not
     * @param light
     * @param l the vector from light source to point
     * @param n the normal of geometry from point
     * @param geopoint
     * @return
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n); // refactored ray head move
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0 &&
                    gp.geometry.getMaterial().kT == 0)
                return false;
        }
        return true;
    }

    /**
     * calculate shadowing with refraction
     *
     * @param light    the light source
     * @param l        vector from light source
     * @param n        the normal of geometry
     * @param geopoint
     * @return
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        double lightDistance = light.getDistance(geopoint.point);
        var intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return 1.0;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().kT;
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint p = findClosestIntersection(ray);
        return p == null ? Color.BLACK : calcColor(p, ray);
    }

    private Color calcColor(GeoPoint p, Ray ray, int level, double k) {
        if (p == null)
            return scene.background;
        Color color = p.geometry.getEmission();
        color = color.add(calcLocalEffects(p, ray, k));
        return level == 1 ? color : color.add(calcGlobalEffects(p, ray, level, k));
    }

    private Color calcColor(GeoPoint p, Ray ray) {
        return calcColor(p, ray, MAX_CALC_COLOR_LEVEL, 1).add(scene.ambientLight.getIntensity());
    }

    /**
     * calc specular and defusive
     *
     * @param intersection
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD, ks = material.kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                double ktr = transparency(lightSource, l, n, intersection);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(lightIntensity.scale(calcDiffusive(kd, l, n) +
                            calcSpecular(ks, l, n, v, nShininess)));
                }
            }
        }
        return color;
    }

    private double calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess) {
        Vector r = getR(l, n);
        return ks * Math.pow(Math.max(0, -v.dotProduct(r)), nShininess);
    }

    private double calcDiffusive(double kd, Vector l, Vector n) {
        return kd * Math.abs(l.dotProduct(n));
    }

    /**
     * calculate reflection and refraction effects
     *
     * @param geopoint
     * @param ray      ray from camera
     * @param level    for stop recursive
     * @param k        for stop recursive
     * @return the color of reflection and refraction
     */
    private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Material material = geopoint.geometry.getMaterial();
        double kr = material.kR, kkr = k * kr;
        if (alignZero(kkr - MIN_CALC_COLOR_K) > 0) {
            Ray reflectedRay = constructReflectedRay(geopoint.geometry.getNormal(geopoint.point), geopoint.point, ray);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }
        double kt = material.kT, kkt = k * kt;
        if (alignZero(kkt - MIN_CALC_COLOR_K) > 0) {
            Ray refractedRay = constructRefractedRay(geopoint.geometry.getNormal(geopoint.point), geopoint.point, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                kt = kt;
            color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }
        return color;
    }

    private Ray constructReflectedRay(Vector n, Point3D p, Ray ray) {
        Vector r = getR(ray.getDir(), n);
        return new Ray(p, r, n);
    }

    private Ray constructRefractedRay(Vector n, Point3D p, Ray ray) {
        return new Ray(p, ray.getDir(), n);
    }

    /**
     * help function for calculate the vector of the specular and reflection
     *
     * @param l the in vector
     * @param n the normal
     * @return
     */
    private Vector getR(Vector l, Vector n) {
        return l.subtract(n.scale(2 * l.dotProduct(n))).normalize();
    }
}