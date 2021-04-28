package unitTests;

import elements.Camera;
import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import java.util.List;

/**
 * @author neria nayman
 * integration tests for camera and geometries
 */
class CameraTestsIntegration {
    Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)).setVpSize(3, 3).setDistance(1);

    @Test
    void integrationWithSphere() {
        Sphere sphere = new Sphere(new Point3D(0, 0, -3), 1);
        tests("sphere 2 points", 2, sphere, camera);

        Camera camera1 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVpSize(3, 3).setDistance(1);
        sphere = new Sphere(new Point3D(0, 0, -2.5), 2.5);
        tests("VP in sphere", 18, sphere, camera1);

        sphere = new Sphere(new Point3D(0, 0, -2), 2);
        tests("only ten intersection points", 10, sphere, camera1);

        sphere = new Sphere(new Point3D(0, 0, -2), 4);
        tests("camera and VP in sphere", 9, sphere, camera1);

        sphere = new Sphere(new Point3D(0, 0, 1), 0.5);
        tests("VP up sphere", 0, sphere, camera1);
    }

    @Test
    void integrationWithPlane() {
        Plane p = new Plane(new Point3D(0, 0, -2), new Vector(0, 0, 1));
        tests("plane 9 points", 9, p, camera);

        Plane p1 = new Plane(new Point3D(0, 0, -2), new Vector(0, 3, 4));
        tests("plane 9 points", 9, p1, camera);

        Plane p2 = new Plane(new Point3D(0, 0, -2), new Vector(0, -4, 4));
        tests("plane 6 points", 6, p2, camera);
    }

    @Test
    void integrationWithTriangle() {
        Triangle triangle = new Triangle(new Point3D(0, 1, -2), new Point3D(-1, -1, -2), new Point3D(1, -1, -2));
        tests("triangle one point", 1, triangle, camera);

        triangle = new Triangle(new Point3D(0, 20, -2), new Point3D(-1, -1, -2), new Point3D(1, -1, -2));
        tests("triangle 2 points", 2, triangle, camera);
    }

    //help function
    void tests(String message, int count, Geometry g, Camera c) {
        int count1 = 0;
        List<Point3D> l;
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j) {
                l = g.findIntersections(c.constructRayThroughPixel(3, 3, j, i));
                count1 += l != null ? l.size() : 0;
            }
        Assert.assertEquals(message, count, count1);
    }
}