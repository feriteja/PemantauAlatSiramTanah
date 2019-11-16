package com.example.pemantaualatsiramtanah;


public class TempleteChart {

   long Time;
   int Kalium;
   int Kelembapan;
   int Nitrogen;
   int Phosphorous;

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    public int getKalium() {
        return Kalium;
    }

    public void setKalium(int kalium) {
        Kalium = kalium;
    }

    public int getKelembapan() {
        return Kelembapan;
    }

    public void setKelembapan(int kelembapan) {
        Kelembapan = kelembapan;
    }

    public int getNitrogen() {
        return Nitrogen;
    }

    public void setNitrogen(int nitrogen) {
        Nitrogen = nitrogen;
    }

    public int getPhosphorous() {
        return Phosphorous;
    }

    public void setPhosphorous(int phosphorous) {
        Phosphorous = phosphorous;
    }

    public TempleteChart() {
    }

    public TempleteChart(long time, int kalium, int kelembapan, int nitrogen, int phosphorous) {
        Time = time;
        Kalium = kalium;
        Kelembapan = kelembapan;
        Nitrogen = nitrogen;
        Phosphorous = phosphorous;
    }
}