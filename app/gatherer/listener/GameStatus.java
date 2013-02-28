package gatherer.listener;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GameStatus {

    private String id;

    private GamePlayerStatus white;

    private GamePlayerStatus black;

    public GameStatus() {
    }

    public GameStatus(String id, GamePlayerStatus white, GamePlayerStatus black) {
        this.id = id;
        this.white = white;
        this.black = black;
    }

    public GamePlayerStatus getBlack() {
        return black;
    }

    public String getId() {
        return id;
    }

    public GamePlayerStatus getWhite() {
        return white;
    }

    public void setBlack(GamePlayerStatus black) {
        this.black = black;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWhite(GamePlayerStatus white) {
        this.white = white;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
