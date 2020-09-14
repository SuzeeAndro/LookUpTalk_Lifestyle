package com.lookuptalk.model;

public class PastRateModel {

    public static final int TITLE_TYPE = 0;

    public PastRateModel(String date, String weight, String minimum, String maximum, int mType) {
        this.name = name;

    }

    public static final int DATA_TYPE = 1;
    private String name;

    public static int getTitleType() {
        return TITLE_TYPE;
    }

    public static int getDataType() {
        return DATA_TYPE;
    }

    public String getDate() {
        return name;
    }

    public void setDate(String date) {
        this.name = date;
    }



    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    private String minimum;
    private String maximum;
    private int mType;
}
