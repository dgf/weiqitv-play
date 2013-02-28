package gatherer;

public interface IgsConstants {

    public static final String IGS_SERVER = "igs.joyjoy.net";
    public static final int IGS_PORT = 7777;

    // client
    static final String LOGIN_PROMPT = "Login: ";
    static final String GUEST = "guest";
    static final String PROMPT = "#> ";
    static final String OK = "1 5";
    static final String TOGGLE = "toggle {0}";
    static final String TOGGLE_ON = "toggle {0} {1}";

    // games
    static final String GET_GAME_INFO = "game {0}";
    static final String PLAYER_PATTERN = "([^ ]+) \\[ *([0-9kdp]+|NR)[^\\]]*\\]";
    static final String GAME_HEADER = "7 [##]  white name [ rk ]      black name [ rk ] (Move size H Komi BY FR) (###)";

    // moves
    static final String MOVE_LIST_OK = "1 8";
    static final String MOVES = "moves {0}";
    static final String OBSERVED = "2 ";
    static final String OBSERVE_GAME = "observe {0}";
    static final String UNOBSERVE_GAME = "unobserve {0}";

}
