package unitTests;

import geometries.Plane;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
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

    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: point doesn't on plane
        assertTrue("point doesn't on plane and there is intersection",new Plane(new Point3D(0,0,0),new Vector(new Point3D(0,0,1))).findIntersections(new Ray(new Point3D(2,3,4),new Vector(0,0,-1))).get(0).equals(new Point3D(2,3,0)));
        //TC02 point doesn't on plane and there is no intersection
        assertTrue("point doesn't on planeand no intersection",new Plane(new Point3D(0,0,0),new Vector(new Point3D(0,0,1))).findIntersections(new Ray(new Point3D(2,3,4),new Vector(1,0,0)))==null);

        // =============== Boundary Values Tests ==================
        //TC03: point on plane
        assertTrue("point on plane",new Plane(new Point3D(0,0,0),new Vector(new Point3D(0,0,1))).findIntersections(new Ray(new Point3D(1,1,0),new Vector(0,0,1)))==null);
    }
}