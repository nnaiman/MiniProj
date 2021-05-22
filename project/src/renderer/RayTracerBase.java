package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;
    protected static final int MAX_CALC_COLOR_LEVEL = 10;
    protected static final double MIN_CALC_COLOR_K = 0.001;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    public abstract Color traceRay(Ray ray);
}
