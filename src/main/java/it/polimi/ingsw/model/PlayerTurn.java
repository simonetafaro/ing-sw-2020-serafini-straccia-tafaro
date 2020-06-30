package it.polimi.ingsw.model;

/**
 * PlayerTurn class is created to store an entire turn of a player until
 * he sends a {@link PlayerMoveEnd}. It gives information about:
 * {@link Player}, {@link Worker} used in his turn and a pointer to the current {@link Step}
 */
public class PlayerTurn {

    /**
     * Array to keep trace each Step of a turn's player
     */
    private Step[] steps = new Step[6];

    /**
     * Player
     */
    private Player turnPlayer;

    /**
     * Turn worker that must be the same in each step of turn
     */
    private Worker worker;

    /**
     * Pointer to the current Step
     */
    private Step currStep;

    /**
     * Index of array
     */
    private int i;


    /**
     * Constructor that initializes array
     * and sets currentStep to 0
     *
     * @param player
     */
    public PlayerTurn(Player player) {
        this.turnPlayer = player;
        steps[0] = new Step();
        steps[1] = new Step();
        steps[2] = new Step();
        steps[3] = new Step();
        steps[4] = new Step();
        this.i = 0;
        currStep = steps[i];
        this.worker = null;
    }

    /**
     * @return player
     */
    public Player getTurnPlayer() {
        return turnPlayer;
    }

    /**
     * @return current step
     */
    public Step getCurrStep() {
        return currStep;
    }

    /**
     * @return array's index
     */
    public int getI() {
        return i + 1;
    }

    /**
     * It updates current step and index. It's called each time
     * player has finished a single step of his PlayerTurn
     */
    public void updateStep() {
        i++;
        currStep = steps[i];
    }

    /**
     * It resets current step at the end of a PlayerTurn,
     * so that it's ready for the next round
     */
    public void resetStep() {
        this.i = 0;
        currStep = steps[i];
    }

    /**
     * @return true if player is in first step of his playerTurn
     */
    public boolean isFirstStep() {
        return currStep == steps[0];
    }

    /**
     * @param worker It sets turn worker used by player in this turn
     */
    public void setTurnWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * @return worker used by player in this turn
     */
    public Worker getTurnWorker() {
        return this.worker;
    }

    /**
     * Given an index, it returns the corresponding Step
     *
     * @param i index
     * @return Step
     */
    public Step getStepI(int i) {
        //TODO manage array out of bound exception
        return steps[i - 1];
    }

}
