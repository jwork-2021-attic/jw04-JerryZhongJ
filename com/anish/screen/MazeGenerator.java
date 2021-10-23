package com.anish.screen;

import java.util.ArrayList;
import java.util.Stack;

import com.anish.calabashbros.Floor;
import com.anish.calabashbros.Girl;
import com.anish.calabashbros.Wall;
import com.anish.calabashbros.World;

import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class MazeGenerator {
    class Node {
        int x;
        int y;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private Stack<Node> stack = new Stack<>();
    private Random rand = new Random();
    private World world;
    private int endX;
    private int endY;
    private int dimension;

    MazeGenerator() {
        world = new World();
        for (int i = 0; i < World.WIDTH; i++)
            for (int j = 0; j < World.HEIGHT;j++)
                world.put(new Wall(world), i, j);
        dimension = World.WIDTH;
        generateMaze();
    }

    public void generateMaze() {
        dfs(new Node(0, 0), 0);
        
    }

    boolean hasEnd = false;

    private void dfs(Node next, int depth) {
        if(!validNextNode(next))
            return;
        int x = next.x;
        int y = next.y;
        world.put(new Floor(world), x, y);

        Node left = new Node(x - 1, y);
        Node right = new Node(x + 1, y);
        Node up = new Node(x, y - 1);
        Node down = new Node(x, y + 1);

        ArrayList<Node> neighbors = new ArrayList(Arrays.asList(right, right, down, down, left, up));
        Collections.shuffle(neighbors);

        // randomly give up cut off
        while (neighbors.size() > 1 && (new Random()).nextInt(500) < depth)
            neighbors.remove(0);

        for (Node neighbor : neighbors) {
            dfs(neighbor, depth + 1);
        }
        

        // put terminal at the end of first path
        if (!hasEnd) {
            endX = x;
            endY = y;
            hasEnd = true;
        }
        return;
    }
    public World getMaze() {
        return world;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    private boolean validNextNode(Node node) {
        if(!pointOnGrid(node.x, node.y))
            return false;
        
        if(!(world.get(node.x, node.y) instanceof Wall))
            return false;

        int numNeighboringOnes = 0;
        for (int y = node.y-1; y < node.y+2; y++) {
            for (int x = node.x-1; x < node.x+2; x++) {
                if (pointOnGrid(x, y) && pointNotNode(node, x, y) && world.get(x, y) instanceof Floor) {
                    numNeighboringOnes++;
                }
            }
        }
        return (numNeighboringOnes < 3) && world.get(node.x, node.y) instanceof Wall;
    }

    private Boolean pointOnGrid(int x, int y) {
        return x >= 0 && y >= 0 && x < dimension && y < dimension;
    }
    
    private Boolean pointNotNode(Node node, int x, int y) {
        return !(x == node.x && y == node.y);
    }

}