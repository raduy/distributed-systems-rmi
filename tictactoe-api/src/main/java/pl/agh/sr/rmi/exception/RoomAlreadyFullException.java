package pl.agh.sr.rmi.exception;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class RoomAlreadyFullException extends RuntimeException {
    public RoomAlreadyFullException(String message) {
        super(message);
    }

    public RoomAlreadyFullException() {
        super("Room is already full");
    }
}
