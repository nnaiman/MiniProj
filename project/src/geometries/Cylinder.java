package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube {
    double height;

    //ctor
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point3D p) {
        if (p.equals(axisRay.getP0()))
            return (axisRay.getDir().scale(-1)).normalized();
        if (axisRay.getDir().dotProduct(p.subtract(axisRay.getP0())) > 0 && axisRay.getDir().dotProduct(p.subtract(axisRay.getP0())) < height)
            return super.getNormal(p);
        else if (axisRay.getDir().dotProduct(p.subtract(axisRay.getP0())) == 0)
            return (axisRay.getDir().scale(-1)).normalized();
        else
            return (axisRay.getDir()).normalized();
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
}
