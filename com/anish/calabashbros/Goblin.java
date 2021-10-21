package com.anish.calabashbros;

import java.awt.Color;

public class Goblin extends Creature implements Comparable<Goblin> {

    private int rank;

    public Goblin(Color color, int rank, World world) {
        super(color, (char) 2, world);
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    @Override
    public String toString() {
        return String.valueOf(this.rank);
    }

    @Override
    public int compareTo(Goblin o) {
        return Integer.valueOf(this.rank).compareTo(Integer.valueOf(o.rank));
    }

    public void swap(Goblin another) {
        int x = this.getX();
        int y = this.getY();
        this.moveTo(another.getX(), another.getY());
        another.moveTo(x, y);
    }

}
