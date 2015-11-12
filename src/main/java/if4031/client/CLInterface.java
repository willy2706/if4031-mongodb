package if4031.client;

import if4031.client.accessor.MongoAccessor;
import if4031.client.command.*;
import if4031.client.command.mongodb.*;
import if4031.client.model.Timeline;
import if4031.client.model.Tweet;
import if4031.client.model.User;
import if4031.client.model.request.*;
import if4031.client.model.response.DisplayTimelineResponse;
import if4031.client.model.response.DisplayTweetResponse;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CLInterface {

    private final Scanner scanner;
    private final PrintStream out;
    private final MongoAccessor mongoAccessor;
    private final CommandParser commandParser = new CommandParser();

    CLInterface(Scanner _scanner, PrintStream _out, MongoAccessor _mongoAccessor) {
        scanner = _scanner;
        out = _out;
        mongoAccessor = _mongoAccessor;
        MENU[0] = "1. mendaftar user baru (register <username> <password>)\n";
        MENU[1] = "2. follow a friend (follow <username> <follower>)\n";
        MENU[2] = "3. menambah tweet (tweet <username> <body>)\n";
        MENU[3] = "4. menampilkan tweet per user (displaytweet)\n";
        MENU[4] = "5. menampilkan timeline per user (displaytimeline) \n";
        MENU[5] = "6. exit (exit)\n>>";
        for (int i = 0; i < 6; ++i) {
            COMMAND_PROMPT+=MENU[i];
        }
    }

    private void printMessages() {
//        List<Message> messageList = mongoAccessor.getMessages();
//        if (messageList != null) {
//            for (Message message : messageList) {
//                out.println("[" + message.getChannel() + "] " + message.getBody());
//            }
//        }
    }

    void run() {
        // display welcome message
        out.println(WELCOME_MESSAGE);

        // main loop
        String commandString;
        while (true) {
            out.print(COMMAND_PROMPT);
            commandString = scanner.nextLine();
//            CommandParser.ParseResult parseResult = commandParser.parse(commandString);
            CommandParser.ParseResultCassandra parseResultCassandra = commandParser.parseCassandraCommand(commandString);
            CommandParser.ParseStatus status = parseResultCassandra.getStatus();
            if (status == CommandParser.ParseStatus.OK) {
                MongoCommand cmd = parseResultCassandra.getMongoCommand();
                processCassandra(cmd);

            } else if (status == CommandParser.ParseStatus.EXIT) {
                break;
            }

            printMessages();
        }
    }

    void processCassandra(MongoCommand mongoCommand) {
        if (mongoCommand instanceof RegisterUserMongoCommand) {
            RegisterUserMongoCommand registerUserCassandraCommand = (RegisterUserMongoCommand) mongoCommand;
            RegisterUserRequest registerUserRequest = new RegisterUserRequest(registerUserCassandraCommand);
            mongoAccessor.registerUser(registerUserRequest);
        } else if (mongoCommand instanceof FollowUserMongoCommand) {
            FollowUserMongoCommand followUserCassandraCommand = (FollowUserMongoCommand) mongoCommand;
            FollowUserRequest followUserRequest = new FollowUserRequest(followUserCassandraCommand);
            mongoAccessor.followUser(followUserRequest);
        } else if (mongoCommand instanceof AddTweetMongoCommand) {
            AddTweetMongoCommand addTweetMongoCommand = (AddTweetMongoCommand) mongoCommand;
            AddTweetRequest addTweetRequest = new AddTweetRequest(addTweetMongoCommand);
            mongoAccessor.tweet(addTweetRequest);
        } else if (mongoCommand instanceof DisplayTweetMongoCommand) {
            DisplayTweetResponse displayTweetResponse = mongoAccessor.displayTweet(new DisplayTweetRequest());
            Map<User, List<Tweet>> userListMap = displayTweetResponse.getTweetResponse();
            for (Map.Entry<User, List<Tweet>> entry : userListMap.entrySet()) {
                System.out.println(entry.getKey().getUsername());
                List<Tweet> tweetList = entry.getValue();
                for (int i = 1; i <= tweetList.size(); ++i) {
                    out.println(i+". " + tweetList.get(i-1).getBody());
                }
            }
        } else if (mongoCommand instanceof DisplayTimelineMongoCommand) {
            DisplayTimelineResponse displayTimelineResponse = mongoAccessor.displayTimeline(new DisplayTimelineRequest());
            Map<User, Timeline> userListMap = displayTimelineResponse.getTimelineResponse();
            for (Map.Entry<User, Timeline> entry : userListMap.entrySet()) {
                System.out.println(entry.getKey().getUsername());
                List<Tweet> tweetList = entry.getValue().getTweets();
                for (int i = 1; i <= tweetList.size(); ++i) {
                    out.println(i+". [" + tweetList.get(i-1).getTime() +"] ["+ tweetList.get(i-1).getUsername() +"] " +tweetList.get(i-1).getBody());
                }
            }
        }
    }

    private static String PROGRAM_NAME = "MongoDB NoSQL";
    private static String WELCOME_MESSAGE = "Welcome to " + PROGRAM_NAME + "!\n";
    private static String[] MENU = new String[6];
    private static String COMMAND_PROMPT = "";
}
