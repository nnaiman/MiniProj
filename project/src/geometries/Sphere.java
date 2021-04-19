package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static java.lang.Math.sqrt;

public class Sphere implements Geometry {
    Point3D center;
    double radius;

    public Sphere(Point3D p, double r) {
        center = p;
        radius = r;
    }

    @Override
    public Vector getNormal(Point3D p) {
        return p.subtract(center).normalized();
    }

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        Vector u = center.subtract(ray.getP0());
        double tm = ray.getDir().dotProduct(u);
        double d = sqrt(u.lengthSquared() - tm * tm);
        if (d > radius)
            return null;
        double th = sqrt(radius * radius - d * d);
        List<Point3D> l = null;
        l.add(ray.getP0().add(ray.getDir().scale(tm + th)));
        if (d != radius)
            l.add(ray.getP0().add(ray.getDir().scale(tm - th)));
        return l;
    }
}
