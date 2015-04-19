package pl.agh.sr.rmi;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public abstract class Player {
    private final String nickName;

    public Player(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (nickName != null ? !nickName.equals(player.nickName) : player.nickName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nickName != null ? nickName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickName='" + nickName + '\'' +
                '}';
    }
}
