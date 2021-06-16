package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

import static primitives.Util.isZero;

public class Box {
    public Point3D max;
    public Point3D min;

    public Box(Point3D max, Point3D min, Intersectable geometry) {
        this.max = max;
        this.min = min;
        this.geometry = geometry;
        mid = new Point3D((max.getX().getCoord() + min.getX().getCoord()) / 2, (max.getY().getCoord() + min.getY().getCoord()) / 2, (max.getZ().getCoord() + min.getZ().getCoord()) / 2);
    }

    public Intersectable geometry;

    public Box(Point3D max, Point3D min, List<Box> boxes) {
        this.max = max;
        this.min = min;
        this.boxes = boxes;
        mid = mid = new Point3D((max.getX().getCoord() + min.getX().getCoord()) / 2, (max.getY().getCoord() + min.getY().getCoord()) / 2, (max.getZ().getCoord() + min.getZ().getCoord()) / 2);
    }

    List<Box> boxes;

    public Point3D getMid() {
        return mid;
    }

    public Point3D mid;

    public Box(Point3D max, Point3D min) {
        this.max = max;
        this.min = min;
        mid = new Point3D((max.getX().getCoord() + min.getX().getCoord()) / 2, (max.getY().getCoord() + min.getY().getCoord()) / 2, (max.getZ().getCoord() + min.getZ().getCoord()) / 2);
    }

    public Box(Box box) {
        max = box.max;
        min = box.min;
        mid = new Point3D((max.getX().getCoord() + min.getX().getCoord()) / 2, (max.getY().getCoord() + min.getY().getCoord()) / 2, (max.getZ().getCoord() + min.getZ().getCoord()) / 2);
    }


    public boolean hasIntersection(Ray ray) {
        Point3D vector = ray.getDir().getHead();
        Point3D point = ray.getP0();
        double xMax, yMax, zMax, xMin, yMin, zMin;

        if (isZero(vector.getX().getCoord())) {
            if (max.getX().getCoord() >= point.getX().getCoord() && min.getX().getCoord() <= point.getX().getCoord()) {
                xMax = Double.MAX_VALUE;
                xMin = Double.MIN_VALUE;
            } else
                return false;

        } else {
            double t1 = (max.getX().getCoord() - point.getX().getCoord()) / vector.getX().getCoord();
            double t2 = (min.getX().getCoord() - point.getX().getCoord()) / vector.getX().getCoord();
            if (t1 < t2) {
                xMin = t1;
                xMax = t2;
            } else {
                xMin = t2;
                xMax = t1;
            }
        }

        if (isZero(vector.getY().getCoord())) {
            if (max.getX().getCoord() >= point.getY().getCoord() && min.getY().getCoord() <= point.getY().getCoord()) {
                yMax = Double.MAX_VALUE;
                yMin = Double.MIN_VALUE;
            } else
                return false;

        } else {
            double t1 = (max.getY().getCoord() - point.getY().getCoord()) / vector.getY().getCoord();
            double t2 = (min.getY().getCoord() - point.getY().getCoord()) / vector.getY().getCoord();
            if (t1 < t2) {
                yMin = t1;
                yMax = t2;
            } else {
                yMin = t2;
                yMax = t1;
            }
        }

        if (isZero(vector.getZ().getCoord())) {
            if (max.getZ().getCoord() >= point.getZ().getCoord() && min.getZ().getCoord() <= point.getZ().getCoord()) {
                zMax = Double.MAX_VALUE;
                zMin = Double.MIN_VALUE;
            } else
                return false;

        } else {
            double t1 = (max.getZ().getCoord() - point.getZ().getCoord()) / vector.getZ().getCoord();
            double t2 = (min.getZ().getCoord() - point.getZ().getCoord()) / vector.getZ().getCoord();
            zMin = Math.min(t1, t2);
            zMax = Math.max(t1, t2);
        }

        return !(xMin > yMax || xMin > zMax ||
                yMin > xMax || yMin > zMax ||
                zMin > yMax || zMin > xMax);

    }

    public void add(Box boundingBox) {
        double xmin = min.getX().getCoord();
        double ymin = min.getY().getCoord();
        double zmin = min.getZ().getCoord();
        double xmax = max.getX().getCoord();
        double ymax = max.getY().getCoord();
        double zmax = max.getZ().getCoord();

        if (xmin > boundingBox.min.getX().getCoord())
            xmin = boundingBox.min.getX().getCoord();
        if (ymin > boundingBox.min.getY().getCoord())
            ymin = boundingBox.min.getY().getCoord();
        if (zmin > boundingBox.min.getZ().getCoord())
            zmin = boundingBox.min.getZ().getCoord();

        if (xmax < boundingBox.max.getX().getCoord())
            xmax = boundingBox.max.getX().getCoord();
        if (ymax < boundingBox.max.getY().getCoord())
            ymax = boundingBox.max.getY().getCoord();
        if (zmax < boundingBox.max.getZ().getCoord())
            zmax = boundingBox.max.getZ().getCoord();

        min = new Point3D(xmin, ymin, zmin);
        max = new Point3D(xmax, ymax, zmax);
    }
}