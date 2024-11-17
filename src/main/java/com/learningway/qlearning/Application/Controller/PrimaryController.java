package com.learningway.qlearning.Application.Controller;

import com.learningway.qlearning.Application.Service.CreateMazeService;
import com.learningway.qlearning.Application.Service.SolveMazeService;
import com.learningway.qlearning.Config.ConfigProvider;
import com.learningway.qlearning.Enum.WallType;
import com.learningway.qlearning.Model.Maze;
import com.learningway.qlearning.Model.Square;
import java.io.IOException;
import static java.lang.Math.floor;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class PrimaryController {

    @FXML
    private TextField widthTextField;

    @FXML
    private TextField heightTextField;

    @FXML
    private Pane mainPanel;

    @FXML
    private GridPane messagePanel;

    @FXML
    private Label messageLabel;

    ConfigProvider configProvider;

    private int width;

    private int height;

    private Maze maze;

    public PrimaryController() {
        this.configProvider = new ConfigProvider();
    }

    @FXML
    private void drawMaze() throws IOException {
        this.width = Integer.parseInt(this.widthTextField.getText());
        this.height = Integer.parseInt(this.heightTextField.getText());

        if (this.validateSize() == false) {
            return;
        }

        this.maze = this.generateMaze(width, height);

        this.drawMaze(this.maze);
    }

    private Maze generateMaze(int width, int height) {
        CreateMazeService createMazeService = new CreateMazeService();
        this.maze = createMazeService.create(this.width, this.height);

        SolveMazeService solveMazeService = new SolveMazeService(this.maze);
        if (solveMazeService.canSolved() == false) {
            this.generateMaze(width, height);
        }

        return maze;
    }

    @FXML
    private void solveMaze() throws IOException {
        SolveMazeService solveMazeService = new SolveMazeService(this.maze);
        this.drawPath(solveMazeService.solve());
    }

    private boolean validateSize() {
        boolean visible = false;

        this.messageLabel.setText("");

        if (this.width > this.configProvider.getMaxMazeWidth()
                || this.width < this.configProvider.getMinMazeWidth()) {
            visible = true;
            this.messageLabel.setText(
                    String.format(
                            "Width value should be between %d and %d",
                            this.configProvider.getMinMazeWidth(),
                            this.configProvider.getMaxMazeWidth()
                    )
            );
        }

        if (this.height > this.configProvider.getMaxMazeHeight()
                || this.height < this.configProvider.getMinMazeHeight()) {
            visible = true;
            String message = this.messageLabel.getText();
            this.messageLabel.setText(
                    String.format(
                            message + "\nHeight value should be between %d and %d",
                            this.configProvider.getMinMazeHeight(),
                            this.configProvider.getMaxMazeHeight()
                    )
            );
        }

        this.messagePanel.setVisible(visible);

        if (visible == true) {
            Thread newThread = new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException ex) {
                    //continue
                }
                this.messagePanel.setVisible(false);
            });
            newThread.start();
        }

        return visible == false;
    }

    private void drawMaze(Maze maze) {
        this.mainPanel.getChildren().clear();

        int sizeX = (int) ((this.mainPanel.getWidth() / this.width) - 1);
        int sizeY = (int) ((this.mainPanel.getHeight() / this.height) - 1);

        for (int i = 0; i < maze.getHeight(); i++) {
            for (int y = 0; y < maze.getWidth(); y++) {
                Square square = maze.getSquare(i, y);

                if (square.getLeft() == WallType.WALL) {
                    Line line = this.getLine();
                    Double startPointX = (double) y * sizeX;
                    Double startPointY = (double) i * sizeY;

                    line.setStartX(startPointX);
                    line.setEndX(startPointX);
                    line.setStartY(startPointY);
                    line.setEndY(startPointY + sizeY);
                    this.mainPanel.getChildren().add(line);
                }

                if (square.getRight() == WallType.WALL) {
                    Line line = this.getLine();
                    Double startPointX = (double) sizeX + (y * sizeX);
                    Double startPointY = (double) i * sizeY;

                    line.setStartX(startPointX);
                    line.setEndX(startPointX);
                    line.setStartY(startPointY);
                    line.setEndY(startPointY + sizeY);
                    this.mainPanel.getChildren().add(line);
                }

                if (square.getTop() == WallType.WALL) {
                    Line line = this.getLine();
                    Double startPointX = (double) y * sizeX;
                    Double startPointY = (double) i * sizeY;

                    line.setStartX(startPointX);
                    line.setEndX(startPointX + sizeX);
                    line.setStartY(startPointY);
                    line.setEndY(startPointY);
                    this.mainPanel.getChildren().add(line);
                }

                if (square.getBottom() == WallType.WALL) {
                    Line line = this.getLine();
                    Double startPointX = (double) y * sizeX;
                    Double startPointY = (double) sizeY + (i * sizeY);

                    line.setStartX(startPointX);
                    line.setEndX(startPointX + sizeX);
                    line.setStartY(startPointY);
                    line.setEndY(startPointY);
                    this.mainPanel.getChildren().add(line);
                }
            }
        }
    }

    private void drawPath(List<Integer> path) {
        for (int k = 0; k < path.size(); k++) {
            this.drawRectangle(path, k);
        }
    }

    private Line getLine() {
        Line line = new Line();
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        line.setStrokeLineCap(StrokeLineCap.SQUARE);

        return line;
    }

    private void drawRectangle(List<Integer> path, int count) {
        int sizeX = (int) ((this.mainPanel.getWidth() / this.width) - 1);
        int sizeY = (int) ((this.mainPanel.getHeight() / this.height) - 1);
        int rectangleWidth = (int) sizeX - (2 * (int) floor(sizeX / 10));
        int rectangleHeight = (int) sizeY - (2 * (int) floor(sizeY / 10));

        int i = path.get(count) % this.width;
        int y = (int) floor(path.get(count) / this.width);

        Rectangle rectangle = new Rectangle();
        Double startPointX = ((double) i * sizeX) + floor(sizeX / 10);
        Double startPointY = ((double) y * sizeY) + floor(sizeY / 10);

        rectangle.setX(startPointX);
        rectangle.setY(startPointY);

        rectangle.setFill(Color.CRIMSON);
        rectangle.setWidth(rectangleWidth);
        rectangle.setHeight(rectangleHeight);

        Text text = this.getCountText(count + 1, startPointX, startPointY, rectangleWidth, rectangleHeight);

        this.mainPanel.getChildren().addAll(rectangle, text);
    }

    private Text getCountText(int count, Double startPointX, Double startPointY, int width, int height) {

        Text text = new Text();
        text.setText(Integer.toString(count));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setTextOrigin(VPos.CENTER);
        text.setFill(Color.WHITE);
        text.setWrappingWidth((double) width);
        text.setX(startPointX);
        text.setY(startPointY + (height / 2));

        return text;
    }
}
