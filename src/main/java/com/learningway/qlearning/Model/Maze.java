package com.learningway.qlearning.Model;

import com.learningway.qlearning.Application.Exception.SquareNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public final class Maze {
    private final List<Row> rows;

    public Maze() {
        this.rows = new ArrayList<>();
    }
    
    public void addRow(Row row) {
        this.rows.add(row);
    }
    
    public boolean isExit(int x, int y) {
        return this.rows.get(x).getSquares().get(y).isExit();
    }
    
    public int getEntrance() {
        int height = this.rows.size();
        int width = this.rows.get(0).getSquares().size();
        
        for(int i = 0; i < height; i++) {
            for(int y = 0; y < width; y++) {
                if(this.rows.get(i).getSquares().get(y).isEntrance() == true) {
                    return (i * width ) + y;
                }
            }
        }
        throw new SquareNotFoundException();
    }
    
    public Square getSquare(int x, int y) {
        return this.rows.get(x).getSquares().get(y);
    }
    
    public int getHeight() {
        return this.rows.size();
    }
    
    public int getWidth() {
        return this.rows.get(0).getSquares().size();
    }
}
