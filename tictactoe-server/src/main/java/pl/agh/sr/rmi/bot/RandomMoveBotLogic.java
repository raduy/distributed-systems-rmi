package pl.agh.sr.rmi.bot;

import pl.agh.sr.rmi.BoardImpl;

import java.io.Serializable;
import java.util.Random;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class RandomMoveBotLogic implements IBotLogic, Serializable {

    private Random random = new Random(System.currentTimeMillis());

    @Override
    public int nextMove() {
        return random.nextInt(BoardImpl.BOARD_SIZE * BoardImpl.BOARD_SIZE + 1);
    }
}
