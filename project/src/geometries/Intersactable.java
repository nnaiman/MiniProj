package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 *
 */
public interface Intersactable {
    List<Point3D> findIntsersections(Ray ray);
}
