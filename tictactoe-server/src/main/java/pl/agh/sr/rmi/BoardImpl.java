package pl.agh.sr.rmi;

import java.rmi.RemoteException;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class BoardImpl implements IBoard {

    @Override
    public String sayHello() throws RemoteException {
        return "sha bum";
    }
}
