package com.example.pemantaualatsiramtanah;

public class booleanChart {
    boolean nitrogen;
    boolean psfor;
    boolean kalium;
    boolean kelembapan;

    public boolean isNitrogen() {
        return nitrogen;
    }

    public void setNitrogen(boolean nitrogen) {
        this.nitrogen = nitrogen;
    }

    public boolean isPsfor() {
        return psfor;
    }

    public void setPsfor(boolean psfor) {
        this.psfor = psfor;
    }

    public boolean isKalium() {
        return kalium;
    }

    public void setKalium(boolean kalium) {
        this.kalium = kalium;
    }

    public boolean isKelembapan() {
        return kelembapan;
    }

    public void setKelembapan(boolean kelembapan) {
        this.kelembapan = kelembapan;
    }

    public booleanChart( boolean value) {
        this.nitrogen = value;
        this.psfor = value;
        this.kalium = value;
        this.kelembapan = value;
    }

    public booleanChart(boolean nitrogen, boolean psfor, boolean kalium, boolean kelembapan) {
        this.nitrogen = nitrogen;
        this.psfor = psfor;
        this.kalium = kalium;
        this.kelembapan = kelembapan;
    }
}
