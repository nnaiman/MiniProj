package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

public class Box {
    public Point3D max;
    public Point3D min;
    public List<Box> boxes = new ArrayList<>();
    public Intersectable geometry = null;

    public Box(Point3D max, Point3D min, List<Box> boxes) {
        this.max = max;
        this.min = min;
        this.boxes = boxes;
    }


    public Box(Point3D max, Point3D min, Intersectable geometry) {
        this.max = max;
        this.min = min;
        this.geometry = geometry;
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
}