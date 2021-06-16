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

    public Geometries setBvh(boolean bvh) {
        this.bvh = bvh;
        return this;
    }

    private boolean bvh = true;

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(List<Intersectable> geometries) {
        this.geometries = geometries;
    }


    /**
     * create sub boxes according to this algorithm: https://www.haroldserrano.com/blog/visualizing-the-boundary-volume-hierarchy-collision-algorithm
     *
     * @param box
     * @param boxes
     */

    private void createSubBoxes(Box box, List<Box> boxes) {
        List<Box> boxesTmp = new ArrayList<>();
        if (box.boxes == null || box.boxes.size() <= 2)
            return;
        double x = box.max.getX().getCoord() - box.min.getX().getCoord();
        double y = box.max.getY().getCoord() - box.min.getY().getCoord();
        double z = box.max.getZ().getCoord() - box.min.getZ().getCoord();
        int edge = x > y && x > z ? 0 : (y > x && y > z ? 1 : 2);
        boxes.sort((Box xb, Box yb) -> {
            if (xb.mid.getCoord(edge) < yb.mid.getCoord(edge))
                return -1;
            if (xb.mid.getCoord(edge) > yb.mid.getCoord(edge))
                return 1;
            return 0;
        });
        int size = boxes.size() - 1;
        int i = 0;
        /*for (; i <= size && boxes.get(i).max.getCoord(edge) < (box.max.getCoord(edge)) / 2; ++i)
            ;*/
        i = size / 2;
        if (i == 0) {
            ++i;
            boxesTmp.add(getBox(boxes.subList(i, size + 1)));
        } else if (i == size + 1) {
            //--i;
            boxesTmp.add(getBox(boxes.subList(0, i)));
        } else {
            //--i;
            boxesTmp.add(getBox(boxes.subList(0, i)));
            //++i;
            boxesTmp.add(getBox(boxes.subList(i, size + 1)));
        }
        if (boxesTmp.get(0).boxes.size() > 2) {
            box.boxes = boxesTmp;
            createSubBoxes(boxesTmp.get(0), boxesTmp.get(0).boxes);
        }
        if (boxesTmp.size() > 1 && boxesTmp.get(1).boxes.size() > 2) {
            box.boxes = boxesTmp;
            createSubBoxes(boxesTmp.get(1), boxesTmp.get(1).boxes);
        }
    }

    private List<GeoPoint> hasIntersections(Ray ray, Box box, double d) {
        List<GeoPoint> l = new ArrayList<>();
        if (!box.hasIntersection(ray))
            return l;
        if (box.geometry != null) {
            var tmp = box.geometry.findGeoIntersections(ray, d);
            if (tmp != null)
                l.addAll(tmp);
        } else {
            for (Box box1 : box.boxes) {
                l.addAll(hasIntersections(ray, box1, d));
            }
        }
        return l;
    }

    public Box getBox(List<Box> boxes1) {
        Box box1;
        double xmin = (boxes1.get(0).min).getX().getCoord();
        double ymin = (boxes1.get(0).min).getY().getCoord();
        double zmin = (boxes1.get(0).min).getZ().getCoord();
        double xmax = (boxes1.get(0).max).getX().getCoord();
        double ymax = (boxes1.get(0).max).getY().getCoord();
        double zmax = (boxes1.get(0).max).getZ().getCoord();

        if (boxes1.size() > 1) {
            for (int i = 1; i < boxes1.size(); ++i) {
                if (xmin > (boxes1.get(i).min).getX().getCoord())
                    xmin = (boxes1.get(i).min).getX().getCoord();
                if (ymin > (boxes1.get(i).min).getY().getCoord())
                    ymin = (boxes1.get(i).min).getY().getCoord();
                if (zmin > (boxes1.get(i).min).getZ().getCoord())
                    zmin = (boxes1.get(i).min).getZ().getCoord();

                if (xmax < (boxes1.get(i).max).getX().getCoord())
                    xmax = (boxes1.get(i).max).getX().getCoord();
                if (ymax < (boxes1.get(i).max).getY().getCoord())
                    ymax = (boxes1.get(i).max).getY().getCoord();
                if (zmax < (boxes1.get(i).max).getZ().getCoord())
                    zmax = (boxes1.get(i).max).getZ().getCoord();
            }
        }
        box1 = new Box(new Point3D(xmax, ymax, zmax), new Point3D(xmin, ymin, zmin), boxes1);
        return box1;
    }

    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries.clone()));
        if (!bvh)
            return;
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
        box = getBox(boxes);
        createSubBoxes(box, boxes);
    }

    public List<GeoPoint> findGeoIntersections(Ray ray, double d) {
        if (geometries.size() == 0)
            return null;
        List<GeoPoint> l = new ArrayList<>();
        List<GeoPoint> ltmp = new ArrayList<>();
        if (!bvh) {
            for (int i = 0; i < boxes.size(); ++i) {
                if (bvh ? boxes.get(i).hasIntersection(ray) : true)
                    ltmp = boxes.get(i).geometry.findGeoIntersections(ray, d);
                if (ltmp != null)
                    l.addAll(ltmp);
            }
        } else {
            l.addAll(hasIntersections(ray, box, d));
        }
        return l.size() > 0 ? l : null;
    }
}