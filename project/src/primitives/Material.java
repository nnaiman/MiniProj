package primitives;

public class Material {
    /**
     * kD for diffusive, kS for specular
     */
    public double kD = 0;
    public double kS = 0;

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
