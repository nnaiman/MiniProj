package unitTests;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
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
    Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVpSize(150, 150) //
            .setDistance(1000);
    Geometry sphere1 = new Sphere(new Point3D(-10, -15, -50), 15) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkT(0.5));

    @Test
    public void multiGeometries() {
        Scene scene = new Scene("miniP");
        Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVpSize(150, 150) //
                .setDistance(1000);
        Geometry sphere1 = new Sphere(new Point3D(-50, -8, -50), 15) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkR(0.5));
        Geometry sphere2 = new Sphere(new Point3D(10, -8, -50), 15) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkR(0.5));
        Geometry sphere3 = new Sphere(new Point3D(-30, -8, -50), 15) //
                .setEmission(new Color(java.awt.Color.RED)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkR(0.5));
        Geometry sphere4 = new Sphere(new Point3D(50, -8, -50), 15) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkR(0.5));

        Geometry sphere5 = new Sphere(new Point3D(-20, -10, -50), 15) //
                .setEmission(new Color(java.awt.Color.YELLOW)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkR(0.5));
        Geometry sphere6 = new Sphere(new Point3D(0, -10, -50), 15) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkR(0.5));
        Geometry sphere7 = new Sphere(new Point3D(20, -10, -50), 15) //
                .setEmission(new Color(java.awt.Color.BLUE)) //
                .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100).setkR(0.5));

        Geometry plane1 = new Plane(new Point3D(0, -30, -100), new Vector(0, 1, 0.02))
                .setEmission(new Color(java.awt.Color.BLUE)).setMaterial(new Material().setkD(0.5).setkS(0).setkT(0));

        scene.background = new Color(225,200,50);
        scene.geometries.add(plane1,sphere1,sphere2,sphere3,sphere4,sphere5,sphere6,sphere7);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.ORANGE), 0.000005));
        //scene.lights.add(new DirectionalLight(new Color(java.awt.Color.ORANGE), new Vector(1, 1, -1)));
        //scene.lights.add(new SpotLight(new Color(45, 89, 63), new Point3D(0, 50, -1), new Vector(0, -1, -1)));
        //scene.lights.add(new PointLight(new Color(0, 255, 0), new Point3D(0, 0, 10)));

        ImageWriter imageWriter = new ImageWriter("miniP2", 1000, 1000);
        Render render = new Render().setImageWriter(imageWriter).
                setCamera(camera1).setRayTracer(new BasicRayTracer(scene)).setMultithreading(3).setDebugPrint();
        render.renderImage();
        //render.printGrid(50, new Color(20,36,54));
        render.writeToImage();
    }
}
