package it.polimi.ingsw.model;

/**
 * Step class is used in {@link PlayerTurn} class to store
 * every move made by the player so that he can perform the right
 * sequence of moves, standard or custom that depends on his card
 */
public class Step {

    /**
     * Type move:
     * "M" move
     * "B" build
     */
    private String type; //M or B

    /**
     * Cell from which a worker
     * is moving or building
     */
    private Cell CellFrom;

    /**
     * Cell in which a player wants his
     * worker to build or move
     */
    private Cell CellTo;

    /**
     * @param type
     * It sets type move
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param cellFrom
     * It sets cellFrom
     */
    public void setCellFrom(Cell cellFrom) {
        CellFrom = cellFrom;
    }

    /**
     * @param cellTo
     * It sets cellTo
     */
    public void setCellTo(Cell cellTo) {
        CellTo = cellTo;
    }

    /**
     * @return type move
     */
    public String getType() {
        return type;
    }

    /**
     * @return cellFrom
     */
    public Cell getCellFrom() {
        return CellFrom;
    }

    /**
     * @return cellTo
     */
    public Cell getCellTo() {
        return CellTo;
    }
}
