package jobs;

import events.ResultEvent;
import models.Channel;
import models.ChannelList;
import models.Game;
import play.Logger;
import play.jobs.Job;

import java.util.List;

public class SaveResult extends Job {

    private final String onlineId;
    private final String server;
    private final String result;

    public SaveResult(String server, String onlineId, String result) {
        this.server = server;
        this.onlineId = onlineId;
        this.result = result;
    }

    @Override
    public void doJob() throws Exception {
        Game game = Game.findByServerHostAndOnlineId(server, onlineId);
        if (game == null) {
            Logger.warn("game %s %s doesn't exists", server, onlineId);
            return;
        } else {
            if (result != null && result.isEmpty() == false) {
                game.result = result;
                game.onlineId = null;
                game.save();
                Logger.info("save game %s result %s", onlineId, result);

                // publish result
                ResultEvent re = new ResultEvent();
                re.message = result;
                ChannelList.publishEvent(game, re);

                // initialize channel update
                List<Channel> channels = Channel.findByGame(game);
                for (Channel channel : channels) {
                    if (channel.next != null) {
                        new NextGame(channel.number).in("10s");
                    }
                }

                // remove upcoming references of this game
                channels = Channel.findByNext(game);
                for (Channel channel : channels) {
                    channel.next = null;
                    channel.save();
                }
            }
        }
    }

}
