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
    Set<Room> listRooms() throws RemoteException;

    /**
     * @return Newly created room.
     * @throws RemoteException
     */
    Room createNewRoom() throws RemoteException;

    /**
     * @param roomId id of existing room.
     *               Creates new room if not exists!
     * @throws RemoteException
     */
    void joinRoom(RoomId roomId) throws RemoteException;
}
