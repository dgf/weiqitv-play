package models;

import play.data.validation.Required;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("Move")
public class Move extends Turn {

    @Required
    @Column(length = 4)
    public String coordinate;

    @OneToMany(mappedBy = "move", cascade = CascadeType.ALL)
    public List<Prisoner> prisoners;

    @Override
    public String toString() {
        return player + " " + coordinate + " " + seconds + " " + byo;
    }

    public static List<Move> findByGameId(long id) {
        return Move.find("game_id = ? order by number", id).fetch();
    }

    public static Move findByGameAndNumber(Game game, int number) {
        return Move.find("byGameAndNumber", game, number).<Move>first();
    }
}
