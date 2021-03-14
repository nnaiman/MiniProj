package primitives;

public class Vector {
    Point3D head;

    public Vector add(Vector v) {
        return new Vector(head.add(v));
    }

    public Vector subtract(Vector v) {
        return new Vector(head.subtract(v.head).getHead());
    }

    public Vector(Coordinate c0, Coordinate c1, Coordinate c2) {
        head = new Point3D(c0, c1, c2);
        if (head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("this is point");
    }

    public Vector(double n0, double n1, double n2) {
        head = new Point3D(new Coordinate(n0), new Coordinate(n1), new Coordinate(n2));
        if (head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("this is point");
    }

    public Vector(Point3D head) {
        this.head = new Point3D(head.x, head.y, head.z);
        if (head.equals(Point3D.ZERO))
            throw new IllegalArgumentException("this is point");
    }

    public Vector scale(double s) {
        return new Vector(new Point3D(new Coordinate(head.x.coord * s), new Coordinate(head.y.coord * s), new Coordinate(head.z.coord * s)));
    }

    public double dotProduct(Vector v) {
        return head.x.coord * v.head.x.coord + head.y.coord * v.head.y.coord + head.z.coord * v.head.z.coord;
    }

    public Vector crossProduct(Vector v) {
        Vector v2=new Vector(new Point3D(
                new Coordinate(head.y.coord * v.head.z.coord - head.z.coord * v.head.y.coord),
                new Coordinate(head.z.coord * v.head.x.coord - head.x.coord * v.head.z.coord),
                new Coordinate(head.x.coord * v.head.y.coord - head.y.coord * v.head.x.coord)));
        if(v2.head.equals(Point3D.ZERO)){
            throw new IllegalArgumentException("the vectors is parallel");
        }
        return v2;
    }

    public double lengthSquared() {
        return (head.x.coord * head.x.coord) + (head.y.coord * head.y.coord) + (head.z.coord * head.z.coord);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        head = scale(1 / length()).head;
        return this;
    }

    public Vector normalized() {
        return new Vector(head).normalize();
    }

    public Point3D getHead() {
        return head;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Vector vector = (Vector) object;
        return head.equals(vector.head);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "head=" + head +
                '}';
    }
}