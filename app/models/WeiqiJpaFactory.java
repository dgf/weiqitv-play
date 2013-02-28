package models;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import static models.RankType.NR;

public class WeiqiJpaFactory {

    public static Criteria criteria(String name, int size, String minRank, String maxRank,
                                    int minHandicap, int maxHandicap) {
        Criteria c = new Criteria();
        c.name = name;
        c.size = Size.get(size);
        c.minRank = rank(minRank);
        c.maxRank = rank(maxRank);
        c.minHandicap = handicap(minHandicap);
        c.maxHandicap = handicap(maxHandicap);
        return c.save();
    }

    public static Game game(String white, String wRank, String black, String bRank, int size,
                            int handicap, float komi) {
        Game g = new Game();
        g.white = gamePlayer(white, wRank);
        g.black = gamePlayer(black, bRank);
        g.size = Size.get(size);
        g.handicap = handicap(handicap);
        g.komi = komi;
        return g.save();
    }

    public static GameServer server(String host, int port) {
        GameServer gs = GameServer.findByHostAndPort(host, port);
        if (gs == null) {
            gs = new GameServer();
            gs.host = host;
            gs.port = port;
            gs.save();
        }
        return gs;
    }

    public static GamePlayer gamePlayer(String name, String rank) {
        GamePlayer gp = new GamePlayer();
        gp.player = player(name);
        gp.rank = rank(rank);
        return gp; // cascade
    }

    public static Handicap handicap(int stones) {
        Handicap h = Handicap.find("stones", stones).first();
        if (h == null) {
            if (stones < 0 || stones == 2 || stones > 9) {

            }
            h = new Handicap();
            h.stones = stones;
            h.save();
        }
        return h;
    }

    public static Move move(Game game, int number, BlackOrWhite player, String coordinate,
                            int seconds, int byo, String... prisoners) {
        Move m = new Move();
        m.game = game;
        m.number = number;
        m.player = player;
        m.coordinate = coordinate;
        m.prisoners = prisoner(m, prisoners);
        m.seconds = seconds;
        m.byo = byo;
        return m.save();
    }

    public static Player player(String name) {
        Player p = Player.findByName(name);
        if (p == null) {
            p = new Player();
            p.name = name;
            p.save();
        }
        return p;
    }

    public static Rank rank(String nrAndTyp) {
        if (nrAndTyp == null || nrAndTyp.equals(NR.name())) {
            return rank(0, NR);
        }

        Matcher m = Rank.RANK_PATTERN.matcher(nrAndTyp);
        if (m.matches() == false) {
            throw new IllegalArgumentException("unknown rank " + nrAndTyp);
        } else {
            int nr = Integer.parseInt(m.group(1));
            RankType type = RankType.get(m.group(2));
            return rank(nr, type);
        }
    }

    public static Rank rank(int nr, RankType type) {
        Rank r = Rank.findByNrAndType(nr, type);
        if (r == null) {
            r = new Rank();
            r.nr = nr;
            r.type = type;
            r.save();
        }
        return r;
    }

    public static List<Prisoner> prisoner(Move move, String... prisoners) {
        return Prisoner.toPrisonerList(move, Arrays.asList(prisoners)); // cascade
    }

}
