package pl.agh.sr.rmi.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agh.sr.rmi.*;
import pl.agh.sr.rmi.exception.RoomAlreadyFullException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class RoomImpl implements IRoom, Serializable {
    private static final Logger log = LoggerFactory.getLogger(RoomImpl.class);

    private final RoomId id;
    private final IBoard board;
    private Set<RealPlayer> players = new HashSet<RealPlayer>(2);

    public RoomImpl(IBoard board, RoomId roomId) {
        this.board = board;
        this.id = roomId;
    }

    @Override
    public void addPlayer(RealPlayer player) {
        if (players.size() >= 2) {
            throw new RoomAlreadyFullException();
        }

        players.add(player);
    }

    @Override
    public RoomId getId() throws RemoteException {
        return id;
    }

    @Override
    public IBoard board() throws RemoteException {
        return board;
    }

    @Override
    public void markPlayerReady(RealPlayer player) throws RemoteException {
        player.markReady();
        log.debug("User {} ready!", player.getNickName());

        if (players.size() != 2) {
            return;
        }

        for (RealPlayer realPlayer : players) {
            if (!realPlayer.isReady()) {
                log.info("Player {} not ready yet. Waiting...", realPlayer);
                return;
            }
        }

        startGame();
    }

    private void startGame() throws RemoteException {
        for (RealPlayer realPlayer : players) {
            realPlayer.onGameStart();
        }
    }

    @Override
    public Set<RealPlayer> players() throws RemoteException {
        return players;
    }
}
