package pl.agh.sr.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agh.sr.rmi.board.Sign;

import java.rmi.RemoteException;
import java.util.Random;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class BoardImpl implements IBoard {
    private static final Logger log = LoggerFactory.getLogger(BoardImpl.class);

    public static final int BOARD_SIZE = 3;
    private RealPlayer crossPlayer;
    private RealPlayer circlePlayer;
    private RealPlayer nextMovePlayer;
    private Sign nextMoveSign;
    private int doneMovesCounter;

    public void beginGame(RealPlayer crossPlayer, RealPlayer circlePlayer) throws RemoteException {
        this.crossPlayer = crossPlayer;
        this.circlePlayer = circlePlayer;

        drawNextMovePlayer();
        nextMovePlayer.onMove();
    }

    private void drawNextMovePlayer() throws RemoteException {
        Random random = new Random(System.currentTimeMillis());
        boolean circleStarts = random.nextBoolean();

        nextMovePlayer = circleStarts ? circlePlayer : crossPlayer;

        if (circleStarts) {
            nextMovePlayer = circlePlayer;
            nextMoveSign = Sign.O;
        } else {
            nextMovePlayer = crossPlayer;
            nextMoveSign = Sign.X;
        }
    }

    private void toggleNextMovePlayer() {
        if (nextMovePlayer == crossPlayer) {
            nextMovePlayer = circlePlayer;
            nextMoveSign = Sign.O;
        } else {
            nextMovePlayer = crossPlayer;
            nextMoveSign = Sign.X;
        }
    }

    private final Sign[][] board = new Sign[BOARD_SIZE][BOARD_SIZE];

    public BoardImpl() {
        initBoard();
    }

    private void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Sign.NONE;
            }
        }
    }

    @Override
    public String show() throws RemoteException {
        StringBuilder builder = new StringBuilder();
        char[] fieldNumbers = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};

        int index = 0;
        for (Sign[] Signs : board) {
            for (Sign sign : Signs) {
                if (Sign.NONE.equals(sign)) {
                    builder.append(fieldNumbers[index]);
                } else {
                    builder.append(sign);
                }
                builder.append(" ");
                index++;
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    @Override
    public void mark(int fieldNo, RealPlayer player) throws RemoteException {
//        if (nextMovePlayer != player) {
//            return; //nice try
//        }

        fieldNo--;
        int yPos = fieldNo % BOARD_SIZE;
        int xPos = fieldNo / BOARD_SIZE;

        if (!Sign.NONE.equals(board[xPos][yPos])) {
            throw new IllegalArgumentException("Field now allow exception");
        }

        board[xPos][yPos] = nextMoveSign;
        doneMovesCounter++;
        log.debug("Board marked by {} on {}-{}", player.getNickName(), xPos, yPos);

        notifyPlayers();

        if (weHaveWinner()) {
            player.onWin();
            toggleNextMovePlayer();
            nextMovePlayer.onLoose();
            return;
        }

        if (itWasTheLastPossibleMove()) {
            crossPlayer.onDraw();
            circlePlayer.onDraw();
            return;
        }

        toggleNextMovePlayer();
        nextMovePlayer.onMove();
    }

    private boolean itWasTheLastPossibleMove() {
        return doneMovesCounter == BOARD_SIZE * BOARD_SIZE;
    }

    private boolean weHaveWinner() {
        if (checkRows()) return true;
        if (checkColumns()) return true;
        if (checkCrossLines()) return true;

        return false;
    }

    private boolean checkCrossLines() {
        int length = board.length;
        int left = 0;
        int right = 0;
        for (int i = 0; i < length; i++) {
            left += board[i][i].value();
            right += board[length - i - 1][length - i - 1].value();
        }

        return isWinningLine(left) || isWinningLine(right);
    }

    private boolean checkRows() {

        for (int i = 0; i < board.length; i++) {
            int sum = 0;
            for (int j = 0; j < board[i].length; j++) {
                sum += board[i][j].value();
            }

            if (isWinningLine(sum)) {
                return true;
            }
        }
        return false;
    }

    private boolean isWinningLine(int sum) {
        return Math.abs(sum) == BOARD_SIZE;
    }

    private boolean checkColumns() {
        for (int i = 0; i < board.length; i++) {
            int sum = 0;
            for (int j = 0; j < board[i].length; j++) {
                sum += board[j][i].value();
            }

            if (isWinningLine(sum)) {
                return true;
            }
        }
        return false;
    }

    private void notifyPlayers() throws RemoteException {
        circlePlayer.onBoardUpdated();
        crossPlayer.onBoardUpdated();
    }
}
