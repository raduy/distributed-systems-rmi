package pl.agh.sr.rmi;

import pl.agh.sr.rmi.bot.IBotLogic;

import java.rmi.RemoteException;
import java.util.UUID;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class Bot implements RealPlayer {

    private final String nickName = generateNickName();
    private final IBoard board;
    private final IBotLogic botLogic;

    public Bot(IBoard board, IBotLogic botLogic) {
        this.board = board;
        this.botLogic = botLogic;
    }

    private String generateNickName() {
        return "Bot" + UUID.randomUUID().toString();
    }

    @Override
    public String getNickName() throws RemoteException {
        return nickName;
    }

    @Override
    public boolean isReady() throws RemoteException {
        return true;
    }

    @Override
    public void markReady() throws RemoteException {
        //pass
    }

    @Override
    public void onMove() throws RemoteException {
        while (!Thread.interrupted()) {
            try {
                int fieldNo = botLogic.nextMove();

                board.mark(fieldNo, this);
                return;
            } catch (Exception e) {
                //retry
            }
        }
    }

    @Override
    public void onGameStart() throws RemoteException {
        //pass
    }

    @Override
    public void onBoardUpdated() throws RemoteException {
        //pass
    }

    @Override
    public void onWin() throws RemoteException {
        //pass
    }

    @Override
    public void onLoose() throws RemoteException {
        //pass
    }

    @Override
    public void onDraw() throws RemoteException {
        //pass
    }
}
