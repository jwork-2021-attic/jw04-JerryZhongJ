package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.anish.calabashbros.QuickSorter;
import com.anish.calabashbros.Goblin;
import com.anish.calabashbros.World;

import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    private World world;
    private Goblin[][] goblins;
    String[] sortSteps;

    public WorldScreen() {
        world = new World();

        goblins = new Goblin[8][8];
        List<Integer> row = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
        Collections.shuffle(row);
        List<Integer> column = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
        for (int i = 0; i < 8; i++) {
            Collections.shuffle(column);
            for (int j = 0; j < 8; j++) {
                int ii = row.get(i);
                int jj = row.get(j);
                goblins[i][j] = new Goblin(new Color(ii * 32, jj * 32, 128), ii * 8 + jj, world);
                world.put(goblins[i][j], 4 * i + 4, 3 * j + 2);
            }
        }

        QuickSorter<Goblin> b = new QuickSorter<>();
        b.load(flatten(goblins, 8));
        b.sort();

        sortSteps = this.parsePlan(b.getPlan());
    }

    private Goblin[] flatten(Goblin[][] goblins, int col_num) {
        Goblin[] arr = new Goblin[goblins.length * col_num];
        for (int i = 0; i < goblins.length; i++) {
            for (int j = 0; j < col_num; j++) {
                arr[i * col_num + j] = goblins[i][j];
            }
        }
        return arr;
    }
    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(Goblin[][] goblins, String step) {
        String[] couple = step.split("<->");
        getGoblinByRank(goblins, Integer.parseInt(couple[0])).swap(getGoblinByRank(goblins, Integer.parseInt(couple[1])));
    }

    private Goblin getGoblinByRank(Goblin[][] goblins, int rank) {
        for (Goblin[] row : goblins) {
            for (Goblin goblin : row) {
                if (goblin.getRank() == rank) {
                    return goblin;
                }
            }  
        }
        return null;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        if (i < this.sortSteps.length) {
            this.execute(goblins, sortSteps[i]);
            i++;
        }

        return this;
    }

}
