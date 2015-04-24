package pl.agh.sr.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface App extends Remote {

    /**
     * @return Set of Rooms existing on server at the moment.
     * @throws RemoteException
     */
    Set<IRoom> listRooms() throws RemoteException;

    /**
     * Creates new room for real player.
     * @param player Player creating this new Room.
     * @return Newly created room.
     * @throws RemoteException
     */
    IRoom createNewRoom(IPlayer player) throws RemoteException;

    /**
     * Creates new room for a game with bot.
     * @param player Player creating this new Room.
     * @return Newly created room.
     * @throws RemoteException
     */
    IRoom createNewBotRoom(IPlayer player) throws RemoteException;

    /**
     * @param roomId id of existing room.
     *               Creates new room if not exists!
     * @throws RemoteException
     */
    void joinRoom(RoomId roomId, IPlayer player) throws RemoteException;
}
