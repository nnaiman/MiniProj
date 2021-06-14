package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

public abstract class Geometry implements Intersectable {
    public abstract Vector getNormal(Point3D p);

    /**
     * getter of emission
     *
     * @return
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter of emmission
     *
     * @param emission
     * @return
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    protected Color emission = Color.BLACK;

    public Geometry setBox(boolean box) {
        this.box = box;
        return this;
    }

    public boolean isBox() {
        return box;
    }

    private boolean box=false;

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    private Material material = new Material();
}