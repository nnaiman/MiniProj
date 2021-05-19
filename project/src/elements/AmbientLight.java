package elements;

import primitives.Color;

public class AmbientLight extends Light{
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }

    public AmbientLight() {
        super(Color.BLACK);
    }
}
