package elements;

import primitives.Color;

public class AmbientLight {
    public AmbientLight() {
        intensity=Color.BLACK;
    }

    public Color getIntensity() {
        return intensity;
    }

    Color intensity;
    public AmbientLight(Color Ia, double Ka) {
        intensity=Ia.scale(Ka);
    }
}
