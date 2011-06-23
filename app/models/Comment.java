package models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import play.data.validation.MaxSize;
import play.data.validation.Required;

@Entity
public class Comment extends TemporalModel {

	@Required
	public String author;

	@Lob
	@Required
	@MaxSize(1000)
	public String content;

	@ManyToOne(fetch = FetchType.LAZY)
	public Channel channel;

	public Comment(Channel channel, String author, String content) {
		this.channel = channel;
		this.author = author;
		this.content = content;
	}

	@Override
	public String toString() {
		return channel + " " + author + " " + created;
	}

}
