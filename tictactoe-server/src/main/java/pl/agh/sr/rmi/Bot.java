package pl.agh.sr.rmi;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class Bot implements Player {

    private final String nickName;

    public Bot(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public boolean isReady() {
        return false;
    }
}
