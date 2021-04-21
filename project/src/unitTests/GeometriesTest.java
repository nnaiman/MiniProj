package unitTests;

import geometries.Geometries;
import geometries.Polygon;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GeometriesTest {

    @Test
    void add() {
    }

    @Test
    void findIntersections() {
        Polygon p = new Polygon(new Point3D(-1, -1, 0), new Point3D(-1, 1, 0), new Point3D(1, 1, 0), new Point3D(1, -1, 0));
        Sphere s1 = new Sphere(new Point3D(0, 0, 0), 4);
        Sphere s2 = new Sphere(new Point3D(0, 0, 0), 3);
        // ============ Equivalence Partitions Tests ==============
        //TC01:
        assertTrue(new Geometries(p, s1, s2).findIntersections(new Ray(new Point3D(2, 2, -100), new Vector(0, 0, 1))).size() == 4, "some of shapes");
        // =============== Boundary Values Tests ==================
        //TC02: empty
        assertTrue(new Geometries().findIntersections(new Ray(Point3D.ZERO, new Vector(1, 0, 0))) == null, "empty");
        //TC03: no intersections
        assertTrue(new Geometries(p, s1).findIntersections(new Ray(new Point3D(100, 0, 0), new Vector(0, 0, 1))) == null, "no intersection");
        //TC04: only one shape
        List<Point3D> l = new Geometries(p, s1).findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(1, 0, 0)));
        assertTrue(l.size() == 1, "only one intersection");
        //TC05: all shapes
        l = new Geometries(p, s1, s2).findIntersections(new Ray(new Point3D(0, 0, -100), new Vector(0, 0, 1)));
        assertTrue(l.size() == 5, "all shapes");
    }
}