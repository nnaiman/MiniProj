package geometries;

import primitives.Point3D;
import primitives.Vector;

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
}
