package pl.agh.sr.rmi;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public enum Sign {
    NONE(0),
    X(1),
    O(-1);

    private final int value;

    Sign(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
