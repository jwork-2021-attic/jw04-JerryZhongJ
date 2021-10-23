package com.anish.calabashbros;

import java.lang.System.Logger.Level;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

public class Navigation {
    public enum Direction {
        LEFT, RIGHT, UP, DOWN, NOWHERE;
    }

    private World maze;
    private int endX;
    private int endY;
    LinkedList<Direction> plan;

    public Navigation(World maze, int endX, int endY) {
        this.maze = maze;
        this.endX = endX;
        this.endY = endY;
        plan = new LinkedList<>();
    }

    public void startNavigation(){
        dfs(0, 0, Direction.NOWHERE);
    }

    public boolean dfs(int x, int y, Direction from) {
        if(isEnd(x, y))
            return true;
        for (Direction d : getDirections(x, y)) {
            switch (d) {
            case DOWN:
                if (from != Direction.DOWN && checkDown(x, y)) {
                    plan.addLast(Direction.DOWN);
                    if (dfs(x, y + 1, Direction.UP))
                        return true;
                    plan.addLast(Direction.UP);
                }
                break;
            case UP:
                if (from != Direction.UP && checkUp(x, y)) {
                    plan.addLast(Direction.UP);
                    if (dfs(x, y - 1, Direction.DOWN))
                        return true;
                    plan.addLast(Direction.DOWN);
                }
                break;
            case LEFT:
                if (from != Direction.LEFT && checkLeft(x, y)) {
                    plan.addLast(Direction.LEFT);
                    if (dfs(x - 1, y, Direction.RIGHT))
                        return true;
                    plan.addLast(Direction.RIGHT);
                }
                break;
            case RIGHT:
                if (from != Direction.RIGHT && checkRight(x, y)) {
                    plan.addLast(Direction.RIGHT);
                    if (dfs(x + 1, y, Direction.LEFT))
                        return true;
                    plan.addLast(Direction.LEFT);
                }
                break;
            }
        }
        
        return false;
    }

    public LinkedList<Direction> getPlan() {
        return plan;
    }

    private boolean checkLeft(int x, int y) {
        return x - 1 >= 0 &&  maze.get(x - 1, y) instanceof Floor;
    }

    private boolean checkRight(int x, int y) {
        return x + 1 < World.WIDTH && maze.get(x + 1, y) instanceof Floor;
    }

    private boolean checkUp(int x, int y) {
        return y - 1 >= 0 && maze.get(x, y - 1) instanceof Floor;
    }

    private boolean checkDown(int x, int y) {
        return y + 1 < World.HEIGHT && maze.get(x, y + 1) instanceof Floor;
    }

    private Direction[] getDirections(int x, int y){
        Direction[] directions = new Direction[]{Direction.LEFT, Direction.RIGHT, Direction.UP, Direction.DOWN};
        int[] dis = new int[]{Math.abs(x-1-endX) + Math.abs(y-endY), 
                              Math.abs(x+1-endX) + Math.abs(y-endY), 
                              Math.abs(x-endX) + Math.abs(y-1-endY), 
                Math.abs(x - endX) + Math.abs(y + 1 - endY) };
                              
        for (int i = 0; i < 3; i++) {
            for (int j = i; j < 3; j++) {
                if (dis[j] > dis[j + 1]) {
                    int tmp = dis[j];
                    dis[j] = dis[j + 1];
                    dis[j + 1] = tmp;
                    Direction dmp = directions[j];
                    directions[j] = directions[j + 1];
                    directions[j + 1] = dmp;
                }
            }
        }
        return directions;
    }
    private boolean isEnd(int x, int y){
        return x + 1 == endX && y == endY ||
               x - 1 == endX && y == endY ||
               x == endX && y + 1 == endY ||
                x == endX && y - 1 == endY;

    }
}

