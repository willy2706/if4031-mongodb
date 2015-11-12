package if4031.client.model.request;

import if4031.client.command.mongodb.AddTweetMongoCommand;

import java.util.UUID;

/**
 * Created by nim_13512065 on 11/8/15.
 */
public class AddTweetRequest implements MongoModelRequest {
    private final String username;
    private final String body;
    private final UUID tweet_id;
    private final UUID time;
    public AddTweetRequest(AddTweetMongoCommand addTweetMongoCommand) {
        username = addTweetMongoCommand.getUsername();
        body = addTweetMongoCommand.getBody();
        tweet_id = UUID.randomUUID();
        time = UUID.randomUUID(); //TODO change
    }

    public String getUsername() {
        return username;
    }

    public String getBody() {
        return body;
    }

    public UUID getTweet_id() {
        return tweet_id;
    }

    public UUID getTime() {
        return time;
    }
}
