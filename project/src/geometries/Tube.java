package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube implements Geometry {
    Ray axisRay;
    double radius;

    public Tube(Ray axisRay, double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    @Override
    public Vector getNormal(Point3D p) {
        double t = axisRay.getDir().dotProduct(p.subtract(axisRay.getP0()));
        Point3D o;
        if (t!=0)
            o = axisRay.getDir().scale(t).getHead();
        else
            o=axisRay.getP0();
        if (axisRay.getP0()!=Point3D.ZERO)
             o = o.add(new Vector(axisRay.getP0()));
        return p.subtract(o).normalized();
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public List<Point3D> findIntsersections(Ray ray) {
        return null;
    }
}
