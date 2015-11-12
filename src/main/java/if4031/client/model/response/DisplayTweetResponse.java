package if4031.client.model.response;

import if4031.client.model.Tweet;
import if4031.client.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by nim_13512065 on 11/9/15.
 */
public class DisplayTweetResponse {
    private Map<User,List<Tweet>> userListMap;

    public DisplayTweetResponse(Map<User,List<Tweet>> mapList) {
        setUserListMap(mapList);
    }

    public Map<User,List<Tweet>> getTweetResponse() {
        return userListMap;
    }

    public void setUserListMap(Map<User, List<Tweet>> userListMap) {
        this.userListMap = userListMap;
    }
}
