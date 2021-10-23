package com.anish.calabashbros;

import java.awt.Color;

public class Floor extends Thing {

    public Floor(World world) {
        super(Color.gray, (char) 250, world);
    }

    public Floor(Color color, World world) {
        super(color, (char) 250, world);
    }

}
