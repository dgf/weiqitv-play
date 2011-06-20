package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Comment extends Model {

	@Required
	public String author;

	@Required
	public Date postedAt;

	@Lob
	@Required
	@MaxSize(1000)
	public String content;

	@ManyToOne
	public Channel channel;

	public Comment(Channel channel, String author, String content) {
		this.channel = channel;
		this.author = author;
		this.content = content;
		this.postedAt = new Date();
	}

	@Override
	public String toString() {
		return channel + " " + author + " " + postedAt;
	}

}
