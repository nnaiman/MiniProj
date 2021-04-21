package unitTests;

import geometries.Sphere;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

class SphereTest {

    @Test
    void getNormal() {
        Assertions.assertTrue(new Sphere(Point3D.ZERO, 4).getNormal(new Point3D(4, 0, 0)).equals(new Vector(1, 0, 0)), "get normal sphere");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull("Ray's line out of sphere",
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(3, 1, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getX().getCoord() > result.get(1).getX().getCoord())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);

        // TC03: Ray starts inside the sphere (1 point)
        assertTrue("Ray starts inside the sphere", sphere.findIntersections(new Ray(new Point3D(1.5, 0, 0), new Vector(1, 0, 0))).get(0).equals(new Point3D(2, 0, 0)));
        // TC04: Ray starts after the sphere (0 points)
        assertTrue("Ray starts after the sphere", sphere.findIntersections(new Ray(new Point3D(2.5, 0, 0), new Vector(1, 0, 0))) == null);

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        List<Point3D> l = sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(-1, 0, 1)));
        assertTrue("Ray starts at sphere", l.get(0).equals(new Point3D(1, 0, 1)) && l.size() == 1);
        // TC12: Ray starts at sphere and goes outside (0 points)
        l = sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, -1)));
        assertTrue("Ray starts at sphere", l == null);
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        l = sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(-1, 0, 0)));
        assertTrue("ray start before and goes through the center" + l, l.size() == 2 && (l.get(0).equals(new Point3D(2, 0, 0)) && l.get(1).equals(Point3D.ZERO) || l.get(0).equals(Point3D.ZERO) && l.get(1).equals(new Point3D(2, 0, 0))));
        // TC14: Ray starts at sphere and goes inside (1 points)
        l = sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(-1, 0, 0)));
        assertTrue("ray start at and goes through the center" + l, l.size() == 1 && l.get(0).equals(Point3D.ZERO));
        // TC15: Ray starts inside (1 points)
        l = sphere.findIntersections(new Ray(new Point3D(1.5, 0, 0), new Vector(-1, 0, 0)));
        assertTrue("ray start inside and goes through the center" + l, l.size() == 1 && l.get(0).equals(Point3D.ZERO));
        // TC16: Ray starts at the center (1 points)
        l=sphere.findIntersections(new Ray(new Point3D(1,0,0),new Vector(-1,0,0)));
        assertTrue("ray start at the center and goes through the center"+l,l.size()==1&&l.get(0).equals(Point3D.ZERO));
        // TC17: Ray starts at sphere and goes outside (0 points)
        l=sphere.findIntersections(new Ray(new Point3D(2,0,0),new Vector(1,0,0)));
        assertTrue("ray start at and goes outside"+l,l==null);
        // TC18: Ray starts after sphere (0 points)
        l=sphere.findIntersections(new Ray(new Point3D(3,0,0),new Vector(1,0,0)));
        assertTrue("ray start after"+l,l==null);

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        l=sphere.findIntersections(new Ray(new Point3D(2,0,-1),new Vector(0,0,1)));
        assertTrue("ray start at and goes through the center"+l,l==null);
        // TC20: Ray starts at the tangent point
        l=sphere.findIntersections(new Ray(new Point3D(2,0,0),new Vector(0,0,1)));
        assertTrue("ray start at and goes through the center"+l,l==null);
        // TC21: Ray starts after the tangent point
        l=sphere.findIntersections(new Ray(new Point3D(2,0,1),new Vector(0,0,1)));
        assertTrue("ray start at and goes through the center"+l,l==null);

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        l=sphere.findIntersections(new Ray(new Point3D(3,0,0),new Vector(0,0,1)));
        assertTrue("ray out of sphere"+l,l==null);
    }
}