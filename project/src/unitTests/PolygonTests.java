package unitTests; /**
 * 
 */

import geometries.Polygon;
import org.junit.Assert;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

    /**
     * Test method for
     * {@link geometries.Polygon#Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (IllegalArgumentException e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

        // TC12: Colocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (IllegalArgumentException e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        Assert.assertTrue("Bad normal to trinagle",new Vector(sqrt3, sqrt3, sqrt3).equals(pl.getNormal(new Point3D(0, 0, 1)))||new Vector(sqrt3, sqrt3, sqrt3).equals(pl.getNormal(new Point3D(0, 0, 1)).scale(-1)));
    }
    @Test
    public void testFindIntersections(){
        // ============ Equivalence Partitions Tests ==============
        //TC01: point doesn't on the plane and no intersection
        assertTrue("point doesn't on the plane",new Polygon(new Point3D(1,0,0),new Point3D(0,0,0),new Point3D(0,1,0),new Point3D(1,1,0)).findIntersections(new Ray(new Point3D(0,0,1),new Vector(1,0,0)))==null);
        //TC02: point doesn't on the plane and there is inrtersection
        assertTrue("point doesn't on the plane and there is intersection",new Polygon(new Point3D(1,0,0),new Point3D(0,0,0),new Point3D(0,1,0),new Point3D(1,1,0)).findIntersections(new Ray(new Point3D(0,0,1),new Vector(0,0,1))).get(0).equals(Point3D.ZERO));

        // =============== Boundary Values Tests ==================
        //TC03: point on the plane
        assertTrue("point on the plane",new Polygon(new Point3D(1,0,0),new Point3D(0,0,0),new Point3D(0,1,0),new Point3D(1,1,0)).findIntersections(new Ray(new Point3D(2,2,0),new Vector(1,0,0)))==null);
        //TC04: point on the vertice
        assertTrue("point on the vertice",new Polygon(new Point3D(1,0,0),new Point3D(0,0,0),new Point3D(0,1,0),new Point3D(1,1,0)).findIntersections(new Ray(new Point3D(0,0,0),new Vector(1,0,0)))==null);
        //TC05: point on the edge
        assertTrue("point on the edge",new Polygon(new Point3D(1,0,0),new Point3D(0,0,0),new Point3D(0,1,0),new Point3D(1,1,0)).findIntersections(new Ray(new Point3D(0.5,0,0),new Vector(1,0,0)))==null);
        //TC06: point in polygon
        assertTrue("point in polygon",new Polygon(new Point3D(1,0,0),new Point3D(0,0,0),new Point3D(0,1,0),new Point3D(1,1,0)).findIntersections(new Ray(new Point3D(0.5,0.5,0),new Vector(1,0,0)))==null);
    }
}
