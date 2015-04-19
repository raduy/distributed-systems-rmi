package pl.agh.sr.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public interface IBoard extends Remote {

    String sayHello() throws RemoteException;
}
