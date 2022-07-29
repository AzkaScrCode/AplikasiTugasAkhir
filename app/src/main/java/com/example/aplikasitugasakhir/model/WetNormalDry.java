package com.example.aplikasitugasakhir.model;

public class WetNormalDry {
    private long hari;
    private String hariString;
    private double wet;
    private double normal;
    private double dry;
    private double waterReq;
    private double wetBalance;
    private double normalBalance;
    private double dryBalance;
    private String wetStatus;
    private String normalStatus;
    private String dryStatus;

    public WetNormalDry() {
    }

    public WetNormalDry(long hari, double wet, double normal, double dry) {
        this.hari = hari;
        this.wet = wet;
        this.normal = normal;
        this.dry = dry;
    }

    public WetNormalDry(String hariString, double wet, double normal, double dry) {
        this.hariString = hariString;
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

    public double getWaterReq() {
        return waterReq;
    }

    public void setWaterReq(double waterReq) {
        this.waterReq = waterReq;
    }

    public double getWetBalance() {
        return wetBalance;
    }

    public void setWetBalance(double wetBalance) {
        this.wetBalance = wetBalance;
    }

    public double getNormalBalance() {
        return normalBalance;
    }

    public void setNormalBalance(double normalBalance) {
        this.normalBalance = normalBalance;
    }

    public double getDryBalance() {
        return dryBalance;
    }

    public void setDryBalance(double dryBalance) {
        this.dryBalance = dryBalance;
    }

    public String getWetStatus() {
        return wetStatus;
    }

    public void setWetStatus(String wetStatus) {
        this.wetStatus = wetStatus;
    }

    public String getNormalStatus() {
        return normalStatus;
    }

    public void setNormalStatus(String normalStatus) {
        this.normalStatus = normalStatus;
    }

    public String getDryStatus() {
        return dryStatus;
    }

    public void setDryStatus(String dryStatus) {
        this.dryStatus = dryStatus;
    }
}
