package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight {
    private Vector direction;
    private double angle = 179 * Math.PI / 180;

    public SpotLight(Color intensity, Point3D point3D, Vector direction) {
        super(intensity, point3D);
        this.direction = direction.normalize();
    }

    public SpotLight setAngle(double angle) {
        this.angle = angle < 180 ? angle * Math.PI / 180 : 179;
        return this;
    }

    @Override
    public SpotLight setPosition(Point3D position) {
        super.setPosition(position);
        return this;
    }

    @Override
    public SpotLight setkC(double kC) {
        super.setkC(kC);
        return this;
    }

    @Override
    public SpotLight setkL(double kL) {
        super.setkL(kL);
        return this;
    }

    @Override
    public SpotLight setkQ(double kQ) {
        super.setkQ(kQ);
        return this;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double ang = direction.dotProduct(getL(p));
        return super.getIntensity(p).scale(ang > Math.cos(angle) && Math.acos(ang) > 0 && ang > 0 ? Math.max(0, Math.cos(Math.acos(ang) * (Math.PI / angle))) : 0);
    }
}
