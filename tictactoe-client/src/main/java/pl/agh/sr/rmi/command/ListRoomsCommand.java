package pl.agh.sr.rmi.command;

import pl.agh.sr.rmi.App;
import pl.agh.sr.rmi.IRoom;
import pl.agh.sr.rmi.RealPlayer;
import pl.agh.sr.rmi.RmiClient;

import java.rmi.RemoteException;
import java.util.Set;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class ListRoomsCommand implements ICommand {
    public static final String INVOCATION_PREFIX = "-l";
    public static final String DESCRIPTION = "Lists all existing rooms";
    private RmiClient rmiClient;

    public ListRoomsCommand(RmiClient rmiClient) {
        this.rmiClient = rmiClient;
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
