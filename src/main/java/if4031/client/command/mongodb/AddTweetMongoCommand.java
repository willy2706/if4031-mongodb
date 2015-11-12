package if4031.client.command.mongodb;

/**
 * Created by nim_13512065 on 11/8/15.
 */
public class AddTweetMongoCommand implements MongoCommand {
    private String username;
    private String body;

    public AddTweetMongoCommand(String username, String body) {
        this.setUsername(username);
        this.setBody(body);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
