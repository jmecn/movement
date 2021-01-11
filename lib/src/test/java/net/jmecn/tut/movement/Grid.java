package net.jmecn.tut.movement;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @title Grid
 * @author yanmaoyuan
 * @date 2021年1月11日
 * @version 1.0
 */
public class Grid {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        int size = 256;
        int half = size / 2;

        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size, size);

        g.setColor(new Color(233, 233, 233));
        g.drawRect(0, half, size, 1);
        g.drawRect(half, 0, 1, size);

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, size - 1, size - 1);

        int min = 1;
        int max = size - 2;
        int seg = size / 8;
        int halfSeg = seg / 2;

        // corners
        g.drawLine(min, min, min, min + seg);
        g.drawLine(min, min, min + seg, min);

        g.drawLine(max, min, max - seg, min);
        g.drawLine(max, min, max, min + seg);

        g.drawLine(min, max, min, max - seg);
        g.drawLine(min, max, min + seg, max);

        g.drawLine(max, max, max - seg, max);
        g.drawLine(max, max, max, max - seg);

        // center
        g.drawLine(min, half - halfSeg, min, half + 1 + halfSeg);
        g.drawLine(max, half - halfSeg, max, half + 1 + halfSeg);
        g.drawLine(half - halfSeg, min, half + 1 + halfSeg, min);
        g.drawLine(half - halfSeg, max, half + 1 + halfSeg, max);

        g.setColor(Color.WHITE);
        g.fillRect(size / 2 - 1, size / 2 - 1, 4, 4);

        ImageIO.write(img, "png", new File("grid.png"));
    }

}
