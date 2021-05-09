package renderer;

import elements.Camera;
import primitives.Color;

import java.util.MissingResourceException;

public class Render {
    ImageWriter imageWriter;
    //Scene scene;
    Camera camera;
    RayTracerBase rayTracerBase;

    public void renderImage() {
        if (imageWriter == null || rayTracerBase.scene == null || camera == null || rayTracerBase == null)
            throw new MissingResourceException("not all the values are okay", "Render", "1");
        for (int i = 0; i < imageWriter.getNx(); ++i)
            for (int j = 0; j < imageWriter.getNy(); ++j)
                imageWriter.writePixel(i, j, rayTracerBase.traceRay(camera.constructRayThroughPixel((int) imageWriter.getNx(), (int) imageWriter.getNy(), j, i)));
    }

    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("imageWriter is null", "Render", "2");
        for (int k = 0; k < imageWriter.getNx(); k += 50)
            for (int t = 0; t < imageWriter.getNy(); ++t)
                imageWriter.writePixel(k, t, Color.BLACK);
        for (int k = 0; k < imageWriter.getNx(); k += 50)
            for (int t = 0; t < imageWriter.getNy(); ++t)
                imageWriter.writePixel(t, k, Color.BLACK);
    }

    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("imageWriter is null", "Render", "2");
        imageWriter.writeToImage();
    }

    //region setters
    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }
/*
    public Render setScene(Scene scene) {
        rayTracerBase.scene = scene;
        return this;
    }
*/
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }
    //endregion
}
