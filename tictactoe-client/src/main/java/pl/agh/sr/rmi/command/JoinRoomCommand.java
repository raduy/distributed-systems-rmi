package pl.agh.sr.rmi.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agh.sr.rmi.*;

import java.rmi.RemoteException;
import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class JoinRoomCommand implements ICommand {
    private static final Logger log = LoggerFactory.getLogger(JoinRoomCommand.class);

    public static final String INVOCATION_PREFIX = "-j";
    public static final String DESCRIPTION = "<room id> Joins an existing room";
    private RmiClient rmiClient;
    private TicTacToeApp ticTacToeApp;
    private String cmd;

    public JoinRoomCommand(RmiClient rmiClient, TicTacToeApp ticTacToeApp, String cmd) {
        this.rmiClient = rmiClient;
        this.ticTacToeApp = ticTacToeApp;
        this.cmd = cmd;
    }

    @Override
    public void execute() {
        String roomIdPrefix = prepareRoomIdPrefix();

        App app = rmiClient.getApp();
        RealPlayer player = ticTacToeApp.getRemotePlayer();

        try {
            Set<IRoom> rooms = app.listRooms();

            for (IRoom room : rooms) {
                if (isRoomIdMatching(roomIdPrefix, room)) {
                    app.joinRoom(room.getId(), player);
                    ticTacToeApp.gameMode(room);
                    player.markReady();
                    room.markPlayerReady(player);
                    return;
                }
            }
            System.out.println("No matching room id! Try again");
        } catch (Exception e) {
            log.error("Error executing command", e);
        }
    }

    private boolean isRoomIdMatching(String roomIdPrefix, IRoom room) throws RemoteException {
        return room.getId().toString().startsWith(roomIdPrefix);
    }

    private String prepareRoomIdPrefix() {
        return cmd.split("\\s+")[1];
    }
}
