package unitTests;

import geometries.Sphere;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

class SphereTest {

    @Test
    void getNormal() {
        Assertions.assertTrue(new Sphere(Point3D.ZERO,4).getNormal(new Point3D(4,0,0)).equals(new Vector(1,0,0)),"get normal sphere");
    }
    @Test
    void findIntersections(Ray ray){
        Assert.assertTrue("out of sphere",new Sphere(Point3D.ZERO,3).findIntsersections(new Ray(new Point3D(8,0,0),new Vector(1,0,0))).size()==0);
        Assert.assertTrue("tangent to sphere",new Sphere(Point3D.ZERO,3).findIntsersections(new Ray(new Point3D(3,0,0),new Vector(0,1,0))).size()==1);
        Assert.assertTrue("crossed the sphere",new Sphere(Point3D.ZERO,3).findIntsersections(new Ray(new Point3D(4,0,0),new Vector(-1,0,0))).size()==2);
    }
}