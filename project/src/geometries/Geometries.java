package geometries;

import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geometries implements Intersectable {
    public Geometries() {
        geometries = new ArrayList<>();
    }

    private List<Intersectable> geometries;

    public Geometries(Intersectable... geometries) {
        this.geometries = Arrays.asList(geometries.clone());
    }

    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries.clone()));
    }

    public List<GeoPoint> findGeoIntersections(Ray ray, double d) {
        if (geometries.size() == 0)
            return null;
        List<GeoPoint> l = new ArrayList<>();
        List<GeoPoint> ltmp = new ArrayList<>();
        for (int i = 0; i < geometries.size(); ++i) {
            ltmp = geometries.get(i).findGeoIntersections(ray, d);
            if (ltmp != null)
                l.addAll(ltmp);
        }
        return l.size() > 0 ? l : null;
    }
}
