package unitTests;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static junit.framework.TestCase.assertTrue;

class PlaneTest {

    @Test
    void getNormal() {
        Vector v=new Plane(new Point3D(1,5,3),new Point3D(6,4,3),new Point3D(1,0,0)).getNormal();
        Vector v1=new Vector(5,-1,0).crossProduct(new Vector(5,4,3)).normalize();
        assertTrue("get normal plane",v.equals(v1)||v.equals(v1.scale(-1)));
        assertTrue ("the normal is not normalize",v.length()==1);
    }
}