package model;

import java.util.List;

import models.Comment;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class CommentTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("data.yml");
	}

	@Test
	public void count() throws Exception {
		assertEquals(4, Comment.count());
	}

	@Test
	public void getAllChannelCommentsByAuthor() {
		// Find all comments related to admin's channels
		List<Comment> c = Comment.find("channel.author.mail", "admin@g2d.de").fetch();
		assertEquals(4, c.size());
	}
}
