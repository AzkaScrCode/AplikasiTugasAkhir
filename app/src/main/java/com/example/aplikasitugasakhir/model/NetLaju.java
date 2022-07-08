package com.example.aplikasitugasakhir.model;

public class NetLaju {
    int Hari;
    float NetLaju;

    public NetLaju(){}

    public NetLaju(int hari, float netLaju) {
        Hari = hari;
        NetLaju = netLaju;
    }

    public int getHari() {
        return Hari;
    }

    public void setHari(int hari) {
        Hari = hari;
    }

    public float getNetLaju() {
        return NetLaju;
    }

    public void setNetLaju(Float netLaju) {
        NetLaju = netLaju;
    }
}
