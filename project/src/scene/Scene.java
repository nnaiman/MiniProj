package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight= new AmbientLight(Color.BLACK,0);
    public Geometries geometries;

    public Scene setLights(LightSource... lights) {
        this.lights = Arrays.asList(lights.clone());
        return this;
    }

    public List<LightSource> lights=new LinkedList<LightSource>();

    public Scene(String name) {
        this.name = name;
        geometries=new Geometries();
        background=Color.BLACK;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
