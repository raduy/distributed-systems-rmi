package pl.agh.sr.rmi;

import pl.agh.sr.rmi.exception.RoomAlreadyFullException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class Room implements Serializable {
    private final RoomId id;
    private final IBoard board;
    private Set<Player> players = new HashSet<Player>(2);

    public Room(IBoard board, RoomId roomId) {
        this.board = board;
        this.id = roomId;
    }

    public RoomId getId() {
        return id;
    }

    public IBoard board() {
        return board;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", players=" + players +
                '}';
    }

    public void addPlayer(Player player) {
        if (players.size() >= 2) {
            throw new RoomAlreadyFullException();
        }

        players.add(player);
    }
}
