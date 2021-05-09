package unitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import primitives.Point3D;

class Point3DTest {

    @Test
    void distance() {
        Assertions.assertEquals(10d,new Point3D(0,0,0).distance(new Point3D(0,10,0)));
    }
}