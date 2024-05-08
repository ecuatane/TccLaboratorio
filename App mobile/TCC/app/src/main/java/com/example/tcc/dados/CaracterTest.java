package com.example.tcc.dados;

public class CaracterTest {

    private String caracter;
    private long downTime;
    private long upTime;


    public CaracterTest() {
    }

    public String getCaracter() {

        return caracter;
    }

    public void setCaracter(String caracter) {

        this.caracter = caracter;
    }

    public long getDownTime() {

        return downTime;
    }

    public void setDownTime(long downTime) {

        this.downTime = downTime;
    }

    public long getUpTime() {

        return upTime;
    }

    public void setUpTime(long upTime) {

        this.upTime = upTime;
    }



    @Override
    public String toString() {
        return  caracter
                + ", " + downTime
                + ", " + upTime
                + "\n";
    }
}
