package com.example.navanee.homework01;

/**
 * Created by navanee on 02-09-2016.
 */
public class Drink {
    private int size;
    private int alcoholPerc;
    private Double alcoholConsumed;

    public int getAlcoholPerc() {
        return alcoholPerc;
    }

    public void setAlcoholPerc(int alcoholPerc) {
        this.alcoholPerc = alcoholPerc;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Drink(int size, int alcoholPerc) {
        this.size = size;
        this.alcoholPerc = alcoholPerc;
        alcoholConsumed = Double.valueOf((size * alcoholPerc) / 100.0);
        System.out.println(alcoholConsumed);
    }

    public Double getAlcoholConsumed() {
        return alcoholConsumed;
    }
}
