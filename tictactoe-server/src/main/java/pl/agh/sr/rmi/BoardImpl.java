package pl.agh.sr.rmi;

import java.rmi.RemoteException;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class BoardImpl implements IBoard {

    private final char[][] board = new char[3][3];

    public BoardImpl() {
        initBoard();
    }

    private void initBoard() {
        char[] fieldNumbers = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int index = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = fieldNumbers[index++];
            }
        }
    }

    @Override
    public String show() throws RemoteException {
        StringBuilder builder = new StringBuilder();
        for (char[] chars : board) {
            for (char aChar : chars) {
                builder.append(aChar).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public void mark(int fieldNo) throws RemoteException {

    }
}
