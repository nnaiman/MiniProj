package geometries;

import primitives.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Triangle extends Polygon {
    public Triangle(Point3D p0, Point3D p1, Point3D p2) {
        super(p0, p1, p2);
        List<Point3D> v = new ArrayList<>();
        v.add(p0);
        v.add(p1);
        v.add(p2);
        double xmin = v.get(0).getX().getCoord();
        double ymin = v.get(0).getY().getCoord();
        double zmin = v.get(0).getZ().getCoord();
        double xmax = v.get(0).getX().getCoord();
        double ymax = v.get(0).getY().getCoord();
        double zmax = v.get(0).getZ().getCoord();

        for (int i = 1; i < v.size(); ++i) {
            if (xmin > v.get(i).getX().getCoord())
                xmin = v.get(i).getX().getCoord();
            if (ymin > v.get(i).getY().getCoord())
                ymin = v.get(i).getY().getCoord();
            if (zmin > v.get(i).getZ().getCoord())
                zmin = v.get(i).getZ().getCoord();

            if (xmax < v.get(i).getX().getCoord())
                xmax = v.get(i).getX().getCoord();
            if (ymax < v.get(i).getY().getCoord())
                ymax = v.get(i).getY().getCoord();
            if (zmax < v.get(i).getZ().getCoord())
                zmax = v.get(i).getZ().getCoord();
        }
        box = new Box(new Point3D(xmax, ymax, zmax), new Point3D(xmin, ymin, zmin));
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
