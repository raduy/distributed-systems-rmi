package pl.agh.sr.rmi;

import com.google.common.collect.ImmutableSet;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class RoomsRepository {
    private final Map<RoomId, IRoom> rooms = new ConcurrentHashMap<RoomId, IRoom>();

    public synchronized IRoom load(RoomId roomId) {
        return rooms.get(roomId);
    }

    public synchronized void addRoom(IRoom newRoom) {
        try {
            if (rooms.containsKey(newRoom.getId())) {
                return;
            }
            rooms.put(newRoom.getId(), newRoom);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Set<IRoom> immutableView() {
        return new HashSet<IRoom>(rooms.values());
//        return ImmutableSet.copyOf(rooms.values());
    }
}
