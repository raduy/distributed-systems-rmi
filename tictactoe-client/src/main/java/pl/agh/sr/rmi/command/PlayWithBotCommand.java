package pl.agh.sr.rmi.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agh.sr.rmi.IRoom;
import pl.agh.sr.rmi.RmiClient;
import pl.agh.sr.rmi.TicTacToeApp;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class PlayWithBotCommand implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(PlayWithBotCommand.class);

    public static final String INVOCATION_PREFIX = "-b";
    public static final String DESCRIPTION = "Creates a new room with bot";
    private final TicTacToeApp app;
    private final RmiClient rmiClient;

    public PlayWithBotCommand(TicTacToeApp app, RmiClient rmiClient) {
        this.app = app;
        this.rmiClient = rmiClient;
    }

    @Override
    public void execute() {
        try {
            IRoom newRoom = rmiClient.createNewBotRoom(app.getPlayer());
            app.gameMode(newRoom);
            newRoom.markPlayerReady(app.getPlayer());
        } catch (Exception e) {
            log.error("Error execution command", e);
        }
    }
}
