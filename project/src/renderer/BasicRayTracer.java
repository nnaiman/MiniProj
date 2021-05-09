package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class BasicRayTracer extends RayTracerBase {
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> l = scene.geometries.findIntersections(ray);
        if (l == null || l.size() == 0)
            return scene.background;
        return calcColor(ray.findClosestPoint(l));
    }

    private Color calcColor(Point3D p) {
        return scene.ambientLight.getIntensity();
    }
}
