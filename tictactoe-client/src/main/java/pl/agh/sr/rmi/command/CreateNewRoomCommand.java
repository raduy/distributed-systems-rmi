package pl.agh.sr.rmi.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agh.sr.rmi.TicTacToeApp;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class CreateNewRoomCommand implements ICommand {
    public static final String INVOCATION_PREFIX = "-c";
    public static final String DESCRIPTION = "Creates a new room";
    private static final Logger log = LoggerFactory.getLogger(CreateNewRoomCommand.class);
    private TicTacToeApp app;

    public CreateNewRoomCommand(TicTacToeApp app) {
        this.app = app;
    }

    @Override
    public void execute() {
        try {
            app.createNewRoom();
            app.gameMode();
        } catch (Exception e) {
            log.error("Error execution command", e);
        }
    }
}
