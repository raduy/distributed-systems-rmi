package pl.agh.sr.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class RmiClient {
    private static final Logger log = LoggerFactory.getLogger(RmiClient.class);

    private static final String RMI_REGISTRY_ADDRESS = "rmi://127.0.0.1:1099";
    private static final String BOARD = "board";
    private static final String ROOM = "room";
    private static final String APP = "app";


    public static App connectToServer() throws NotBoundException, MalformedURLException, RemoteException {
        return (App) Naming.lookup(RMI_REGISTRY_ADDRESS + "/" + APP);
    }

    public IBoard loadBoard(Room newRoom) {
        String roomRMIAddress = RMI_REGISTRY_ADDRESS + "/" + ROOM + "/" + newRoom.getId().toString() + "/" + BOARD;
        try {
            return (IBoard) Naming.lookup(roomRMIAddress);
        } catch (Exception e) {
            log.error("Error loading board", e);
            return null;
        }
    }
}
