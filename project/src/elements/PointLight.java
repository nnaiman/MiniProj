package elements;

import primitives.*;

public class PointLight extends Light implements LightSource {


    public PointLight(Color intensity, Point3D point3D) {
        super(intensity);
        position=point3D;
    }

    public Point3D getPosition() {
        return position;
    }

    public double getkC() {
        return kC;
    }

    public double getkL() {
        return kL;
    }

    public double getkQ() {
        return kQ;
    }

     public PointLight setPosition(Point3D position) {
        this.position = position;
        return this;
    }

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    private Point3D position;
    double kC = 1, kL = 0, kQ = 0;

    @Override
    public Color getIntensity(Point3D p) {
        double d = p.distance(position);
        return getIntensity().scale(1 / (kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point3D p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point3D p) {
        return position.distance(p);
    }
}
