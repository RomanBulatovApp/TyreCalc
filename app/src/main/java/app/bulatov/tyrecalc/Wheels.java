package app.bulatov.tyrecalc;

class Wheel {

    private int width = 215;
    private int height = 50;
    private int R = 17;
    private int jj = 70;
    private int et = 50;

    public void setWidth(String width) {
        this.width = Integer.parseInt(width);
    }
    public void setHeight(String height) {
        this.height = Integer.parseInt(height);
    }
    public void setR(String r) {
        this.R = Integer.parseInt(r);
    }
    public void setJJ(String jj) {
        this.jj = Math.round(Float.parseFloat(jj) * 10);
    }
    public void setET(String et) {
        this.et = Integer.parseInt(et);
    }

    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public float getRimDiameterMM() {return R * 254f / 10;}
    public int getJJ() {return jj;}
    public int getET() {return et;}
    public float getDiameterMM() {return width * height * 2f / 100 + getRimDiameterMM();}
    public int getOutLine() {return (width + 1) / 2 - et;}
    public int getInLine() {return (width + 1) / 2 + et;}
    public float getSpeed60(float oldDiameter) {
        return Math.round(600 / oldDiameter * getDiameterMM()) / 10f;
    }
    public float getSpeed90(float oldDiameter) {
        return Math.round(900 / oldDiameter * getDiameterMM()) / 10f;
    }

}
