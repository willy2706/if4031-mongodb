package if4031.client.model.response;

import if4031.client.model.Tweet;
import if4031.client.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nim_13512065 on 11/9/15.
 */
public class DisplayTweetResponse implements MongoModelResponse {
    private Map<User,List<Tweet>> userListMap;

    public DisplayTweetResponse() {
        setUserListMap(new HashMap<User, List<Tweet>>());
    }

    public DisplayTweetResponse(Map<User,List<Tweet>> mapList) {
        setUserListMap(mapList);
    }

    public Map<User,List<Tweet>> getTweetResponse() {
        return userListMap;
    }

    public void setUserListMap(Map<User, List<Tweet>> userListMap) {
        this.userListMap = userListMap;
    }

    public void addTweet(User user, List<Tweet> tweets) {
        userListMap.put(user, tweets);
    }
}
