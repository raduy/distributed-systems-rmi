package pl.agh.sr.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface IPlayer extends Remote, Serializable {

    String getNickName() throws RemoteException;

    boolean isReady() throws RemoteException;

    void markReady() throws RemoteException;

    void onMove() throws RemoteException;

    void onGameStart(Sign sign) throws RemoteException;

    void onBoardUpdated() throws RemoteException;

    void onWin() throws RemoteException;

    void onLoose() throws RemoteException;

    void onDraw() throws RemoteException;
}
