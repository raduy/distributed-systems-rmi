package pl.agh.sr.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class AppImpl implements App {
    private final static Logger log = LoggerFactory.getLogger(AppImpl.class);
    private static final String RMI_REGISTRY_ADDRESS = "rmi://127.0.0.1:1099";
    private static final String BOARD_REMOTE_OBJECT_NAME = "board";

    private final RoomsRepository roomsRepository = new RoomsRepository();

    @Override
    public Set<Room> listRooms() throws RemoteException {
        return roomsRepository.immutableView();
    }

    @Override
    public Room createNewRoom() throws RemoteException {
        log.debug("Creating new room...");

        BoardImpl board = new BoardImpl();
        RoomId roomId = new RoomId();
        Room newRoom = new Room(board, roomId);
        roomsRepository.addRoom(newRoom);

        IBoard stub = (IBoard) UnicastRemoteObject.exportObject(board, 0);

        String boardRmiAddress = RMI_REGISTRY_ADDRESS + "/room/" + roomId.toString() + "/" + BOARD_REMOTE_OBJECT_NAME;
        try {
            Naming.rebind(boardRmiAddress, stub);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        log.debug("New room created at {}.", boardRmiAddress);
        return newRoom;
    }

    @Override
    public void joinRoom(RoomId roomId) throws RemoteException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
