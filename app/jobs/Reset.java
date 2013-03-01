package jobs;

import models.Channel;
import models.ChannelList;
import models.User;
import play.jobs.Job;
import play.test.Fixtures;

import java.util.List;

/**
 * resets the database and update the channel streams
 */
public class Reset extends Job {

    private final String userName;

    public Reset(String userName) {
        this.userName = userName;
    }

    public void doJob() {
        try {

            // cache the actual connected account
            User actual = User.findByName(userName);
            User clone = (User) actual.clone();

            // reset database
            Fixtures.deleteDatabase();
            Fixtures.loadModels("initial-data.yml");

            // persist the cached account
            clone.save();

            // update channel lists
            List<Channel> channels = Channel.findAll();
            for (Channel channel : channels) {

                // remove the actual and next game
                channel.game = null;
                channel.next = null;
                channel.save();

                // create an event stream
                if (ChannelList.instance.getStream(channel.number) == null) {
                    ChannelList.instance.addStream(channel);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
