package pl.agh.sr.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class AppImpl implements App {

    private static final String RMI_REGISTRY_ADDRESS = "rmi://127.0.0.1:1099";
    private static final String BOARD_REMOTE_OBJECT_NAME = "board";

    private final RoomsRepository roomsRepository = new RoomsRepository();

    @Override
    public Set<Room> listRooms() throws RemoteException {
        return roomsRepository.immutableView();
    }

    @Override
    public Room createNewRoom() throws RemoteException {
//        logger... todo
        System.out.println("Creating new room...");

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

        System.out.printf("New room created at %s", boardRmiAddress);
        return newRoom;
    }

    @Override
    public void joinRoom(RoomId roomId) throws RemoteException {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
