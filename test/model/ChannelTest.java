package model;

import java.util.List;

import models.Channel;
import models.Comment;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class ChannelTest extends UnitTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("data.yml");
	}

	@Test
	public void count() throws Exception {
		assertEquals(3, Channel.count());
	}

	@Test
	public void checkChannel() {

		List<Channel> channels = Channel.find("author.mail", "admin@g2d.de").fetch();
		assertEquals(3, channels.size());

		Channel actual = channels.get(0);
		assertNotNull(actual);
		assertEquals("pro games", actual.title);
	}

	@Test
	public void useTheCommentsRelation() {

		// remember overall comment count
		long count = Comment.count();

		// Retrieve the pro channel
		Channel channel = Channel.find("title", "pro games").first();
		assertNotNull(channel);

		// Navigate to comments
		assertEquals(3, channel.comments.size());
		assertEquals("Guest", channel.comments.get(0).author);

		// Delete the channel
		channel.delete();

		// Check that all comments have been deleted
		assertEquals(count - 3, Comment.count());
	}

}
