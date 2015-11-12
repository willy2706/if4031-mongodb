db.users.find().forEach (function (user) {
	print(user.username)
	var i = 1;
	db.timeline.find({username:user.username}).forEach(function (timeline) {
		var mytweet = db.tweets.findOne({tweet_id:timeline.tweet_id})
		print(i + ". [" + timeline.time +"] "+ mytweet.body)
		++i
	})
})
