package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends Geometry {
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
        return normal;
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

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,double d) {
        if (ray.getP0().equals(q0) || isZero(normal.dotProduct(ray.getDir())) || isZero(normal.dotProduct(ray.getP0().subtract(q0))))
            return null;
        double t = alignZero(normal.dotProduct(q0.subtract(ray.getP0())) / normal.dotProduct(ray.getDir()));
        if (t == 0 || t < 0)
            return null;
        List<GeoPoint> l = new ArrayList<>();
        if (alignZero(t-d)<=0)
            l.add(new GeoPoint(this, ray.getPoint(t)));
        return l;
    }
}
