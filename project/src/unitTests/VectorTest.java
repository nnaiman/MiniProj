package unitTests;

import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @org.junit.jupiter.api.Test
    void add() {
        assertTrue(new Vector(1, 0, 0).add(new Vector(0, 1, 0)).equals(new Vector(1, 1, 0)), "1,0,0 and 0,1,0");
        assertTrue (new Vector(1, 0, 0).add(new Vector(0, 0, 1)).equals(new Vector(1, 0, 1)),"1,0,0 and 0,0,1");
        assertTrue (new Vector(1, 0, 0).add(new Vector(1, 0, 0)).equals(new Vector(2, 0, 0)),"1,0,0 and 1,0,0");
        assertTrue (new Vector(1, 0, 0).add(new Vector(0, -1, 0)).equals(new Vector(1, -1, 0)),"1,0,0 and 0,-1,0");
        assertTrue (new Vector(0, -1, 0).add(new Vector(-1, 0, 0)).equals(new Vector(-1, -1, 0)),"0,-1,0 and -1,0,0");
    }

    @org.junit.jupiter.api.Test
    void subtract() {
        assertTrue (new Vector(1, 0, 0).subtract(new Vector(0, 1, 0)).equals(new Vector(1, -1, 0)),"1,0,0 and 0,1,0");
        assertTrue (new Vector(1, 0, 0).subtract(new Vector(0, 0, 1)).equals(new Vector(1, 0, -1)),"1,0,0 and 0,0,1");
        try {
            assertTrue (new Vector(1, 0, 0).subtract(new Vector(1, 0, 0)).equals(new Vector(0, 0, 0)),"1,0,0 and 1,0,0");

        } catch (IllegalArgumentException e) {
        }
        assertTrue(new Vector(1, 0, 0).subtract(new Vector(0, -1, 0)).equals(new Vector(1, 1, 0)),"1,0,0 and 0,-1,0");
        assertTrue (new Vector(0, -1, 0).subtract(new Vector(-1, 0, 0)).equals(new Vector(1, -1, 0)),"0,-1,0 and -1,0,0");
    }

    @org.junit.jupiter.api.Test
    void scale() {
        try {
            new Vector(40, 567, 32).scale(0);
            fail("failed scale 0");
        } catch (IllegalArgumentException e) {
        }
        assertTrue (new Vector(4, 1, 8).scale(3).equals(new Vector(12, 3, 24)),"4,1,8   3");
        assertTrue (new Vector(4, 1, 8).scale(-3).equals(new Vector(-12, -3, -24)),"4,1,8   -3");
    }

    @org.junit.jupiter.api.Test
    void dotProduct() {
        if (new Vector(1, 0, 0).dotProduct(new Vector(0, 1, 0)) != 0)
            fail("dot product 0");
        if (new Vector(1, 2, 4).dotProduct(new Vector(-4, -1, 0)) != -6)
            fail("dot product");
    }

    @org.junit.jupiter.api.Test
    void crossProduct() {
        Vector v1 = new Vector(4, 8, 2);
        Vector v2 = new Vector(1, -9, 5);
        try {
            new Vector(1, 0, 0).crossProduct(new Vector(1, 0, 0));
            fail("crossproduct 0");
        } catch (Exception e) {
        }
        if (v1.crossProduct(v2).dotProduct(v1) != 0 && v1.crossProduct(v2).dotProduct(v2) != 0)
            fail("not orthogonal");
    }

    @org.junit.jupiter.api.Test
    void lengthSquared() {
    }

    @org.junit.jupiter.api.Test
    void length() {
    }

    @org.junit.jupiter.api.Test
    void normalize() {
    }

    @org.junit.jupiter.api.Test
    void normalized() {
    }
}