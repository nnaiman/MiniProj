package renderer;

import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

public class BasicRayTracer extends RayTracerBase {
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> l = scene.geometries.findGeoIntersections(ray);
        if (l == null || l.size() == 0)
            return scene.background;
        return calcColor(ray.findClosestGeoPoint(l), ray);
    }

    private Color calcColor(GeoPoint p, Ray ray) {
        return scene.ambientLight.getIntensity().add(p.geometry.getEmission(), calcLocalEffects(p, ray));
    }

    /**
     * calc specular and defusive
     *
     * @param intersection
     * @param ray
     * @return
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
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
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(lightIntensity.scale(calcDiffusive(kd, l, n) +
                        calcSpecular(ks, l, n, v, nShininess)));
            }
        }
        return color;
    }

    private double calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n)))).normalize();
        return ks * Math.pow(Math.max(0, -v.dotProduct(r)), nShininess);
    }

    private double calcDiffusive(double kd, Vector l, Vector n) {
        return kd * Math.abs(l.dotProduct(n));
    }
}
