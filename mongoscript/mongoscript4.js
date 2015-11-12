db.users.find().forEach (function (user) {
	print(user.username)
	var i = 1;
	db.tweets.find({username:user.username}).forEach(function (tweet) {
		print(i + ". " + tweet.body)
		++i
	})
})
