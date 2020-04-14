package it.polimi.ingsw.model;

public class Step {
    private String type; //M or B
    private Cell CellFrom;
    private Cell CellTo;

    public void setType(String type) {
        this.type = type;
    }
    public void setCellFrom(Cell cellFrom) {
        CellFrom = cellFrom;
    }
    public void setCellTo(Cell cellTo) {
        CellTo = cellTo;
    }

    public String getType() {
        return type;
    }
    public Cell getCellFrom() {
        return CellFrom;
    }
    public Cell getCellTo() {
        return CellTo;
    }
}
