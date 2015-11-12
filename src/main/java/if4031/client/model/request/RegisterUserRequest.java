package if4031.client.model.request;

import if4031.client.command.cassandra.RegisterUserCassandraCommand;

/**
 * Created by nim_13512065 on 11/8/15.
 */
public class RegisterUserRequest implements CassandraModelRequest {
    private String username;
    private String password;

    public RegisterUserRequest(RegisterUserCassandraCommand registerUserCassandraCommand) {
        this.setUsername(registerUserCassandraCommand.getUsername());
        this.setPassword(registerUserCassandraCommand.getPassword());
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
