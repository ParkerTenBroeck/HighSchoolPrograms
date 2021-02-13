/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import DLibX.DConsole;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Parker TenBroeck
 */
public class Game {

    public Game() {

    }

    public static Cell[][] initCells(int x, int y) {
        Cell[][] cells = new Cell[x][y];

        for (int i = 0; i < x; ++i) {
            for (int j = 0; j < y; ++j) {
                cells[i][j] = new Cell();
            }
        }
        return cells;
    }

    public static int[] getMousePos(DConsole dc) {

        int[] mousePos = new int[2];

        mousePos[0] = dc.getMouseXPosition() / 26;
        mousePos[1] = dc.getMouseYPosition() / 26;

        return mousePos;
    }

    public static void setFlag(DConsole dc, Cell[][] c) {

        int[] pos = getMousePos(dc);

        if (!(pos[0] < 0 || pos[0] > c[0].length - 1)) {
            if (!(pos[1] < 0 || pos[1] > c.length - 1)) {
                if (!c[pos[1]][pos[0]].isChecked()) {
                    c[pos[1]][pos[0]].invertMark();
                }
            }
        }

    }

    public static boolean lose(Cell[][] cells) {

        for (int i = 0; i < cells[0].length; ++i) {
            for (int j = 0; j < cells.length; ++j) {

                Cell c = cells[j][i];

                if (c.isChecked() && c.isMine()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean win(Cell[][] cells) {

        for (int i = 0; i < cells[0].length; ++i) {
            for (int j = 0; j < cells.length; ++j) {

                Cell c = cells[j][i];

                if ((!c.isChecked() && !c.isMine())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void click(DConsole dc, Cell[][] cells) {

        int[] pos = getMousePos(dc);

        if (!(pos[0] < 0 || pos[0] > cells[0].length - 1)) {
            if (!(pos[1] < 0 || pos[1] > cells.length - 1)) {
                showCell(cells, pos[0], pos[1]);
            }
        }

    }

    public static void showCell(Cell[][] cells, int x, int y) {

        if (!(x < 0 || x > cells[0].length - 1)) {
            if (!(y < 0 || y > cells.length - 1)) {

                Cell c = cells[y][x];

                if (!c.isMarked()) {
                    c.checked();
                }
                if (c.isMine()) {
                    return;
                }
            }
        }

        ArrayList<Cell> surroundingCells;
        surroundingCells = getSurroundingCells(cells, x, y);

        for (Cell cell : surroundingCells) {
            if (cell.isMine()) {
                return;
            }
        }
        for (Cell cell : surroundingCells) {

            if (!cell.isChecked()) {
                int[] pos = getCellPos(cells, cell);
                if (!cell.isMarked()) {
                    //cell.checked();
                    showCell(cells, pos[0], pos[1]);
                }

            }
        }

    }

    public static int[] getCellPos(Cell[][] cells, Cell cell) {

        for (int i = 0; i < cells[0].length; ++i) {
            for (int j = 0; j < cells.length; ++j) {

                if (cell.equals(cells[j][i])) {
                    return new int[]{i, j};
                }

            }
        }
        try {
            throw new Exception("cell now found ");
        } catch (Exception ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static void drawCells(DConsole dc, Cell[][] c) {

        for (int i = 0; i < c[0].length; ++i) {
            for (int j = 0; j < c.length; ++j) {

                if (c[j][i].isChecked()) {
                    dc.setPaint(new Color(0xd1d0ce));
                } else {
                    dc.setPaint(new Color(0xa6a4a0));
                }
                dc.fillRect(i * 26, j * 26, 25, 25);

                if (c[j][i].isMarked()) {
                    dc.setPaint(new Color(255, 0, 0));
                    dc.fillEllipse(i * 26, j * 26, 6, 6);
                }
                if (c[j][i].isChecked() && c[j][i].getValue() > 0) {
                    dc.setPaint(Color.BLUE);
                    dc.setFont(new Font("bit9x9", 20, 20));
                    dc.drawString(c[j][i].getValue(), i * 26 + 8, j * 26 + 8);
                }
            }
        }
        int[] pos = getMousePos(dc);
        dc.setPaint(new Color(255, 0, 0, 50));
        dc.fillRect(pos[0] * 26, pos[1] * 26, 25, 25);
    }

    public static void setMines(Cell[][] cells, int ammount) {

        if (ammount > 0) {
            int x = (int) randomRange(0, cells[0].length - 1);
            int y = (int) randomRange(0, cells.length - 1);

            if (cells[y][x].isMine()) {
                setMines(cells, ammount);
            } else {
                cells[y][x].setMine(true);
                setMines(cells, ammount - 1);
            }
        }

    }

    public static void drawBombs(DConsole dc, Cell[][] c) {

        for (int i = 0; i < c[0].length; ++i) {
            for (int j = 0; j < c.length; ++j) {

                if (c[j][i].isMine()) {
                    dc.setPaint(Color.BLACK);
                    dc.fillEllipse(i * 26 + 19, j * 26, 6, 6);
                }
            }
        }
    }

    public static void setValues(Cell[][] cells) {

        for (int i = 0; i < cells[0].length; ++i) {
            for (int j = 0; j < cells.length; ++j) {

                cells[j][i].setValue(getValue(cells, i, j));

            }
        }
    }

    public static int getValue(Cell[][] cells, int x, int y) {

        int value = 0;
        ArrayList<Cell> surroundingCells;
        surroundingCells = getSurroundingCells(cells, x, y);

        for (Cell cell : surroundingCells) {
            if (cell.isMine()) {
                value++;
            }
        }
        return value;
    }

    public static ArrayList<Cell> getSurroundingCells(Cell[][] cells, int x, int y) {

        ArrayList<Cell> surroundingCells = new ArrayList();

        for (int i = 0; i < 3; i++) {
            surroundingCells.add(safeGetCell(cells, x + 1, y - 1 + i));
        }
        surroundingCells.add(safeGetCell(cells, x, y - 1));
        surroundingCells.add(safeGetCell(cells, x, y + 1));

        for (int i = 0; i < 3; i++) {
            surroundingCells.add(safeGetCell(cells, x - 1, y - 1 + i));
        }
        for (int i = surroundingCells.size() - 1; i >= 0; i--) {
            if (surroundingCells.get(i) == null) {
                surroundingCells.remove(i);
            }
        }
        return surroundingCells;
    }

    public static Cell safeGetCell(Cell[][] cells, int x, int y) {

        if (!(x < 0 || x > cells[0].length - 1)) {
            if (!(y < 0 || y > cells.length - 1)) {
                return cells[y][x];
            }
        }
        return null;

    }

    public static double randomRange(double min, double max) {
        double x = (double) ((Math.random() * ((max - min) + 1)) + min);
        return x;
    }
}
