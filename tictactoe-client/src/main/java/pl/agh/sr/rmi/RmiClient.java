package pl.agh.sr.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import static pl.agh.sr.rmi.Config.*;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class RmiClient {
    private static final Logger log = LoggerFactory.getLogger(RmiClient.class);

    private final String rmiRegistryAddress;
    private App app;

    public RmiClient(String rmiIp, int rmiPort) {
        this.rmiRegistryAddress = buildRbiRegistryAddress(rmiIp, rmiPort);

        setupSecurityManager();
        connectToServer();
    }

    private String buildRbiRegistryAddress(String rmiIp, int rmiPort) {
        return String.format(RMI_ADDRESS_FORMAT, rmiIp, rmiPort);
    }

    private void connectToServer() {
        try {
            this.app = (App) Naming.lookup(rmiRegistryAddress + "/" + APP);
        } catch (Exception e) {
            log.error("Error connecting to server", e);
        }
    }

    private void setupSecurityManager() {
        if (System.getProperty("java.security.policy") == null) {
            System.setProperty("java.security.policy",
                    "/home/raduy/Dropbox/Development/IdeaProjects/distributed-systems-rmi/tictactoe-client/target/classes/client.policy");
        }

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    }

    public App getApp() {
        return app;
    }

    public IRoom createNewRoom(IPlayer player) throws RemoteException, NotBoundException, MalformedURLException {
        IPlayer stub = (IPlayer) UnicastRemoteObject.exportObject(player, 0);
        return app.createNewRoom(stub);
    }

    public IRoom createNewBotRoom(IPlayer player) throws RemoteException, NotBoundException, MalformedURLException {
        return app.createNewBotRoom(player);
    }

    public IBoard loadBoard(IRoom newRoom) {
        try {
            String roomId = newRoom.getId().toString();
            String roomRMIAddress = rmiRegistryAddress + "/" + ROOM + "/" + roomId + "/" + BOARD;

            return (IBoard) Naming.lookup(roomRMIAddress);
        } catch (Exception e) {
            log.error("Error loading board", e);
            return null;
        }
    }
}
