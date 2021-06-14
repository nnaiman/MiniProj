package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class BasicRayTracer extends RayTracerBase {
    private Camera camera;

    @Override
    public RayTracerBase setGlossy(boolean glossy) {
        this.glossy = glossy;
        return this;
    }

    @Override
    public RayTracerBase setDiffuse(boolean diffuse) {
        this.diffuse = diffuse;
        return this;
    }

    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * * whether the geopoint is unshaded or not
     *
     * @param light
     * @param l        the vector from light source to point
     * @param n        the normal of geometry from point
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
        return p == null ? scene.background : calcColor(p, ray);
    }

    @Override
    public Color traceRay(List<Ray> rays) {
        Color color = Color.BLACK;
        Color tmp;
        for (Ray ray : rays) {
            tmp = traceRay(ray);
            color = color.add(tmp);
        }
        return color.scale(1d / rays.size());
    }

    private Color calcColor(GeoPoint p, Ray ray, int level, double k) {
        if (p == null)
            return scene.background;
        Color color = p.geometry.getEmission();
        color = color.add(calcLocalEffects(p, ray, k));
        return level == 1 ? color : color.add(calcGlobalEffects(p, ray, level, k));
    }

    /**
     * calc color for surfaces with glossy or/and diffuse
     *
     * @param rays
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(List<Ray> rays, int level, double k) {
        GeoPoint p;
        Color color = Color.BLACK;
        for (Ray ray : rays) {
            p = findClosestIntersection(ray);
            color = color.add(calcColor(p, ray, level, k));
        }
        return color.scale(1d / rays.size());
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
            if (!glossy || !scene.geometries.box.hasIntersection(reflectedRay))
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            else {
                //region create up and right for glossy surfaces
                double x, y, z;
                Point3D n = geopoint.geometry.getNormal(geopoint.point).getHead();
                Point3D t = reflectedRay.getDir().getHead();
                double a = n.getX().getCoord();
                double b = n.getY().getCoord();
                double c = n.getZ().getCoord();
                double d = t.getX().getCoord();
                double e = t.getY().getCoord();
                double f = t.getZ().getCoord();
                if (!isZero(a) && !isZero((-d * b / a) + e)) {
                    z = 1;
                    y = ((d * c / a) - f) / ((-d * b / a) + e);
                    x = (-b * y - c) / a;
                } else if (!isZero(b) && !isZero((-a * e / b) + d)) {
                    z = 1;
                    x = ((c * e / b) - f) / ((-a * e / b) + d);
                    y = (-a * x - c) / b;
                } else {
                    z = 0;
                    y = 1;
                    x = -e / d;
                }
                Vector rtmp = new Vector(x, y, z).normalize();
                Vector u = reflectedRay.getDir().crossProduct(rtmp).normalize();
                camera = new Camera(geopoint.point, reflectedRay.getDir(), u.dotProduct(new Vector(n)) * new Vector(t).dotProduct(new Vector(n)) > 0 ? u : u.scale(-1));
                //endregion
                color = color.add(calcColor(camera.constructRays(reflectedRay, false, geopoint.geometry.getMaterial().wP, geopoint.geometry.getMaterial().hP, geopoint.geometry.getMaterial().dP), level - 1, kkr).scale(kr));
            }
        }
        double kt = material.kT, kkt = k * kt;
        if (alignZero(kkt - MIN_CALC_COLOR_K) > 0) {
            Ray refractedRay = constructRefractedRay(geopoint.geometry.getNormal(geopoint.point), geopoint.point, ray);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                kt = kt;
            if (!diffuse || !scene.geometries.box.hasIntersection(refractedRay))
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            else {
                //region create up and right for diffuse surfaces
                double x, y, z;
                Point3D n = geopoint.geometry.getNormal(geopoint.point).getHead();
                Point3D t = refractedRay.getDir().getHead();
                double a = n.getX().getCoord();
                double b = n.getY().getCoord();
                double c = n.getZ().getCoord();
                double d = t.getX().getCoord();
                double e = t.getY().getCoord();
                double f = t.getZ().getCoord();
                if (!isZero(a) && !isZero((-d * b / a) + e)) {
                    z = 1;
                    y = ((d * c / a) - f) / ((-d * b / a) + e);
                    x = (-b * y - c) / a;
                } else if (!isZero(b) && !isZero((-a * e / b) + d)) {
                    z = 1;
                    x = ((c * e / b) - f) / ((-a * e / b) + d);
                    y = (-a * x - c) / b;
                } else {
                    z = 0;
                    y = 1;
                    x = -e / d;
                }
                Vector rtmp = new Vector(x, y, z).normalize();
                Vector u = refractedRay.getDir().crossProduct(rtmp).normalize();
                camera = new Camera(geopoint.point, refractedRay.getDir(), u.dotProduct(new Vector(n)) * new Vector(t).dotProduct(new Vector(n)) > 0 ? u : u.scale(-1));
                //endregion
                color = color.add(calcColor(camera.constructRays(refractedRay, false, geopoint.geometry.getMaterial().wP, geopoint.geometry.getMaterial().hP, geopoint.geometry.getMaterial().dP), level - 1, kkt).scale(kt));
            }
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