package if4031.client.command;

import if4031.client.command.cassandra.*;

public class CommandParser {


    public enum ParseStatus {
        OK, ERROR, EMPTY, EXIT
    }

    public class ParseResult {
        private final ParseStatus status;
        private final String reason;
        private final Command command;

        public ParseResult(ParseStatus _status, String _reason, Command _command) {
            status = _status;
            reason = _reason;
            command = _command;
        }

        public ParseStatus getStatus() {
            return status;
        }

        public String getReason() {
            return reason;
        }

        public Command getCommand() {
            return command;
        }
    }

    public class ParseResultCassandra {
        private final ParseStatus status;
        private final String reason;
        private final CassandraCommand cassandraCommand;

        public ParseResultCassandra(ParseStatus _status, String _reason, CassandraCommand _command) {
            status = _status;
            reason = _reason;
            cassandraCommand = _command;
        }

        public ParseStatus getStatus() {
            return status;
        }

        public String getReason() {
            return reason;
        }

        public CassandraCommand getCassandraCommand() {
            return cassandraCommand;
        }
    }

    /**
     * Parse IRC Command.
     * format of a command: /<command> <params> or @<channelName> <message> or \<message> or <message>
     * <command> = nick|join|leave|exit|refresh
     * for nick command, <params> = <nickname>
     * for join|leave command, <params> = <channelName>
     * for exit|refresh command, <params> = <EMPTY>
     * <nickname>|<channelName> = <WORD>
     * <message> = <STRING>
     *
     * @param line pre line.length() > 0, the line to be parsed
     * @return ParseResult
     */
    public ParseResult parse(String line) {
        if (line.equals("")) {
            return new ParseResult(ParseStatus.EMPTY, "", null);
        }

        // line must contains at least one character
        switch (line.charAt(0)) {
            /*
            handles line format: /<command> <params>
            <command> = nick|join|leave|exit|refresh
            for nick command, <params> = <nickname>
            for join|leave command, <params> = <channelName>
            for exit|refresh command, <params> = <EMPTY>
            <nickname>|<channelName> = <WORD>
            */
            case '/': {
                String[] tokens = line.substring(1).split("\\s+");
                if (tokens.length == 1) {
                    String command = tokens[0].toLowerCase();
                    if (command.equals("exit")) {
                        return new ParseResult(ParseStatus.EXIT, "", null);
                    }

                } else if (tokens.length == 2) {
                    String command = tokens[0].toLowerCase();
                    if (command.equals("nick")) {
                        String newNickname = tokens[1];
                        return new ParseResult(ParseStatus.OK, OK_TEXT, new ChangeNicknameCommand(newNickname));
                    }
                    if (command.equals("join")) {
                        String channelName = tokens[1];
                        return new ParseResult(ParseStatus.OK, OK_TEXT, new JoinChannelCommand(channelName));

                    }
                    if (command.equals("leave")) {
                        String channelName = tokens[1];
                        return new ParseResult(ParseStatus.OK, OK_TEXT, new LeaveChannelCommand(channelName));
                    }
                }
                return new ParseResult(ParseStatus.ERROR, ERROR_COMMAND_NOTFOUND, null);
            }

            /*
            handles line format: @<channelName> <message>
            <channelName> = <WORD>
            <message> = <STRING>
            */
            case '@': {
                // split string in two with the first occurrence of a space, discarding the space itself.
                int firstSpaceIdx = line.indexOf(" ");
                String channelName = line.substring(1, firstSpaceIdx);
                String message = line.substring(firstSpaceIdx + 1);

                if (channelName.equals("")) {
                    return new ParseResult(ParseStatus.ERROR, ERROR_EMPTY_CHANNELNAME, null);
                }
                if (message.equals("")) {
                    return new ParseResult(ParseStatus.ERROR, ERROR_EMPTY_MESSAGE, null);
                }

                return new ParseResult(ParseStatus.OK, OK_TEXT, new SendMessageChannel(channelName, message));
            }

            /*
            handles line format: \<message>
            <message> = <STRING>
            */
            case '\\': {
                String message = line.substring(1);
                if (message.equals("")) {
                    return new ParseResult(ParseStatus.ERROR, ERROR_EMPTY_MESSAGE, null);
                }

                return new ParseResult(ParseStatus.OK, OK_TEXT, new SendMessageAll(message));
            }

            /*
            handles line format: <message>
            <message> = <STRING>
            */
            default:
                return new ParseResult(ParseStatus.OK, OK_TEXT, new SendMessageAll(line));
        }
    }

    public ParseResultCassandra parseCassandraCommand(String line) {
        if (line.equals("")) {
            return new ParseResultCassandra(ParseStatus.EMPTY, "", null);
        }
        /*
        handles line format: <command> <params>
        <command> = register|follow|tweet|displaytweet|displaytimeline|exit
        for register command, <params> = <username>
        for follow command, <params> = <username>, <follower>
        for tweet command, <params> = <username>, <tweet>
        for displaytweet|displaytimeline|exit command, <params> = <EMPTY>
        <username>|<tweet>|<follower> = <WORD>
        */
        String[] tokens = line.substring(0).split("\\s+");
        String command = tokens[0].toLowerCase();
        if (command.equals("register")) {
            String username = tokens[1];
            String password = tokens[2];
            return new ParseResultCassandra(ParseStatus.OK, OK_TEXT, new RegisterUserCassandraCommand(username, password));
        } else if (command.equals("follow")) {
            String username = tokens[1];
            String follower = tokens[2];
            return new ParseResultCassandra(ParseStatus.OK, OK_TEXT, new FollowUserCassandraCommand(username, follower));
        } else if (command.equals("tweet")) {
            String username = tokens[1];
            int firstSpaceIdx = line.indexOf(" ");
            String body = line.substring(firstSpaceIdx + username.length() + 1 /*spasi*/ + 1);
            return new ParseResultCassandra(ParseStatus.OK, OK_TEXT, new AddTweetCassandraCommand(username, body));
        } else if (command.equals("displaytweet")) {
            return new ParseResultCassandra(ParseStatus.OK, OK_TEXT, new DisplayTweetCassandraCommand());
        } else if (command.equals("displaytimeline")) {
            return new ParseResultCassandra(ParseStatus.OK, OK_TEXT, new DisplayTimelineCassandraCommand());
        } else if (command.equals("exit")) {
            return new ParseResultCassandra(ParseStatus.EXIT, "", null);
        } else {
            return new ParseResultCassandra(ParseStatus.ERROR, ERROR_COMMAND_NOTFOUND, null);
        }
    }

    private static String OK_TEXT = "";
    private static String ERROR_EMPTY_MESSAGE = "Message is empty";
    private static String ERROR_COMMAND_NOTFOUND = "Command not found";
    private static String ERROR_EMPTY_CHANNELNAME = "Channel name is empty";
}
