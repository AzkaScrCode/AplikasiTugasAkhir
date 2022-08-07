package com.example.aplikasitugasakhir.model;

public class WaterBalance {

    private long hari;
    private double wet;
    private double normal;
    private double dry;
    private String normalStatus;
    private String wetStatus;
    private String dryStatus;

    public WaterBalance() {
    }

    public WaterBalance(long hari, double wet, double normal, double dry) {
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

    public String getNormalStatus() {
        return normalStatus;
    }

    public void setNormalStatus(String normalStatus) {
        this.normalStatus = normalStatus;
    }

    public String getWetStatus() {
        return wetStatus;
    }

    public void setWetStatus(String wetStatus) {
        this.wetStatus = wetStatus;
    }

    public String getDryStatus() {
        return dryStatus;
    }

    public void setDryStatus(String dryStatus) {
        this.dryStatus = dryStatus;
    }
}
