//Assignment : Homework 1
//File name  : group15_hw1.zip
//Full names : Manideep Reddy Nukala, Sai Charan Reddy Vallapureddy
package com.mad.group15_hw1;

public class Drink {
    int size;
    Double alcoholSeekValue;

    public Drink(int size, Double alcoholSeekValue) {
        this.size = size;
        this.alcoholSeekValue = alcoholSeekValue;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Double getAlcoholSeekValue() {
        return alcoholSeekValue;
    }

    public void setAlcoholSeekValue(Double alcoholSeekValue) {
        this.alcoholSeekValue = alcoholSeekValue;
    }
}
