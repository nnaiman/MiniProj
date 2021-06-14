package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public abstract class RayTracerBase {
    protected Scene scene;

    public abstract RayTracerBase setGlossy(boolean glossy);

    public abstract RayTracerBase setDiffuse(boolean diffuse);

    protected boolean glossy = false;
    protected boolean diffuse = false;
    protected static final int MAX_CALC_COLOR_LEVEL = 10;
    protected static final double MIN_CALC_COLOR_K = 0.001;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    public abstract Color traceRay(Ray ray);

    public abstract Color traceRay(List<Ray> rays);
}
