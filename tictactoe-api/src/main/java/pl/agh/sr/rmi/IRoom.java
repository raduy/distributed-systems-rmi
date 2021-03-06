package pl.agh.sr.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface IRoom extends Remote {

    RoomId getId() throws RemoteException;

    IBoard board() throws RemoteException;

    /**
     * Send notification to server that player is ready.
     * @param player
     * @throws RemoteException
     */
    void markPlayerReady(IPlayer player) throws RemoteException;

    /**
     *
     * @return Returns players in this Room
     */
    Set<IPlayer> players() throws RemoteException;

    void addPlayer(IPlayer player) throws RemoteException;
}
