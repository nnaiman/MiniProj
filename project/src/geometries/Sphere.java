package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.*;

import static java.lang.Math.sqrt;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
    public List<Point3D> findIntersections(Ray ray) {
        Vector u=null;
        try {
            u = center.subtract(ray.getP0());
        }
        catch (Exception e){
            List<Point3D>l=new ArrayList<>();
            l.add(center.add(ray.getDir().scale(radius)));
            return l;
        }
        double tm = ray.getDir().dotProduct(u);
        double d = sqrt(u.lengthSquared() - tm * tm);
        if (alignZero(d - radius) >= 0)
            return null;
        double th = sqrt(radius * radius - d * d);
        if (alignZero(tm - th) <= 0 && alignZero(tm + th) <= 0)
            return null;
        List<Point3D> l = new ArrayList<>();
        if (!isZero(tm + th))
            l.add(ray.getPoint(tm + th));
        if (alignZero(tm - th) > 0)
            l.add(ray.getPoint(tm - th));
        return l;
    }
}
