package geometries;

import primitives.*;

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

    public Box box;

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    private Material material = new Material();
}