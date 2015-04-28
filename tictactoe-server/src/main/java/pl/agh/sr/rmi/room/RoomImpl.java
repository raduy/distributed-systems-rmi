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
    private final BoardImpl board;
    private IPlayer crossPlayer;
    private IPlayer circlePlayer;
    private boolean isRoomReady;

    public RoomImpl(BoardImpl board, RoomId roomId) {
        this.board = board;
        this.id = roomId;
    }

    @Override
    public void addPlayer(IPlayer player) {
        if (circlePlayer != null && crossPlayer != null) {
            throw new RoomAlreadyFullException();
        }

        if (crossPlayer == null) {
            crossPlayer = player;
        } else {
            circlePlayer = player;
            isRoomReady = true;
        }
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
    public void markPlayerReady(IPlayer player) throws RemoteException {
        player.markReady();
        log.debug("User {} ready!", player.getNickName());

        if (!isRoomReady) {
            return;
        }

        if (!circlePlayer.isReady() || !crossPlayer.isReady()) {
            log.info("Players not ready yet. Waiting...");
            return;
        }

        startGame();
    }

    private void startGame() throws RemoteException {
        board.beginGame(crossPlayer, circlePlayer);
    }

    @Override
    public Set<IPlayer> players() throws RemoteException {
        Set<IPlayer> players = new HashSet<IPlayer>();
        if (circlePlayer != null) {
            players.add(circlePlayer);
        }
        if (crossPlayer != null) {
            players.add(crossPlayer);
        }
        return players;
    }
}
