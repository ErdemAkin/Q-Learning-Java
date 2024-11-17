package com.learningway.qlearning.Application.Service;

import com.learningway.qlearning.Enum.WallType;
import com.learningway.qlearning.Model.Maze;
import com.learningway.qlearning.Model.Row;
import com.learningway.qlearning.Model.Square;
import java.util.Random;

public class CreateMazeService {

    private final Random rand;

    public CreateMazeService() {
        this.rand = new Random();
    }

    public Maze create(int width, int height) {
        Maze maze = new Maze();

        for (int i = 0; i < height; i++) {
            Row row = new Row();
            for (int y = 0; y < width; y++) {
                row.addSquare(this.createSquare(i + 1, y + 1, width, height));
            }
            maze.addRow(row);
        }
        this.createEntrance(maze, this.rand.nextBoolean());
        this.createExit(maze, this.rand.nextBoolean());
        this.createMazeInside(maze);
        return maze;
    }

    private Square createSquare(int row, int column, int width, int height) {
        Square square = new Square();

        if (row == 1) {
            square.setTop(WallType.WALL);

            if (column == 1) {
                square.setLeft(WallType.WALL);
            }

            if (column == width) {
                square.setRight(WallType.WALL);
            }
        } else if (row == height) {
            square.setBottom(WallType.WALL);

            if (column == 1) {
                square.setLeft(WallType.WALL);
            }

            if (column == width) {
                square.setRight(WallType.WALL);
            }
        } else {
            if (column == 1) {
                square.setLeft(WallType.WALL);
            }

            if (column == width) {
                square.setRight(WallType.WALL);
            }
        }

        return square;
    }

    private void createEntrance(Maze maze, boolean top) {
        if (top == true) {
            maze
                    .getSquare(0, this.rand.nextInt(maze.getWidth()))
                    .setEntrance(top);
        } else {
            maze
                    .getSquare(this.rand.nextInt(maze.getHeight()), 0)
                    .setEntrance(top);
        }
    }

    private void createExit(Maze maze, boolean bottom) {
        if (bottom == true) {
            maze
                    .getSquare(maze.getHeight() - 1, this.rand.nextInt(maze.getWidth()))
                    .setExit(bottom);
        } else {
            maze
                    .getSquare(this.rand.nextInt(maze.getHeight()), maze.getWidth() - 1)
                    .setExit(bottom);
        }
    }

    private void createMazeInside(Maze maze) {
        int height = maze.getHeight();
        int width = maze.getWidth();

        for (int i = 0; i < height; i++) {
            for (int y = 0; y < width; y++) {
                Square square = maze.getSquare(i, y);
                boolean bottom;
                boolean right;

                do {
                    right = rand.nextBoolean();
                    bottom = rand.nextBoolean();
                } while (right == true && bottom == true);

                if (y + 1 < width) {
                    square.setRight(right == true ? WallType.WALL : WallType.GATE);
                    maze.getSquare(i, y + 1).setLeft(right == true ? WallType.WALL : WallType.GATE);
                }

                if (i + 1 < height) {
                    square.setBottom(bottom == true ? WallType.WALL : WallType.GATE);
                    maze.getSquare(i + 1, y).setTop(bottom == true ? WallType.WALL : WallType.GATE);
                }
            }
        }
    }
}
