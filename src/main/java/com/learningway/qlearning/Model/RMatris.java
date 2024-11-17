package com.learningway.qlearning.Model;

import com.learningway.qlearning.Enum.WallType;
import static java.lang.Math.floor;

public class RMatris {

    public int[][] rMatris;

    public RMatris(Maze maze) {
        int height = maze.getHeight();
        int width = maze.getWidth();
        int matrisSize = height * width;

        this.rMatris = new int[matrisSize][matrisSize];

        for (int i = 0; i < matrisSize; i++) {
            for (int y = 0; y < matrisSize; y++) {
                this.adjustRelations(maze, i, y, width);
            }
        }
    }

    private void adjustRelations(Maze maze, int xIndex, int yIndex, int width) {
        Square square = maze.getSquare((int) floor(xIndex / width), xIndex % width);

        if (xIndex - width == yIndex) {
            if (square.getTop() == WallType.WALL) {
                this.rMatris[xIndex][yIndex] = -1;
            } else {
                if (maze.isExit((int) floor(yIndex / width), yIndex % width) == true) {
                    this.rMatris[xIndex][yIndex] = 100;
                } else {
                    this.rMatris[xIndex][yIndex] = 0;
                }
            }
        } else if (xIndex - 1 == yIndex) {
            if (square.getLeft() == WallType.WALL 
                    || square.getLeft() == WallType.ENTRANCE) {
                this.rMatris[xIndex][yIndex] = -1;
            } else {
                if (maze.isExit((int) floor(yIndex / width), yIndex % width) == true) {
                    this.rMatris[xIndex][yIndex] = 100;
                } else {
                    this.rMatris[xIndex][yIndex] = 0;
                }
            }
        } else if (xIndex + 1 == yIndex) {
            if (square.getRight() == WallType.WALL 
                    || square.getRight() == WallType.EXIT) {
                this.rMatris[xIndex][yIndex] = -1;
            } else {
                if (maze.isExit((int) floor(yIndex / width), yIndex % width) == true) {
                    this.rMatris[xIndex][yIndex] = 100;
                } else {
                    this.rMatris[xIndex][yIndex] = 0;
                }
            }
        } else if (xIndex + width == yIndex) {
            if (square.getBottom() == WallType.WALL) {
                this.rMatris[xIndex][yIndex] = -1;
            } else {
                if (maze.isExit((int) floor(yIndex / width), yIndex % width) == true) {
                    this.rMatris[xIndex][yIndex] = 100;
                } else {
                    this.rMatris[xIndex][yIndex] = 0;
                }
            }
        } else if (xIndex == yIndex) {
            if (maze.isExit((int) floor(yIndex / width), yIndex % width) == true) {
                this.rMatris[xIndex][yIndex] = 100;
            } else {
                this.rMatris[xIndex][yIndex] = -1;
            }
        } else {
            this.rMatris[xIndex][yIndex] = -1;
        }
    }
}
