package if4031.client.command.cassandra;

import if4031.client.command.Command;

/**
 * Created by nim_13512065 on 11/8/15.
 */
public class RegisterUserCassandraCommand implements CassandraCommand {
    private String username;
    private String password;

    public RegisterUserCassandraCommand(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
