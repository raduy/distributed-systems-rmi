package pl.agh.sr.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface IBoard extends Remote {

    /**
     * Shows current state of Board.
     * @return String representation of board state
     * @throws RemoteException
     */
    String show() throws RemoteException;

    /**
     * Marks field in current Board.
     * @param fieldNo Field number.
     * @throws RemoteException
     */
    void mark(int fieldNo, RealPlayer player) throws RemoteException;

}
