package pl.agh.sr.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agh.sr.rmi.bot.RandomMoveBotLogic;
import pl.agh.sr.rmi.room.RoomImpl;

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
    public Set<IRoom> listRooms() throws RemoteException {
        return roomsRepository.immutableView();
    }

    @Override
    public IRoom createNewRoom(RealPlayer player) throws RemoteException {
        log.debug("Creating new room...");

        BoardImpl board = new BoardImpl();
        RoomId roomId = new RoomId();
        IRoom newRoom = new RoomImpl(board, roomId);

        newRoom.addPlayer(player);
        roomsRepository.addRoom(newRoom);

        IBoard stub = (IBoard) UnicastRemoteObject.exportObject(board, 0);

        String boardRmiAddress = RMI_REGISTRY_ADDRESS + "/room/" + roomId.toString() + "/" + BOARD_REMOTE_OBJECT_NAME;
        try {
            Naming.rebind(boardRmiAddress, stub);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        log.debug("New room created at {}.", boardRmiAddress);
        return (IRoom) UnicastRemoteObject.exportObject(newRoom, 0);
    }

    @Override
    public IRoom createNewBotRoom(RealPlayer player) throws RemoteException {
        IRoom newRoom = createNewRoom(player);

        Bot bot = new Bot(newRoom.board(), new RandomMoveBotLogic());
        newRoom.addPlayer(bot);

        log.debug("Bot's room created");
        return newRoom;
    }

    @Override
    public void joinRoom(RoomId roomId, RealPlayer player) throws RemoteException {
        IRoom room = roomsRepository.load(roomId);
        room.addPlayer(player);

        log.debug("User {} added to {} room", player.getNickName(), roomId.toString());
    }
}
