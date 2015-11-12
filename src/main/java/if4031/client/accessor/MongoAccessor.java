package if4031.client.accessor;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import if4031.client.model.request.*;
import if4031.client.model.response.DisplayTimelineResponse;
import if4031.client.model.response.DisplayTweetResponse;
import if4031.client.util.TableName;

public class MongoAccessor implements Accessor {
    private final MongoDatabase mongoDatabase;
    private final MongoCollection<BasicDBObject> userCollection;
    private final MongoCollection<BasicDBObject> followerCollection;
    private final MongoCollection<BasicDBObject> friendCollection;

    public MongoAccessor(String brokerAddress, String zookeeperAddress) {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        mongoDatabase = mongoClient.getDatabase("test");
        userCollection = getMongoDatabase().getCollection(TableName.USERS, BasicDBObject.class);
        followerCollection = getMongoDatabase().getCollection(TableName.FOLLOWERS, BasicDBObject.class);
        friendCollection = getMongoDatabase().getCollection(TableName.FRIENDS, BasicDBObject.class);
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
                .append("follower", followUserRequest.getFollower());
        getFollowerCollection().insertOne(detailUser);
        getFriendCollection().insertOne(detailUser);
    }

    public void tweet(AddTweetRequest addTweetRequest) {

    }

    public DisplayTweetResponse displayTweet(DisplayTweetRequest displayTweetRequest) {
//        DisplayTweetResponse displayTweetResponse = new DisplayTweetResponse();
//        return displayTweetResponse;
        return null;
    }

    public DisplayTimelineResponse displayTimeline(DisplayTimelineRequest displayTimeline) {

        DisplayTimelineResponse displayTimelineResponse = new DisplayTimelineResponse();

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
}
