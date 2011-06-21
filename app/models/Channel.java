package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;

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
	public Game nextGame;

	@OneToMany(mappedBy = "channel", cascade = CascadeType.ALL)
	public List<Comment> comments;

	public Channel(User author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;

		this.comments = new ArrayList<Comment>();
		this.observer = 0;
		this.game = new Game();
		this.nextGame = new Game();
	}

	@Override
	public String toString() {
		return title;
	}

	public Channel addComment(String author, String content) {
		Comment newComment = new Comment(this, author, content).save();
		this.comments.add(newComment);
		this.save();
		return this;
	}

}
