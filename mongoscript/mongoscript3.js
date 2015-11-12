//masukkan ke tweet
var x = UUID("12345678901234567890123456789012")
db.tweets.insert({tweet_id : x, username: "willy", body: "halo halo bandung"})

//masukkan ke userline sendiri
db.userline.insert({username: "willy", tweet_id: x, time: NumberLong(ISODate().getTime())})

//masukkan ke timeline sendiri
db.timeline.insert({username: "willy", tweet_id: x, time: NumberLong(ISODate().getTime())})

//masukkan ke timeline orang lain
db.followers.find({follower: "willy"}).forEach(function(followers) {
	db.timeline.insert({username: followers.username, tweet_id: x, time: NumberLong(ISODate().getTime())})
})