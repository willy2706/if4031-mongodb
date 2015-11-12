package if4031.client.command.mongodb;

import java.util.Date;

/**
 * Created by nim_13512065 on 11/8/15.
 */
public class FollowUserMongoCommand implements MongoCommand {
    private final String username;
    private final String follower;
    private final Date timestamp;

    public FollowUserMongoCommand(String username, String follower) {
        this.username = username;
        this.follower = follower;
        this.timestamp = new Date();
    }

    public String getUsername() {
        return username;
    }

    public String getFollower() {
        return follower;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
