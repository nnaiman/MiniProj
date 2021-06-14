package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geometries implements Intersectable {
    public Geometries() {
        geometries = new ArrayList<>();
    }

    private List<Intersectable> geometries;
    private List<Box> boxes = new ArrayList<>();
    public Box box;

    public Geometries(Intersectable... geometries) {
        this.geometries = Arrays.asList(geometries.clone());
    }

    public void add1(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries.clone()));
    }

    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries.clone()));
        for (Intersectable geometry : geometries) {
            if (geometry.getClass() == Sphere.class) {
                boxes.add(new Box(new Point3D(((Sphere) geometry).center.getX().getCoord() + ((Sphere) geometry).radius,
                        ((Sphere) geometry).center.getY().getCoord() + ((Sphere) geometry).radius,
                        ((Sphere) geometry).center.getZ().getCoord() + ((Sphere) geometry).radius),
                        new Point3D(((Sphere) geometry).center.getX().getCoord() - ((Sphere) geometry).radius,
                                ((Sphere) geometry).center.getY().getCoord() - ((Sphere) geometry).radius,
                                ((Sphere) geometry).center.getZ().getCoord() - ((Sphere) geometry).radius), geometry));
            } else if (geometry.getClass() == Polygon.class || geometry.getClass() == Triangle.class) {
                double xmin = ((Polygon) geometry).vertices.get(0).getX().getCoord();
                double ymin = ((Polygon) geometry).vertices.get(0).getY().getCoord();
                double zmin = ((Polygon) geometry).vertices.get(0).getZ().getCoord();
                double xmax = ((Polygon) geometry).vertices.get(0).getX().getCoord();
                double ymax = ((Polygon) geometry).vertices.get(0).getY().getCoord();
                double zmax = ((Polygon) geometry).vertices.get(0).getZ().getCoord();

                for (int i = 1; i < ((Polygon) geometry).vertices.size(); ++i) {
                    if (xmin > ((Polygon) geometry).vertices.get(i).getX().getCoord())
                        xmin = ((Polygon) geometry).vertices.get(i).getX().getCoord();
                    if (ymin > ((Polygon) geometry).vertices.get(i).getY().getCoord())
                        ymin = ((Polygon) geometry).vertices.get(i).getY().getCoord();
                    if (zmin > ((Polygon) geometry).vertices.get(i).getZ().getCoord())
                        zmin = ((Polygon) geometry).vertices.get(i).getZ().getCoord();

                    if (xmax < ((Polygon) geometry).vertices.get(i).getX().getCoord())
                        xmax = ((Polygon) geometry).vertices.get(i).getX().getCoord();
                    if (ymax < ((Polygon) geometry).vertices.get(i).getY().getCoord())
                        ymax = ((Polygon) geometry).vertices.get(i).getY().getCoord();
                    if (zmax < ((Polygon) geometry).vertices.get(i).getZ().getCoord())
                        zmax = ((Polygon) geometry).vertices.get(i).getZ().getCoord();
                }
                boxes.add(new Box(new Point3D(xmax, ymax, zmax), new Point3D(xmin, ymin, zmin), geometry));
            } else {
                boxes.add(new Box(new Point3D(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE),
                        new Point3D(Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE), geometry));
            }
        }
        double xmin = (boxes.get(0).min).getX().getCoord();
        double ymin = (boxes.get(0).min).getY().getCoord();
        double zmin = (boxes.get(0).min).getZ().getCoord();
        double xmax = (boxes.get(0).max).getX().getCoord();
        double ymax = (boxes.get(0).max).getY().getCoord();
        double zmax = (boxes.get(0).max).getZ().getCoord();

        for (int i = 1; i < boxes.size(); ++i) {
            if (xmin > (boxes.get(i).min).getX().getCoord())
                xmin = (boxes.get(i).min).getX().getCoord();
            if (ymin > (boxes.get(i).min).getY().getCoord())
                ymin = (boxes.get(i).min).getY().getCoord();
            if (zmin > (boxes.get(i).min).getZ().getCoord())
                zmin = (boxes.get(i).min).getZ().getCoord();

            if (xmax < (boxes.get(i).max).getX().getCoord())
                xmax = (boxes.get(i).max).getX().getCoord();
            if (ymax < (boxes.get(i).max).getY().getCoord())
                ymax = (boxes.get(i).max).getY().getCoord();
            if (zmax < (boxes.get(i).max).getZ().getCoord())
                zmax = (boxes.get(i).max).getZ().getCoord();
        }
        box = new Box(new Point3D(xmax, ymax, zmax), new Point3D(xmin, ymin, zmin), boxes);
    }

    public List<GeoPoint> findGeoIntersections(Ray ray, double d) {
        if (geometries.size() == 0)
            return null;
        if (!box.hasIntersection(ray))
            return null;
        List<GeoPoint> l = new ArrayList<>();
        List<GeoPoint> ltmp = new ArrayList<>();
        for (int i = 0; i < boxes.size(); ++i) {
            if (boxes.get(i).hasIntersection(ray))
                ltmp = boxes.get(i).geometry.findGeoIntersections(ray, d);
            if (ltmp != null)
                l.addAll(ltmp);
        }
        return l.size() > 0 ? l : null;
    }
}