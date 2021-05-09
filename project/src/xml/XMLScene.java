package xml;

import elements.AmbientLight;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;
import primitives.Color;
import primitives.Point3D;
import scene.Scene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XMLScene {

    public static Scene XMLToScene(Scene scene) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder loader = factory.newDocumentBuilder();
        Document document = loader.parse("src/xml/basicRenderTestTwoColors.xml");

        DocumentTraversal trav = (DocumentTraversal) document;

        NodeIterator it = trav.createNodeIterator(document.getDocumentElement(),
                NodeFilter.SHOW_ELEMENT, null, true);

        int c = 1;
        String[] v = it.nextNode().getAttributes().item(0).toString().split("\"")[1].split(" ");
        Color background = new Color(Integer.parseInt(v[0]), Integer.parseInt(v[1]), Integer.parseInt(v[2]));
        v = it.nextNode().getAttributes().item(0).toString().split("\"")[1].split(" ");
        AmbientLight ambientLight = new AmbientLight(new Color(Integer.parseInt(v[0]), Integer.parseInt(v[1]), Integer.parseInt(v[2])), 1);

        Geometries geometries = new Geometries();
        Node n = it.nextNode();
        for (Node node = it.nextNode(); node != null; node = it.nextNode()) {
            String name = node.getNodeName();
            switch (name) {
                case "sphere":
                    v = node.getAttributes().item(0).toString().split("\"")[1].split(" ");
                    geometries.add(new Sphere(new Point3D(Double.parseDouble(v[0]), Double.parseDouble(v[1]), Double.parseDouble(v[2])), Double.parseDouble(node.getAttributes().item(1).toString().split("\"")[1])));
                    break;
                case "triangle":
                    var t = node.getAttributes();
                    var p = t.item(0).toString().split("\"")[1].split(" ");
                    Point3D p0 = new Point3D(Double.parseDouble(p[0]), Double.parseDouble(p[1]), Double.parseDouble(p[2]));
                    p = t.item(1).toString().split("\"")[1].split(" ");
                    Point3D p1 = new Point3D(Double.parseDouble(p[0]), Double.parseDouble(p[1]), Double.parseDouble(p[2]));
                    p = t.item(2).toString().split("\"")[1].split(" ");
                    Point3D p2 = new Point3D(Double.parseDouble(p[0]), Double.parseDouble(p[1]), Double.parseDouble(p[2]));
                    geometries.add(new Triangle(p0, p1, p2));
                    break;
            }
        }
        return scene.setAmbientLight(ambientLight).setBackground(background).setGeometries(geometries);
    }
}