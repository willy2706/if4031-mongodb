package if4031.client.accessor;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import if4031.client.model.Timeline;
import if4031.client.model.Tweet;
import if4031.client.model.User;
import if4031.client.model.request.*;
import if4031.client.model.response.DisplayTimelineResponse;
import if4031.client.model.response.DisplayTweetResponse;
import if4031.client.util.TableName;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MongoAccessor implements Accessor {
    private final MongoDatabase mongoDatabase;
    private final MongoCollection<BasicDBObject> userCollection;
    private final MongoCollection<BasicDBObject> followerCollection;
    private final MongoCollection<BasicDBObject> friendCollection;
    private final MongoCollection<BasicDBObject> tweetCollection;
    private final MongoCollection<BasicDBObject> timelineCollection;
    private final MongoCollection<BasicDBObject> userlineCollection;

    public MongoAccessor(String brokerAddress, String zookeeperAddress) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        mongoDatabase = mongoClient.getDatabase("test");
        userCollection = getMongoDatabase().getCollection(TableName.USERS, BasicDBObject.class);
        followerCollection = getMongoDatabase().getCollection(TableName.FOLLOWERS, BasicDBObject.class);
        friendCollection = getMongoDatabase().getCollection(TableName.FRIENDS, BasicDBObject.class);
        tweetCollection = getMongoDatabase().getCollection(TableName.TWEETS, BasicDBObject.class);
        timelineCollection = getMongoDatabase().getCollection(TableName.TIMELINE, BasicDBObject.class);
        userlineCollection = getMongoDatabase().getCollection(TableName.USERLINE, BasicDBObject.class);
    }

    public void start() {
    }

    public void stop() {

    }

    public void registerUser(RegisterUserRequest registerUserRequest) {
        BasicDBObject detailUser = new BasicDBObject("username", registerUserRequest.getUsername())
                .append("password", registerUserRequest.getPassword())
                .append("_id", registerUserRequest.getUsername());
        getUserCollection().insertOne(detailUser);
    }

    public void followUser(FollowUserRequest followUserRequest) {
        BasicDBObject detailUser = new BasicDBObject("username", followUserRequest.getUsername())
                .append("follower", followUserRequest.getFollower())
                .append("since", System.currentTimeMillis());
        getFollowerCollection().insertOne(detailUser);
        getFriendCollection().insertOne(detailUser);
    }

    public void tweet(final AddTweetRequest addTweetRequest) {
        BasicDBObject detailTweet = new BasicDBObject("tweet_id", addTweetRequest.getTweet_id())
                .append("username", addTweetRequest.getUsername())
                .append("body", addTweetRequest.getBody());
        getTweetCollection().insertOne(detailTweet);

        BasicDBObject detailUserline = new BasicDBObject("tweet_id", addTweetRequest.getTweet_id())
                .append("username", addTweetRequest.getUsername())
                .append("time", System.currentTimeMillis());

        getUserlineCollection().insertOne(detailUserline);
        getTimelineCollection().insertOne(detailUserline);

        FindIterable<BasicDBObject> dbCursorFollower = followerCollection.find(new BasicDBObject("follower", addTweetRequest.getUsername()));
        dbCursorFollower.forEach(new Block<BasicDBObject>() {
            @Override
            public void apply(BasicDBObject basicDBObject) {
                String username = (String) basicDBObject.get("username");
                BasicDBObject detailTimeline = new BasicDBObject("tweet_id", addTweetRequest.getTweet_id())
                        .append("username", username)
                        .append("time", System.currentTimeMillis());
                getTimelineCollection().insertOne(detailTimeline);
            }
        });
    }

    public DisplayTweetResponse displayTweet(final DisplayTweetRequest displayTweetRequest) {
        final DisplayTweetResponse displayTweetResponse = new DisplayTweetResponse();
        FindIterable<BasicDBObject> dbCursorUser = userCollection.find();
        dbCursorUser.forEach(new Block<BasicDBObject>() {
            @Override
            public void apply(BasicDBObject userDBObject) {
                String username = (String) userDBObject.get("username");
                FindIterable<BasicDBObject> tweetResult = tweetCollection.find(new BasicDBObject("username", username));
                final List<Tweet> tweetList = new ArrayList<Tweet>();
                tweetResult.forEach(new Block<BasicDBObject>() {
                    @Override
                    public void apply(BasicDBObject tweetDBObject) {
                        String username = (String) tweetDBObject.get("username");
                        UUID tweet_id = (UUID) tweetDBObject.get("tweet_id");
                        String body = (String) tweetDBObject.get("body");
                        tweetList.add(new Tweet(username, null, tweet_id, body));
                    }
                });
                displayTweetResponse.addTweet(new User(username), tweetList);
            }
        });

        return displayTweetResponse;
    }

    public DisplayTimelineResponse displayTimeline(DisplayTimelineRequest displayTimeline) {
        final DisplayTimelineResponse displayTimelineResponse = new DisplayTimelineResponse();
        FindIterable<BasicDBObject> dbCursorUser = userCollection.find();
        dbCursorUser.forEach(new Block<BasicDBObject>() {
            @Override
            public void apply(BasicDBObject userDBObject) {
                String username = (String) userDBObject.get("username");
                FindIterable<BasicDBObject> timelineResult = timelineCollection.find(new BasicDBObject("username", username));
                final List<Tweet> tweetList = new ArrayList<Tweet>();
                timelineResult.forEach(new Block<BasicDBObject>() {
                    @Override
                    public void apply(BasicDBObject timelineDBObject) {
                        UUID tweet_id = (UUID) timelineDBObject.get("tweet_id");
                        Long time = (Long) timelineDBObject.get("time");
                        BasicDBObject tweetResult = tweetCollection.find(new BasicDBObject("tweet_id", tweet_id)).first();
                        String username = (String) tweetResult.get("username");
                        UUID tweet_id_uuid = (UUID) tweetResult.get("tweet_id");
                        String body = (String) tweetResult.get("body");
                        tweetList.add(new Tweet(username, time, tweet_id_uuid, body));
                    }
                });
                Timeline timeline = new Timeline(tweetList);
                displayTimelineResponse.addUserAndTweets(new User(username), timeline);
            }
        });
        return displayTimelineResponse;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public MongoCollection<BasicDBObject> getUserCollection() {
        return userCollection;
    }

    public MongoCollection<BasicDBObject> getFollowerCollection() {
        return followerCollection;
    }

    public MongoCollection<BasicDBObject> getFriendCollection() {
        return friendCollection;
    }

    public MongoCollection<BasicDBObject> getTweetCollection() {
        return tweetCollection;
    }

    public MongoCollection<BasicDBObject> getTimelineCollection() {
        return timelineCollection;
    }

    public MongoCollection<BasicDBObject> getUserlineCollection() {
        return userlineCollection;
    }
}
