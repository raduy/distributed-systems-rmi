package pl.agh.sr.rmi.player;

import pl.agh.sr.rmi.RealPlayer;
import pl.agh.sr.rmi.TicTacToeApp;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class RealPlayerImpl implements RealPlayer, Serializable {

    private final String nickName;
    private boolean isReady;
    private transient final TicTacToeApp ticTacToeApp;

    public RealPlayerImpl(String nickName, TicTacToeApp ticTacToeApp) {
        this.nickName = nickName;
        this.ticTacToeApp = ticTacToeApp;
    }

    @Override
    public void onMove() {
        ticTacToeApp.readMove();
    }

    @Override
    public void onGameStart() throws RemoteException {
        ticTacToeApp.startGame();
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public boolean isReady() throws RemoteException {
        return isReady;
    }

    @Override
    public void markReady() throws RemoteException {
        this.isReady = true;
    }
}
