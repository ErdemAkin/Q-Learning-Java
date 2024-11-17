package com.learningway.qlearning.Application.Service;

import com.learningway.qlearning.Application.Exception.SquareNotFoundException;
import com.learningway.qlearning.Config.ConfigProvider;
import com.learningway.qlearning.Model.Maze;
import com.learningway.qlearning.Model.QMatris;
import com.learningway.qlearning.Model.RMatris;
import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Q(state,action) = R(state, action) + Gamma * Max[Q(next state, allActions)]
 */
public class SolveMazeService {

    ConfigProvider configProvider;
    private Maze maze;
    private RMatris rMatris;
    private QMatris qMatris;

    public SolveMazeService(Maze maze) {
        this.configProvider = new ConfigProvider();
        this.maze = maze;
        this.rMatris = new RMatris(maze);
        this.qMatris = new QMatris(maze);
    }

    public List<Integer> solve() throws SquareNotFoundException {
        this.prepareQMatris();
        List<Integer> path = this.findPath();

        return path;
    }

    private void prepareQMatris() {
        int height = this.maze.getHeight();
        int width = this.maze.getWidth();
        int matrisSize = height * width;
        float discountFactor = this.configProvider.getDiscountFactor();

        for (int k = 0; k < matrisSize; k++) {
            for (int i = 0; i < matrisSize; i++) {
                for (int y = 0; y < matrisSize; y++) {
                    if (this.rMatris.rMatris[i][y] == -1) {
                        continue;
                    }

                    this.qMatris.qMatris[i][y] = this.rMatris.rMatris[i][y]
                            + (discountFactor * Arrays.stream(this.qMatris.qMatris[y]).max().getAsDouble());
                }
            }
        }
    }
    
    public boolean canSolved() {
        this.prepareQMatris();
        boolean isFinished = false;
        int currentPath = this.maze.getEntrance();
        int width = this.maze.getWidth();
        
        while (isFinished == false) {
            if (this.isThereValueGreaterThan0(this.qMatris.qMatris[currentPath]) == false) {
                break;
            }
            
            int nextPath = this.findIndexOfMaxValue(this.qMatris.qMatris[currentPath]);

            if ( nextPath == currentPath) {
                isFinished = true;          
            }
            
            currentPath = nextPath;
        }
        return this.maze.getSquare((int) floor(currentPath / width), currentPath % width).isExit();
    }

    private List<Integer> findPath() {
        List<Integer> path = new ArrayList<>();

        boolean isFinished = false;
        int currentPath = this.maze.getEntrance();
        
        while (isFinished == false) {
            int nextPath = this.findIndexOfMaxValue(this.qMatris.qMatris[currentPath]);

            if ( nextPath == currentPath) {
                isFinished = true;
            }
            
            path.add(currentPath);
            currentPath = nextPath;
        }

        return path;
    }

    private int findIndexOfMaxValue(double values[]) {
        double max = Double.MIN_VALUE;
        int maxIndex = 0;
        int index = 0;

        for (double value : values) {
            if (value > max) {
                max = value;
                maxIndex = index;
            }
            index++;
        }

        return maxIndex;
    }
    
    private boolean isThereValueGreaterThan0(double values[]) {
        double max = Double.MIN_VALUE;
        int maxIndex = 0;
        int index = 0;

        for (double value : values) {
            if (value > max) {
                max = value;
                maxIndex = index;
            }
            index++;
        }

        return maxIndex > 0;
    }
}
