package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {
    Point3D q0;
    Vector normal;

    //ctor
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        if (normal.length() != 1)
            normal.normalize();
        this.normal = new Vector(normal.getHead());
    }

    public Plane(Point3D p0, Point3D p1, Point3D p2) {
        q0 = p0;
        normal = p1.subtract(p0).crossProduct(p1.subtract(p2)).normalize();
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
    }

    public Vector getNormal() {
        return normal;
    }

    public Point3D getQ0() {
        return q0;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }
}
