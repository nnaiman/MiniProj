package unitTests;

import elements.*;
import geometries.*;
import org.junit.Test;
import org.xml.sax.SAXException;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;
import xml.XMLScene;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {
    private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setDistance(100) //
            .setVpSize(500, 500);

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
                // right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
        // right

        ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene) {
                });

        render.renderImage();
        render.printGrid(50, Color.BLACK);
        render.writeToImage();
    }

    /**
     * Test for XML based scene - for bonus
     */
    @Test
    public void basicRenderXml() throws IOException, SAXException, ParserConfigurationException {
        Scene scene = new Scene("XML Test scene");
        // enter XML file name and parse from XML file into scene object
        // ...
        XMLScene.XMLToScene(scene);

        ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    // For stage 6 - please disregard in stage 5

    /**
     * Produce a scene with basic 3D model - including individual lights of the bodies
     * and render it into a png image with a grid
     */
    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2)).setBackground(new Color(java.awt.Color.YELLOW)); //

        scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50) //
                        .setEmission(new Color(java.awt.Color.CYAN)), //
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)) // up left
                        .setEmission(new Color(java.awt.Color.GREEN)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)) // down left
                        .setEmission(new Color(java.awt.Color.RED)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100)) // down right
                        .setEmission(new Color(java.awt.Color.BLUE)));

        ImageWriter imageWriter = new ImageWriter("color render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.WHITE));
        render.writeToImage();
    }

    /**
     * create scene with DOF(depth of field) and without
     */
    @Test
    public void DOF() {
        Scene scene1 = new Scene("DOF");
        Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(150, 150).setDistance(1000);
        Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(150, 150).setDistance(1000).setdFP(50).sethFP(3).setwFP(3);
        Geometry sphere1 = new Sphere(new Point3D(10, -10, -100), 25) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry sphere2 = new Sphere(new Point3D(0, 30, -50), 25) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        scene1.geometries.add(sphere1, sphere2);
        scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));
        //scene1.geometries.buildHierarchy();
        ImageWriter imageWriter1 = new ImageWriter("withoutDOF", 500, 500);
        Render render1 = new Render()//
                .setImageWriter(imageWriter1) //
                .setCamera(camera1) //
                .setRayTracer(new BasicRayTracer(scene1));
        render1.renderImage();
        render1.writeToImage();

        ImageWriter imageWriter2 = new ImageWriter("withDOF", 500, 500);
        Render render2 = new Render()//
                .setImageWriter(imageWriter2) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene1)).setDepth(true).setMultithreading(3).setDebugPrint();
        render2.renderImage();
        render2.writeToImage();
    }

    @Test
    public void glossy() {
        Scene scene = new Scene("glossy");
        Camera camera1 = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
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

        ImageWriter imageWriter1 = new ImageWriter("withoutGlossy", 500, 500);
        Render render1 = new Render()//
                .setImageWriter(imageWriter1) //
                .setCamera(camera1).setMultithreading(3) //
                .setRayTracer(new BasicRayTracer(scene));
        render1.renderImage();
        render1.writeToImage();
        //scene.geometries.buildHierarchy();
        ImageWriter imageWriter2 = new ImageWriter("withGlossy", 500, 500);
        Render render2 = new Render()//
                .setImageWriter(imageWriter2) //
                .setCamera(camera1).setMultithreading(3).setDebugPrint() //
                .setRayTracer(new BasicRayTracer(scene).setGlossy(true));
        render2.renderImage();
        render2.writeToImage();
    }

    @Test
    public void diffuse() {
        Scene scene = new Scene("diffuse");
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(150, 150).setDistance(1000);

        scene.geometries.add( //
                new Sphere(new Point3D(0, 0, -50), 50) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)
                        ),
                new Sphere(new Point3D(0, 0, -50), 25) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setkL(0.0004).setkQ(0.0000006));

        ImageWriter imageWriter1 = new ImageWriter("withoutDiffuse", 500, 500);
        Render render1 = new Render()//
                .setImageWriter(imageWriter1) //
                .setCamera(camera).setMultithreading(3) //
                .setRayTracer(new BasicRayTracer(scene));
        render1.renderImage();
        render1.writeToImage();

        ImageWriter imageWriter2 = new ImageWriter("withDiffuse", 500, 500);
        Render render2 = new Render()//
                .setImageWriter(imageWriter2) //
                .setCamera(camera).setMultithreading(3) //
                .setRayTracer(new BasicRayTracer(scene).setDiffuse(true));
        render2.renderImage();
        render2.writeToImage();
    }

    @Test
    public void superSampling() {
        Scene scene = new Scene("super");
        scene.geometries.add(new Sphere(new Point3D(0, 0, -50), 250).setEmission(new Color(java.awt.Color.cyan)));
        Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)).setDistance(1000).setVpSize(500, 500);

        ImageWriter imageWriter1 = new ImageWriter("withoutSuperSampling", 500, 500);
        Render render1 = new Render()//
                .setImageWriter(imageWriter1) //
                .setCamera(camera1) //
                .setRayTracer(new BasicRayTracer(scene));
        render1.renderImage();
        render1.writeToImage();

        ImageWriter imageWriter2 = new ImageWriter("withSuperSampling", 500, 500);
        Render render2 = new Render()//
                .setImageWriter(imageWriter2) //
                .setCamera(camera1) //
                .setRayTracer(new BasicRayTracer(scene)).setSuperSampling(true);
        render2.renderImage();
        render2.writeToImage();
    }

    @Test
    public void multiGeometries2() {
        Scene scene = new Scene("multi2");
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
                .setEmission(new Color(60, 60, 60)).setMaterial(new Material().setkD(0.5).setkS(0).setkR(1));

        scene.geometries.add(sphere1, sphere2, sphere3, sphere4, sphere5, sphere6, sphere7, sphere8, sphere9, poly1, sphere10, sphere11, poly2, poly3, plane1);
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));
        scene.lights.add(new SpotLight(new Color(45, 89, 63), new Point3D(0, 50, -1), new Vector(0, -1, -1)));
        scene.lights.add(new PointLight(new Color(0, 255, 0), new Point3D(0, 0, 10)));
        ImageWriter imageWriter = new ImageWriter("multiGeometries2", 1000, 1000);
        Render render = new Render().setImageWriter(imageWriter).setCamera(camera1).
                setRayTracer(new BasicRayTracer(scene)).setSuperSampling(true).setMultithreading(1).setDebugPrint();
        render.renderImage();
        //render.printGrid(50, new Color(20,36,54));
        render.writeToImage();
    }
}
