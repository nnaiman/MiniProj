package unitTests;

import elements.Camera;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;

public class ImageWriterTest {
    @Test
    void emptyGrid() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, 1, 0)).setVpSize(16, 10).setDistance(1);
        ImageWriter imageWriter = new ImageWriter("test", 800, 500);

        for (int i = 0; i < 800; ++i)
            for (int j = 0; j < 500; ++j)
                imageWriter.writePixel(i, j, new Color(50, 40, 200));
        for (int k = 0; k < 800; k += 50)
            for (int t = 0; t < 500; ++t)
                imageWriter.writePixel(k, t, Color.BLACK);
        for (int k = 0; k < 500; k += 50)
            for (int t = 0; t < 800; ++t)
                imageWriter.writePixel(t, k, Color.BLACK);
        imageWriter.writeToImage();
    }
}
