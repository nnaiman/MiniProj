package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

public class Ray {
    Point3D p0;
    Vector dir;

    //ctor
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        if (dir.length() != 1)
            dir.normalize();
        this.dir = new Vector(dir.head);
    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    public Point3D getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    public Point3D findClosestPoint(List<Point3D> l) {
        if (l.size() == 0)
            return null;
        Point3D tmp = l.get(0);
        for (int i = 1; i < l.size(); ++i) {
            if (alignZero(l.get(i).distance(p0)) < tmp.distance(p0))
                tmp = l.get(i);
        }
        return tmp;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> l){
        if (l.size() == 0)
            return null;
        GeoPoint tmp = l.get(0);
        for (int i = 1; i < l.size(); ++i) {
            if (alignZero(l.get(i).point.distance(p0)) < tmp.point.distance(p0))
                tmp = l.get(i);
        }
        return tmp;
    }
    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}
