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
    public int getJJ() {return jj;}
    public int getET() {return et;}

    public int getWheelWidth() {
        int rimWidth = (int)Math.ceil(jj*254f/100) + 20;
        return Math.max(width, rimWidth);
    }
    public float getRimDiameterMM() {return R * 254f / 10;}
    public float getWheelDiameterMM() {return width * height * 2f / 100 + getRimDiameterMM();}
    public int getOutLine() {
        return (getWheelWidth() + getWheelWidth() % 2) / 2 - et;
    }
    public int getInLine() {
        return (getWheelWidth() + getWheelWidth() % 2) / 2 + et;
    }
    public float getSpeed60(float oldDiameter) {
        return Math.round(600 / oldDiameter * getWheelDiameterMM()) / 10f;
    }
    public float getSpeed90(float oldDiameter) {
        return Math.round(900 / oldDiameter * getWheelDiameterMM()) / 10f;
    }

    // return min tire width with the current rim width
    public int getMinWidth(){
        float guess = (jj/10f * 254 + 91 - 50)/100;
        return Math.round(guess)*10 + 5;
    }
    // return max tire width with the current rim width
    public int getMaxWidth(){
        float guess = (jj/10f * 254 + 499 - 50)/100;
        return Math.round(guess)*10 + 5;
    }

}
