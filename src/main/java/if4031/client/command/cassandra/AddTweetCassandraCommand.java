package if4031.client.command.cassandra;

import if4031.client.command.Command;

/**
 * Created by nim_13512065 on 11/8/15.
 */
public class AddTweetCassandraCommand implements CassandraCommand {
    private String username;
    private String body;

    public AddTweetCassandraCommand(String username, String body) {
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
