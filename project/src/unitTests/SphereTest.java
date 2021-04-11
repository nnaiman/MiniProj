package unitTests;

import geometries.Sphere;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void getNormal() {
        Assertions.assertTrue(new Sphere(Point3D.ZERO,4).getNormal(new Point3D(4,0,0)).equals(new Vector(1,0,0)),"get normal sphere");
    }
}