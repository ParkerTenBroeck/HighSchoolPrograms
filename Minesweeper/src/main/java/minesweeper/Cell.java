/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

public class Cell {

    private boolean mine, mark, checked;
    private int value;

    public Cell() {
        this.mark = false;
        this.mine = false;
        this.checked = false;
        this.value = 0;
    }

    public int getValue() {
        return this.value;
    }

    public void invertMark() {
        mark = !mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public boolean isEmpty() {
        return this.value == 0;
    }

    public boolean isMine() {
        return this.mine;
    }

    public void setMine(boolean b) {
        this.mine = b;
    }

    public boolean isMarked() {
        return this.mark;
    }

    public void setAroundMines(int count) {
        this.value = count;
    }

    public int getAroundMines() {
        return this.value;
    }
    public void setValue(int value){
        this.value = value;
    }

    public void checked() {
        this.checked = true;
    }

    public void clearChecked() {
        this.checked = false;
    }

    public boolean isChecked() {
        return this.checked;
    }

}
