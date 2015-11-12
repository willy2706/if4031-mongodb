package if4031.client.model.request;

import if4031.client.command.mongodb.FollowUserMongoCommand;

import java.util.Date;

/**
 * Created by nim_13512065 on 11/8/15.
 */
public class FollowUserRequest implements MongoModelRequest {
    private final String username;
    private final String follower;
    private final Date timestamp;

    public FollowUserRequest(FollowUserMongoCommand followUserCassandraCommand) {
        this.username = followUserCassandraCommand.getUsername();
        this.follower = followUserCassandraCommand.getFollower();
        this.timestamp = followUserCassandraCommand.getTimestamp();
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
