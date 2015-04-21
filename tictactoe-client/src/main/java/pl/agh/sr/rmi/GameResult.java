package pl.agh.sr.rmi;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public enum GameResult {
    WIN("Great success! You won!"),
    LOST("Oh crap.. Not this time"),
    DRAW("It's a draw! So boring....");

    private String message;

    GameResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
