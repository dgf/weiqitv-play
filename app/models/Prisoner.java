package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Prisoner extends Model {

    @Required
    @ManyToOne
    public Move move;

    @Required
    @Column(length = 3)
    public String coordinate;

    public Prisoner(Move move, String coordinate) {
        this.move = move;
        this.coordinate = coordinate;
    }

    public static List<Prisoner> toPrisonerList(Move move, List<String> coordinates) {
        List<Prisoner> list = new ArrayList<Prisoner>(coordinates.size());
        for (String coordinate : coordinates) {
            list.add(new Prisoner(move, coordinate));
        }
        return list;
    }

    public static List<String> toStringList(List<Prisoner> prisoners) {
        if (prisoners == null) {
            return null;
        } else {
            List<String> list = new ArrayList<String>(prisoners.size());
            for (Prisoner prisoner : prisoners) {
                list.add(prisoner.coordinate);
            }
            return list;
        }
    }
}
