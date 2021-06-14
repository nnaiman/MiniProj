package primitives;

public class Material {
    /**
     * kD for diffusive, kS for specular
     */
    public double kD = 0;
    public double kS = 0;
    public static double wP = 1;//width for glossy and diffuse
    public static double hP = 1;//height for glossy and diffuse
    public double dP = 20;//distance for glossy and diffuse

    public Material setdP(double dP) {
        this.dP = dP;
        return this;
    }

    public Material setkT(double kT) {
        this.kT = kT;
        return this;
    }

    public Material setkR(double kR) {
        this.kR = kR;
        return this;
    }

    /**
     * kT for refraction, kR for reflection
     */
    public double kT = 0;
    public double kR = 0;

    public int nShininess = 0;

    public Material setkD(double kD) {
        this.kD = kD;
        return this;
    }

    public Material setkS(double kS) {
        this.kS = kS;
        return this;
    }

    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
