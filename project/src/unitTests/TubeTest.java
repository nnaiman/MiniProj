package unitTests;

import geometries.Tube;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

class TubeTest {

    @Test
    void getNormal() {
        Assert.assertTrue("get normal tube",new Tube(new Ray(Point3D.ZERO,new Vector(0,0,1)),6).getNormal(new Point3D(0,6,50)).equals(new Vector(0,1,0)));
        Assert.assertTrue("get normal tube",new Tube(new Ray(Point3D.ZERO,new Vector(0,0,1)),6).getNormal(new Point3D(0,6,0)).equals(new Vector(0,1,0)));
    }
}