package app.bulatov.tyrecalc;

public class Wheel {
    private int tireWidth = 215; // Ширина шины (мм)
    private int tireHeight = 50; // Высота (профиль) шины в процентах от ширины (мм)
    private int rimDiam = 17;    // Димаетр диска (дюймы)
    private double jj = 7.0;     // Посадочная ширина диска (дюймы)
    private int et = 50;         // Вылет диска (мм)

    public void setTireWidth(int tireWidth) {
        this.tireWidth = tireWidth;
    }
    public void setTireHeight(int tireHeight) {
        this.tireHeight = tireHeight;
    }
    public void setRimDiam(int rimDiam) {
        this.rimDiam = rimDiam;
    }
    public void setJJ(double jj) {
        this.jj = jj;
    }
    public void setET(int et) {
        this.et = et;
    }

    public int getTireWidth() {
        return tireWidth;
    }
    public int getTireHeight() {
        return tireHeight;
    }
    public int getRimDiam() {
        return rimDiam;
    }
    public double getJJ() {
        return jj;
    }
    public int getET() {
        return et;
    }

    public int getWheelWidth() {
        int rimWidth = (int)Math.ceil(jj*25.4) + 20;
        return Math.max(tireWidth, rimWidth);
    }
    public double getRimDiamMM() {
        return rimDiam * 25.4;
    }
    public double getWheelDiamMM() {
        return tireWidth * tireHeight * 2d / 100 + getRimDiamMM();
    }
    public int getOutLineFromHub() {
        return (getWheelWidth() + getWheelWidth() % 2) / 2 - et;
    }
    public int getInLineFromHub() {
        return (getWheelWidth() + getWheelWidth() % 2) / 2 + et;
    }
    public int getMinTireWidthForJJ(){
        double jjInMM = jj * 25.4;
        int minTireWidth = (int)((jjInMM + 9.1)/10)*10 + 5;
        return minTireWidth;
    }
    public int getMaxTireWidthForJJ(){
        double jjInMM = jj * 25.4;
        int maxTireWidth = (int)((jjInMM + 49.9)/10)*10 + 5;
        return maxTireWidth;
    }

    public double getRealSpeed(double oldDiameter, int speedometerReads) {
        return Math.round(speedometerReads * 10 / oldDiameter * getWheelDiamMM()) / 10d;
    }
}
