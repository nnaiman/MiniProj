/**
 *
 */
package unitTests;

import geometries.*;
import org.junit.Test;

import elements.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(150, 150).setDistance(1000);

        scene.geometries.add( //
                new Sphere(new Point3D(0, 0, -50), 50) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(new Point3D(0, 0, -50), 25) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setkL(0.0004).setkQ(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(2500, 2500).setDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(new Point3D(-950, -900, -1000), 400) //
                        .setEmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.5)),
                new Sphere(new Point3D(-950, -900, -1000), 200) //
                        .setEmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(1)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setkR(0.5)));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setkL(0.00001).setkQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60)), //
                new Sphere(new Point3D(60, 50, -50), 30) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void multiGeometries() {
        Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(150, 150) //
                .setDistance(1000);
        Geometry sphere1 = new Sphere(new Point3D(-10, -15, -50), 15) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5));
        Geometry sphere2 = new Sphere(new Point3D(10, -15, -50), 15) //
                .setEmission(new Color(java.awt.Color.RED)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5));
        Geometry sphere3 = new Sphere(new Point3D(-30, -15, -50), 15) //
                .setEmission(new Color(java.awt.Color.GREEN)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5));
        Geometry sphere4 = new Sphere(new Point3D(30, -15, -50), 15) //
                .setEmission(new Color(java.awt.Color.WHITE)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5));

        Geometry sphere5 = new Sphere(new Point3D(-20, 0, -50), 15) //
                .setEmission(new Color(java.awt.Color.YELLOW)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5));
        Geometry sphere6 = new Sphere(new Point3D(0, 0, -50), 15) //
                .setEmission(new Color(java.awt.Color.CYAN)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5));
        Geometry sphere7 = new Sphere(new Point3D(20, 0, -50), 15) //
                .setEmission(new Color(java.awt.Color.MAGENTA)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5));

        Geometry sphere8 = new Sphere(new Point3D(-43, 40, -50), 15) //
                .setEmission(new Color(java.awt.Color.MAGENTA)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry sphere9 = new Sphere(new Point3D(-41, 38, -10), 11.5).setEmission(Color.BLACK);
        Geometry poly1 = new Polygon(new Point3D(-33, 41, -30), new Point3D(-33, 36, -30), new Point3D(-26, 36, -30), new Point3D(-26, 41, -30));
        Geometry sphere10 = new Sphere(new Point3D(-11.5, 40, -50), 15) //
                .setEmission(new Color(java.awt.Color.MAGENTA)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry sphere11 = new Sphere(new Point3D(21.5, 40, -50), 15) //
                .setEmission(new Color(java.awt.Color.MAGENTA)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry poly2 = new Polygon(new Point3D(63, 55, -30), new Point3D(63, 25, -30), new Point3D(38, 25, -30), new Point3D(38, 55, -30))
                .setEmission(new Color(java.awt.Color.MAGENTA))
                .setMaterial(new Material().setkD(0.5).setkS(0));
        Geometry poly3 = new Polygon(new Point3D(65, 55, -20), new Point3D(65, 30, -20), new Point3D(42, 30, -20), new Point3D(42, 55, -20));
        Geometry plane1 = new Plane(new Point3D(0, -30, -100), new Vector(0, 1, 0.02))
                .setEmission(new Color(60,60,60)).setMaterial(new Material().setkD(0.5).setkS(0).setkR(1));

        scene.geometries.add(sphere1, sphere2, sphere3, sphere4, sphere5, sphere6, sphere7, sphere8, sphere9, poly1, sphere10, sphere11, poly2, poly3, plane1);
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));
        scene.lights.add(new SpotLight(new Color(45, 89, 63), new Point3D(0, 50, -1), new Vector(0, -1, -1)));
        scene.lights.add(new PointLight(new Color(0, 255, 0), new Point3D(0, 0, 10)));

        ImageWriter imageWriter = new ImageWriter("multiGeometries", 1000, 1000);
        Render render = new Render().setImageWriter(imageWriter).setCamera(camera1).setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.printGrid(50, new Color(20,36,54));
        render.writeToImage();
    }
}
