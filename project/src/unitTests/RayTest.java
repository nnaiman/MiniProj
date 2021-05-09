package unitTests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

class RayTest {

    @Test
    void findClosestPoint() {
        List<Point3D> l = new ArrayList<>();
        // ============ Equivalence Partitions Tests ==============
        //TC01: point in middle list
        l.add(new Point3D(2, 0, 0));
        l.add(new Point3D(0, 1, 0));
        l.add(new Point3D(0, 0, 3));
        Assert.assertTrue("point in middle list", new Ray(Point3D.ZERO, new Vector(1, 0, 0)).findClosestPoint(l).equals(l.get(1)));

        // =============== Boundary Values Tests ==================
        //TC02: first point
        l.clear();
        l.add(new Point3D(1, 0, 0));
        l.add(new Point3D(0, 2, 0));
        l.add(new Point3D(0, 0, 3));
        Assert.assertTrue("first point", new Ray(Point3D.ZERO, new Vector(1, 0, 0)).findClosestPoint(l).equals(l.get(0)));

        //TC03: last point
        l.clear();
        l.add(new Point3D(3, 0, 0));
        l.add(new Point3D(0, 2, 0));
        l.add(new Point3D(0, 0, 1));
        Assert.assertTrue("first point", new Ray(Point3D.ZERO, new Vector(1, 0, 0)).findClosestPoint(l).equals(l.get(2)));

        //TC04: empty list
        l.clear();
        Assert.assertTrue("first point", new Ray(Point3D.ZERO, new Vector(1, 0, 0)).findClosestPoint(l) == null);
    }
}