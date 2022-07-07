package com.example.aplikasitugasakhir;

public class WetNormalDry {
    private long hari;
    private double wet;
    private double normal;
    private double dry;

    public WetNormalDry() {
    }

    public WetNormalDry(long hari, double wet, double normal, double dry) {
        this.hari = hari;
        this.wet = wet;
        this.normal = normal;
        this.dry = dry;
    }

    public long getHari() {
        return hari;
    }

    public void setHari(long hari) {
        this.hari = hari;
    }

    public double getWet() {
        return wet;
    }

    public void setWet(double wet) {
        this.wet = wet;
    }

    public double getNormal() {
        return normal;
    }

    public void setNormal(double normal) {
        this.normal = normal;
    }

    public double getDry() {
        return dry;
    }

    public void setDry(double dry) {
        this.dry = dry;
    }
}
