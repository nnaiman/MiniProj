package elements;

import primitives.Color;

/**
 * @author neria
 */
abstract class Light {
    public Color getIntensity() {
        return intensity;
    }

    private Color intensity;

    protected Light(Color intensity) {
        this.intensity = intensity;
    }
}
