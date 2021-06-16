package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Sphere extends Geometry {
    Point3D center;
    double radius;

    public Sphere(Point3D p, double r) {
        center = p;
        radius = r;
        box = new Box(new Point3D(center.getX().getCoord() + radius, center.getY().getCoord() + radius, center.getZ().getCoord() + radius),
                new Point3D(center.getX().getCoord() - radius, center.getY().getCoord() - radius, center.getZ().getCoord() - radius));
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
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Vector u = null;
        try {
            u = center.subtract(ray.getP0());
        } catch (Exception e) {
            List<GeoPoint> l = new ArrayList<>();
            l.add(new GeoPoint(this, center.add(ray.getDir().scale(radius))));
            return l;
        }
        double tm = ray.getDir().dotProduct(u);
        double d = sqrt(u.lengthSquared() - tm * tm);
        if (alignZero(d - radius) >= 0)
            return null;
        double th = sqrt(radius * radius - d * d);
        if (alignZero(tm - th) <= 0 && alignZero(tm + th) <= 0)
            return null;
        List<GeoPoint> l = new ArrayList<>();
        if (!isZero(tm + th) && alignZero(tm + th - maxDistance) <= 0)
            l.add(new GeoPoint(this, ray.getPoint(tm + th)));
        if (alignZero(tm - th) > 0 && alignZero(tm - th - maxDistance) <= 0)
            l.add(new GeoPoint(this, ray.getPoint(tm - th)));
        return l;
    }
}
