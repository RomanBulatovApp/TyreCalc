package app.bulatov.tyrecalc;

class Wheel {

    private int width = 215; ///< Ширина шины в мм
    private int height = 50; ///< Высота шины в процентах от ширины
    private int R = 17;      ///< Димаетр диска в дюймах
    private int jj = 70;     ///< Посадочная ширина диска в дюймах*10
    private int et = 50;     ///< Вылет в мм

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

    /**
     * Возвращает максимальную ширину диска
     *
     * Высчитывает посадочную диска в мм: переводит посадочную ширину в мм и добавляет толщину бортика (по 10мм на бортик)
     * Возвращает большую ширину
     */
    public int getWheelWidth() {
        int rimWidth = (int)Math.ceil(jj*254f/100) + 20;
        return Math.max(width, rimWidth);
    }

    /** Возвращает диаметр диска в мм */
    public float getRimDiameterMM() {return R * 254f / 10;}

    /** Возвращает внешний диаметр колеса */
    public float getWheelDiameterMM() {return width * height * 2f / 100 + getRimDiameterMM();}

    /** Возвращает расстояние от привелочной плоскости до внешней границы колеса */
    public int getOutLine() {
        return (getWheelWidth() + getWheelWidth() % 2) / 2 - et;
    }

    /** Возвращает расстояние от привелочной плоскости до внутренней границы колеса */
    public int getInLine() {
        return (getWheelWidth() + getWheelWidth() % 2) / 2 + et;
    }

    /**
     * Возвращает реальную скорость с данным колесом при показаниях спидометра 60км/ч
     * принимает внешний диаметр стандартного колеса в качестве аргумента
     * */
    public float getSpeed60(float oldDiameter) {
        return Math.round(600 / oldDiameter * getWheelDiameterMM()) / 10f;
    }

    /**
     * Возвращает реальную скорость с данным колесом при показаниях спидометра 90км/ч
     * принимает внешний диаметр стандартного колеса в качестве аргумента
     * */
    public float getSpeed90(float oldDiameter) {
        return Math.round(900 / oldDiameter * getWheelDiameterMM()) / 10f;
    }

    /** Возвращает минимально допустимую ширину шины при данной ширине диска */
    public int getMinWidth(){
        float guess = (jj/10f * 254 + 91 - 50)/100;
        return Math.round(guess)*10 + 5;
    }

    /** Возвращает минимально допустимую ширину шины при данной ширине диска */
    public int getMaxWidth(){
        float guess = (jj/10f * 254 + 499 - 50)/100;
        return Math.round(guess)*10 + 5;
    }

}
