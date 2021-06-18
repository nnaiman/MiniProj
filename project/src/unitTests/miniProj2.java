package unitTests;

import elements.*;
import geometries.*;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.BasicRayTracer;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class miniProj2 {
    double EPSILON = 0.00001;

    @Test
    public void miniP() {
        Scene scene = new Scene("miniP");
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
                .setEmission(new Color(java.awt.Color.WHITE)).setMaterial(new Material().setkD(0.1).setkS(0).setkR(1));

        Geometry upTri = new Triangle(new Point3D(-20, -30, -100 + EPSILON), new Point3D(20, -30, -100 + EPSILON), new Point3D(0, 0, -1600 + EPSILON))
                .setEmission(new Color(java.awt.Color.BLUE).scale(100)).setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry downTri = new Triangle(new Point3D(-32, -15, -850 + EPSILON), new Point3D(32, -15, -850 + EPSILON), new Point3D(0, -33.5, 75 + EPSILON))
                .setEmission(new Color(java.awt.Color.BLUE).scale(100)).setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

        Geometry sq = new Polygon(new Point3D(-70, 77, -50), new Point3D(-49, 77, -50), new Point3D(-49, 56, -50), new Point3D(-70, 56, -50))
                .setEmission(new Color(java.awt.Color.CYAN)).setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry tri1 = new Triangle(new Point3D(-63, 77, -50 + EPSILON), new Point3D(-56, 77, -50 + EPSILON), new Point3D(-56, 66.5, -50 + EPSILON))
                .setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));
        Geometry tri2 = new Triangle(new Point3D(-63, 56, -50 + EPSILON), new Point3D(-56, 56, -50 + EPSILON), new Point3D(-63, 66.5, -50 + EPSILON))
                .setEmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

        scene.geometries.add(plane1, upTri, downTri, sphere8, sphere9, sphere10, sphere11, poly1, poly2, poly3, sq, tri1, tri2);
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));
        scene.lights.add(new SpotLight(new Color(45, 89, 63), new Point3D(0, 50, -1), new Vector(0, -1, -1)));
        scene.lights.add(new PointLight(new Color(0, 255, 0), new Point3D(0, 0, 10)));

        ImageWriter imageWriter = new ImageWriter("miniP", 1000, 1000);
        Render render = new Render().setImageWriter(imageWriter).
                setCamera(camera1).setRayTracer(new BasicRayTracer(scene)).setMultithreading(3).setDebugPrint();
        render.renderImage();
        //render.printGrid(50, new Color(20,36,54));
        render.writeToImage();
    }

    @Test
    public void miniP2() {
        Scene scene=new Scene("miniP");
        Material material= new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5).setdP(5);
        Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(150, 150) //
                .setDistance(1000);
        Geometry sphere1 = new Sphere(new Point3D(-10, -15, -50), 15) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(material);
        Geometry sphere2 = new Sphere(new Point3D(10, -15, -50), 15) //
                .setEmission(new Color(java.awt.Color.RED)) //
                .setMaterial(material);
        Geometry sphere3 = new Sphere(new Point3D(-30, -15, -50), 15) //
                .setEmission(new Color(java.awt.Color.GREEN)) //
                .setMaterial(material);
        Geometry sphere4 = new Sphere(new Point3D(30, -15, -50), 15) //
                .setEmission(new Color(java.awt.Color.WHITE)) //
                .setMaterial(material);

        Geometry sphere5 = new Sphere(new Point3D(-20, 0, -50), 15) //
                .setEmission(new Color(java.awt.Color.YELLOW)) //
                .setMaterial(material);
        Geometry sphere6 = new Sphere(new Point3D(0, 0, -50), 15) //
                .setEmission(new Color(java.awt.Color.CYAN)) //
                .setMaterial(material);
        Geometry sphere7 = new Sphere(new Point3D(20, 0, -50), 15) //
                .setEmission(new Color(java.awt.Color.MAGENTA)) //
                .setMaterial(material);

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
                .setEmission(new Color(60,60,60)).setMaterial(new Material().setkD(0.5).setkS(0).setkR(1).setdP(5));

        scene.geometries.add(sphere1, sphere2, sphere3, sphere4, sphere5, sphere6, sphere7, sphere8, sphere9, poly1, sphere10, sphere11, poly2, poly3, plane1);
        scene.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));
        scene.lights.add(new SpotLight(new Color(45, 89, 63), new Point3D(0, 50, -1), new Vector(0, -1, -1)));
        scene.lights.add(new PointLight(new Color(0, 255, 0), new Point3D(0, 0, 10)));

        ImageWriter imageWriter = new ImageWriter("miniP2", 1000, 1000);
        Render render = new Render().setImageWriter(imageWriter).setMultithreading(3).
                setCamera(camera1).setRayTracer(new BasicRayTracer(scene).setGlossy(true)).setMultithreading(3).setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }
}
