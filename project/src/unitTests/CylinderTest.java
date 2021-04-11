package unitTests;

import geometries.Cylinder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

class CylinderTest {

    @Test
    void getNormal() {
        Assert.assertTrue("get normal cylinder1",new Cylinder(new Ray(Point3D.ZERO,new Vector(0,0,1)),6,10).getNormal(new Point3D(0,6,5)).equals(new Vector(0,1,0)));
        Assert.assertTrue("get normal cylinder2",new Cylinder(new Ray(Point3D.ZERO,new Vector(0,0,1)),6,10).getNormal(new Point3D(0,5,10)).equals(new Vector(0,0,1)));
        Assert.assertTrue("get normal cylinder3",new Cylinder(new Ray(Point3D.ZERO,new Vector(0,0,1)),6,10).getNormal(new Point3D(0,5,0)).equals(new Vector(0,0,-1)));
        Assert.assertTrue("get normal cylinder4",new Cylinder(new Ray(Point3D.ZERO,new Vector(0,0,1)),6,10).getNormal(new Point3D(0,0,0)).equals(new Vector(0,0,-1)));
        Assert.assertTrue("get normal cylinder5",new Cylinder(new Ray(Point3D.ZERO,new Vector(0,0,1)),6,10).getNormal(new Point3D(0,0,10)).equals(new Vector(0,0,1)));
    }
}