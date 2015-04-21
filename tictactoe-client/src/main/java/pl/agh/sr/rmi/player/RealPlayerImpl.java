package pl.agh.sr.rmi.player;

import pl.agh.sr.rmi.GameResult;
import pl.agh.sr.rmi.RealPlayer;
import pl.agh.sr.rmi.TicTacToeApp;
import pl.agh.sr.rmi.command.CommandRouter;

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
    public void onBoardUpdated() throws RemoteException {
        ticTacToeApp.printBoard();
    }

    @Override
    public void onWin() throws RemoteException {
        afterGameEnd(GameResult.WIN);
    }

    @Override
    public void onLoose() throws RemoteException {
        afterGameEnd(GameResult.LOST);
    }

    @Override
    public void onDraw() throws RemoteException {
        afterGameEnd(GameResult.DRAW);
    }

    private void afterGameEnd(GameResult gameResult) {
        System.out.println(gameResult.getMessage());
        System.out.println("Choose what to do next");
        CommandRouter.printAvailableCommands();
        ticTacToeApp.commandMode();
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
