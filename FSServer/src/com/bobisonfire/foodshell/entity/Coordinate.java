package com.bobisonfire.foodshell.entity;

import java.io.Serializable;
import java.util.Locale;

/**
 * Класс, реализующий координаты локации или персонажа.
 */
public class Coordinate implements Comparable<Coordinate>, Serializable {
    private double x;
    private double y;
    private double z;

    public Coordinate(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate(String vector) {
        String[] temp = vector.split("[),(]");
        x = Double.parseDouble(temp[1]);
        y = Double.parseDouble(temp[2]);
        z = Double.parseDouble(temp[3]);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    /**
     * Изменение координаты на соответствующие величины.
     */
    public void move(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "(%.3f, %.3f, %.3f)", x, y, z);
    }

    @Override
    public int compareTo(Coordinate other) {
        return (int) (y - other.y);
    }
}
