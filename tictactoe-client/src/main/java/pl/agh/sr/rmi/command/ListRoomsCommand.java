package pl.agh.sr.rmi.command;

import pl.agh.sr.rmi.*;

import java.rmi.RemoteException;
import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class ListRoomsCommand implements ICommand {
    public static final String INVOCATION_PREFIX = "-l";
    public static final String DESCRIPTION = "Lists all existing rooms";
    private RmiClient rmiClient;
    private TicTacToeApp ticTacToeApp;

    public ListRoomsCommand(RmiClient rmiClient, TicTacToeApp ticTacToeApp) {
        this.rmiClient = rmiClient;
        this.ticTacToeApp = ticTacToeApp;
    }

    @Override
    public void execute() {
        App app = rmiClient.getApp();
        try {
            Set<IRoom> rooms = app.listRooms();

            for (IRoom room : rooms) {
                System.out.printf("Room of id: %s with players:\n", room.getId());
                printPlayers(room);
            }

            ticTacToeApp.commandMode();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void printPlayers(IRoom room) throws RemoteException {
        for (RealPlayer player : room.players()) {
            System.out.printf("\t%s\n", player.getNickName());
        }
    }
}
