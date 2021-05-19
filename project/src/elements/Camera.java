package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Camera {
    private Point3D p0;
    private Vector To;
    private Vector Up;
    private Vector Right;
    private double width;
    private double height;
    private double d;

    public Camera(Point3D p0, Vector to, Vector up) {
        this.p0 = p0;
        if (!isZero(to.dotProduct(up)))
            throw new IllegalArgumentException("to and up not orthogonal");
        To = to.normalize();
        Up = up.normalize();
        Right = to.crossProduct(Up).normalize();
    }

    //region getters
    public Point3D getP0() {
        return p0;
    }

    public Vector getTo() {
        return To;
    }

    public Vector getUp() {
        return Up;
    }

    public Vector getRight() {
        return Right;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    //endregion
    public Camera setVpSize(double width, double height) {
        if (!(alignZero(width) > 0) || !(alignZero(height) > 0))
            throw new IllegalArgumentException("width or height are negative");
        this.width = width;
        this.height = height;
        return this;
    }

    public Camera setDistance(double distance) {
        if (!(alignZero(distance) > 0))
            throw new IllegalArgumentException("distance is negative");
        d = distance;
        return this;
    }

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        if (nX == 0 || nY == 0)
            throw new IllegalArgumentException("is 0");
        Point3D Pc = p0.add(To.scale(d));
        double Ry = alignZero(height / nY);
        double Rx = alignZero(width / nX);
        double Xj = alignZero((j - (double) (nX - 1) / 2) * Rx);
        double Yi = alignZero(-(i - (double) (nY - 1) / 2) * Ry);
        Point3D Pij;
        if (Yi == 0 && Xj != 0)
            Pij = Pc.add(Right.scale(Xj));
        else if (Yi != 0 && Xj == 0)
            Pij = Pc.add(Up.scale(Yi));
        else if (Yi == 0 && Xj == 0)
            Pij = Pc;
        else
            Pij = Pc.add((Right.scale(Xj)).add(Up.scale(Yi)));
        return new Ray(new Point3D(p0.getX(), p0.getY(), p0.getZ()), new Vector(Pij.subtract(p0).getHead()));
    }

    public Camera setWidth(double width) {
        this.width = width;
        return this;
    }

    public Camera setHeight(double height) {
        this.height = height;
        return this;
    }

    public Camera setD(double d) {
        this.d = d;
        return this;
    }

    public Camera setP0(Point3D p0) {
        this.p0 = p0;
        return this;
    }
}
