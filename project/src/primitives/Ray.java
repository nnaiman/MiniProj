package primitives;

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
    public Point3D getPoint(double t){
        return p0.add(dir.scale(t));
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
