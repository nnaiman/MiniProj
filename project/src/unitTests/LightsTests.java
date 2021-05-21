package unitTests;

import elements.*;
import geometries.Geometry;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    private Scene scene1 = new Scene("Test scene");
    private Scene scene2 = new Scene("Test scene") //
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
    private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVpSize(150, 150) //
            .setDistance(1000);
    private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVpSize(200, 200) //
            .setDistance(1000);

    private static Geometry triangle1 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(75, 75, -150));
    private static Geometry triangle2 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(-70, 70, -50), new Point3D(75, 75, -150));
    private static Geometry sphere = new Sphere(new Point3D(0, 0, -50), 50) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(100));

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new BasicRayTracer(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(new Color(500, 300, 0), new Point3D(-50, -50, 50))//
                .setkL(0.00001).setkQ(0.000001));

        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new BasicRayTracer(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2)) //
                .setkL(0.00001).setkQ(0.00000001));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new BasicRayTracer(scene1));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)), //
                triangle2.setMaterial(new Material().setkD(0.8).setkS(0.2).setnShininess(300)));
        scene2.lights.add(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */

    @Test
    public void trianglesPoint() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)), //
                triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
        scene2.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)) //
                .setkL(0.0005).setkQ(0.0005));
        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */

    @Test
    public void trianglesSpot() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
                triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
        scene2.lights.add(new SpotLight(new Color(500, 250, 250), new Point3D(10, -10, -130), new Vector(-2, -2, -1)) //
                .setkL(0.0001).setkQ(0.000005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void sphereMultiLights() {
        scene1.geometries.add(sphere);
        SpotLight spotLight = new SpotLight(new Color(500, 250, 250), new Point3D(160, -10, 50), new Vector(-2, -2, -1)).setkL(0.0001).setkQ(0.000005);
        PointLight pointLight = new PointLight(new Color(500, 250, 250), new Point3D(-50, -10, 50)).setkL(0.0005).setkQ(0.0005);
        DirectionalLight directionalLight = new DirectionalLight(new Color(500, 300, 0), new Vector(1, -3, -1));
        AmbientLight ambientLight = new AmbientLight(new Color(java.awt.Color.WHITE), 0.15);
        scene1.setLights(spotLight, pointLight, directionalLight).setAmbientLight(ambientLight).setBackground(new Color(54, 89, 168));
        ImageWriter imageWriter = new ImageWriter("multiLightsSphere", 500, 500);
        Render render = new Render().setImageWriter(imageWriter).setCamera(camera1).setRayTracer(new BasicRayTracer(scene1));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void trianglesMultiLights() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
                triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
        SpotLight spotLight = new SpotLight(new Color(500, 250, 250), new Point3D(10, -10, -130), new Vector(-2, -2, -1)).setkL(0.0001).setkQ(0.000005);
        PointLight pointLight = new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)).setkL(0.0005).setkQ(0.0005);
        DirectionalLight directionalLight = new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1));
        AmbientLight ambientLight = new AmbientLight(new Color(79, 78, 97), 0.15);
        scene2.setLights(spotLight, pointLight, directionalLight).setAmbientLight(ambientLight).setBackground(new Color(154, 150, 168));
        ImageWriter imageWriter = new ImageWriter("multiLightsTriangles", 500, 500);
        Render render = new Render().setImageWriter(imageWriter).setCamera(camera2).setRayTracer(new BasicRayTracer(scene2));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void triangleAngleSpot() {
        scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
                triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
        scene2.lights.add(new SpotLight(new Color(500, 250, 250), new Point3D(10, -10, -130), new Vector(-2, -2, -1)) //
                .setkL(0.0001).setkQ(0.000005).setAngle(50));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesAngleSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2));
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void sphereAngleSpot() {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2)) //
                .setkL(0.00001).setkQ(0.00000001).setAngle(50));

        ImageWriter imageWriter = new ImageWriter("lightSphereAngleSpot", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new BasicRayTracer(scene1));
        render.renderImage();
        render.writeToImage();
    }
}
