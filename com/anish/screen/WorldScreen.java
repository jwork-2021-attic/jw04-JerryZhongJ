package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import com.anish.calabashbros.Boy;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.Floor;
import com.anish.calabashbros.Girl;
import com.anish.calabashbros.Navigation;
import com.anish.calabashbros.World;
import com.anish.calabashbros.Navigation.Direction;

import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    private Boy boy;
    private Girl girl;
    LinkedList<Direction> plan;

    public WorldScreen() {
        MazeGenerator mg = new MazeGenerator();
        world = mg.getMaze();
        boy = new Boy(world);
        girl = new Girl(world);

        Navigation nav = new Navigation(world, mg.getEndX(), mg.getEndY());
        nav.startNavigation();
        world.put(boy, 0, 0);
        world.put(girl, mg.getEndX(), mg.getEndY());

        plan = nav.getPlan();
        System.out.println(plan.size());
    }


    private void execute() {
        if(plan.isEmpty())
            return;

        int x = boy.getX();
        int y = boy.getY();
        switch (plan.pop()) {
        case UP:
            boy.moveTo(x, y - 1);
            break;
        case RIGHT:
            boy.moveTo(x + 1, y);
            break;
        case DOWN:
            boy.moveTo(x, y + 1);
            break;
        case LEFT:
            boy.moveTo(x - 1, y);
            break;
        }
        world.put(new Floor(AsciiPanel.brightGreen, world), x, y);
    }


    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }



    @Override
    public Screen respondToUserInput(KeyEvent key) {

        execute();
        return this;
    }

}
