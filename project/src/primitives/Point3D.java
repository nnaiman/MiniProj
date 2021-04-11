package primitives;

public class Point3D {
    public static final Point3D ZERO = new Point3D(0, 0, 0);
    Coordinate x;
    Coordinate y;
    Coordinate z;

    //region ctors
    public Point3D(double n0, double n1, double n2) {
        x = new Coordinate(n0);
        y = new Coordinate(n1);
        z = new Coordinate(n2);
    }

    public Point3D(Coordinate x1, Coordinate y1, Coordinate z1) {
        x = x1;
        y = y1;
        z = z1;
    }
    //endregion

    public Point3D add(Vector v) {
        return new Point3D(new Coordinate(x.coord + v.head.x.coord), new Coordinate(y.coord + v.head.y.coord), new Coordinate(z.coord + v.head.z.coord));
    }

    public Vector subtract(Point3D p) {
        return new Vector(new Coordinate(x.coord - p.x.coord), new Coordinate(y.coord - p.y.coord), new Coordinate(z.coord - p.z.coord));
    }

    public Coordinate getX() {
        return x;
    }

    public Coordinate getY() {
        return y;
    }

    public Coordinate getZ() {
        return z;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) && y.equals(point3D.y) && z.equals(point3D.z);
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}