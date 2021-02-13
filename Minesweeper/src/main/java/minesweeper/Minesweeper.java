 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

/**
 *
 * @author tenbroep
 */
public class Minesweeper extends Game {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DConsole dc = new DConsole();
        dc.setBackground(Color.darkGray);
        dc.registerFont(Minesweeper.class.getResourceAsStream("/bit9x9.ttf"));
        Cell[][] cells;
        cells = initCells(20, 32);
        setMines(cells, 99);
        setValues(cells);

        while (true) {

            if (dc.getMouseButton(1)) {
                click(dc, cells);
            }
            if (dc.getMouseButton(3)) {
                setFlag(dc, cells);
            }
            drawCells(dc, cells);
            if (lose(cells)) {
                dc.setPaint(Color.RED);
                dc.setFont(new Font("bit9x9", 100, 100));
                dc.drawString("lose", dc.getWidth() / 2, dc.getHeight() / 2);
                break;
            }
            if (win(cells)) {
                dc.setPaint(Color.BLUE);
                dc.setFont(new Font("bit9x9", 100, 100));
                dc.drawString("win", dc.getWidth() / 2, dc.getHeight() / 2);
                break;
            }

            dc.redraw();
            dc.clear();
        }
        drawBombs(dc, cells);
        dc.redraw();
        dc.clear();

    }

}