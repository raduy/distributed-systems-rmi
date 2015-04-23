package pl.agh.sr.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class RmiClient {
    private static final Logger log = LoggerFactory.getLogger(RmiClient.class);

    private static final String RMI_REGISTRY_ADDRESS = "rmi://127.0.0.1:1099";
    private static final String BOARD = "board";
    private static final String ROOM = "room";
    private static final String APP = "app";

    private App app;
    private final TicTacToeApp ticTacToeApp;

    public RmiClient(TicTacToeApp ticTacToeApp) {
        System.setProperty("java.security.policy",
                "/home/raduy/Dropbox/Development/IdeaProjects/distributed-systems-rmi/tictactoe-client/src/main/resources/client.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        this.ticTacToeApp = ticTacToeApp;
        try {
            this.app = connectToServer();
        } catch (Exception e) {
            log.error("Error connecting to server", e);
        }
    }

    public App getApp() {
        return app;
    }

    public IRoom createNewRoom(RealPlayer player) throws RemoteException, NotBoundException, MalformedURLException {
        RealPlayer stub = (RealPlayer) UnicastRemoteObject.exportObject(player, 0);
        return app.createNewRoom(stub);
    }

    public IRoom createNewBotRoom(RealPlayer player) throws RemoteException, NotBoundException, MalformedURLException {
        return app.createNewBotRoom(player);
    }

    private App connectToServer() throws NotBoundException, MalformedURLException, RemoteException {
        return (App) Naming.lookup(RMI_REGISTRY_ADDRESS + "/" + APP);
    }

    public IBoard loadBoard(IRoom newRoom) {
        try {
            String roomId = newRoom.getId().toString();
            String roomRMIAddress = RMI_REGISTRY_ADDRESS + "/" + ROOM + "/" + roomId + "/" + BOARD;

            return (IBoard) Naming.lookup(roomRMIAddress);
        } catch (Exception e) {
            log.error("Error loading board", e);
            return null;
        }
    }
}
