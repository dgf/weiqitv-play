package models;

import play.data.validation.Required;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import java.util.List;

@Entity
public class Channel extends TemporalModel {

    @Required
    @Column(unique = true)
    public int number;

    @Required
    @Column(unique = true, nullable = false)
    public String title;

    @Required
    @ManyToOne
    public Criteria criteria;

    @Required
    @Column(nullable = false)
    public String content;

    @Required
    @ManyToOne
    public User author;

    public int observer;

    @ManyToOne
    public Game game;

    @ManyToOne
    public Game next;

    public Channel(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;

        this.observer = 0;
        this.game = new Game();
        this.next = new Game();
    }

    @Override
    public String toString() {
        return title;
    }

    @PostPersist
    public void createChannelStream() {
        ChannelList.instance.addStream(this);
    }

    public static List<Channel> findByGame(Game game) {
        return Channel.find("game", game).fetch();
    }

    public static List<Channel> findByNext(Game game) {
        return Channel.find("next", game).fetch();
    }

    public static Channel findByNumber(int number) {
        return Channel.find("byNumber", number).first();
    }

    public static List<Channel> allByNumber() {
        return Channel.find("order by number").fetch();
    }

    public static List<Channel> findByCriteria(Criteria criteria) {
        return Channel.find("criteria", criteria).fetch();
    }
}
