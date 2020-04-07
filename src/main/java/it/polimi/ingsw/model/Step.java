package it.polimi.ingsw.model;

public class Step {
    private char type; //M or B
    private Cell CellFrom;
    private Cell CellTo;

    public void setType(char type) {
        this.type = type;
    }
    public void setCellFrom(Cell cellFrom) {
        CellFrom = cellFrom;
    }
    public void setCellTo(Cell cellTo) {
        CellTo = cellTo;
    }

    public char getType() {
        return type;
    }
    public Cell getCellFrom() {
        return CellFrom;
    }
    public Cell getCellTo() {
        return CellTo;
    }
}
