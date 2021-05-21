package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

    public DirectionalLight(Color intensity,Vector direction) {
        super(intensity);
        this.direction=direction.normalize();
    }

    public Vector getDirection() {
        return direction;
    }

    private Vector direction;

    @Override
    public Color getIntensity(Point3D p) {
        return getIntensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return direction;
    }

    @Override
    public double getDistance(Point3D p) {
        return Double.POSITIVE_INFINITY;
    }
}
