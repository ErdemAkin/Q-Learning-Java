package com.learningway.qlearning.Model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Row {
    private final List<Square> squares;

    public Row() {
        this.squares = new ArrayList<>();
    }
    
    public void addSquare(Square square) {
        this.squares.add(square);
    }
}
