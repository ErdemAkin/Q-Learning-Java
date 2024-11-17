package com.learningway.qlearning.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import lombok.Getter;

@Getter
public final class ConfigProvider {

    private final int minMazeWidth;
    private final int maxMazeWidth;
    private final int minMazeHeight;
    private final int maxMazeHeight;
    private final float discountFactor;

    private static final String MINMAZEWIDTH = "min.maze.width";
    private static final String MAXMAZEWIDTH = "max.maze.width";
    private static final String MINMAZEHEIGHT = "min.maze.height";
    private static final String MAXMAZEHEIGHT = "max.maze.height";
    private static final String DISCOUNTFACTOR = "discount.factor";

    public ConfigProvider() {
        Properties prop = new Properties();
        String propFileName = "config.properties";
        FileInputStream in;

        int minWidth = 5;
        int maxWidth = 15;
        int minHeight = 5;
        int maxHeight = 15;
        float discountFactor = (float) 0.8;
        try {
            String text = System.getProperty("user.dir");
            in = new FileInputStream(text + "/src/main/java/" + propFileName);
            prop.load(in);
            minWidth = Integer.parseInt(prop.getProperty(ConfigProvider.MINMAZEWIDTH));
            maxWidth = Integer.parseInt(prop.getProperty(ConfigProvider.MAXMAZEWIDTH));
            minHeight = Integer.parseInt(prop.getProperty(ConfigProvider.MINMAZEHEIGHT));
            maxHeight = Integer.parseInt(prop.getProperty(ConfigProvider.MAXMAZEHEIGHT));
            discountFactor = Float.parseFloat(prop.getProperty(ConfigProvider.DISCOUNTFACTOR));
        } catch (IOException ex) {
            // continue
        }

        this.minMazeWidth = minWidth;
        this.maxMazeWidth = maxWidth;
        this.minMazeHeight = minHeight;
        this.maxMazeHeight = maxHeight;
        this.discountFactor = discountFactor;
    }
}
