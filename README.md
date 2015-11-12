Eric (13512021)
Willy (13512065)

# Kumpulan perintah untuk program

1. Tambah user
```
register <username> <password>
```
2. Follow user
```
follow <username> <follower>
```
3. Tweet
```
tweet <username> <body>
```
4. Tampilkan tweet per user
```
displaytweet
```
5. Tampilkan timeline per user
```
displaytimeline
```
6. Keluar
```
exit
```

# Kumpulan perintah untuk query database

1. Tambah user dengan username "a" dan password "ab"
```
db.users.insert({username: "a", password: "ab"})
```

2. Follow user dengan username "a" dan follower "b"
```
db.followers.insert({username: "a", follower: "b"})
```

3. Tweet dengan username "a" dan body "halo halo bandung"
```
ad
```

4. Tampilkan tweet per user
```
db.users.find().forEach (function (user) {
	print(user.username)
	var i = 1;
	db.tweets.find({username:user.username}).forEach(function (tweet) {
		print(i + ". " + tweet.body)
		++i
	})
})
```

5. Tampilkan timeline per user
```
db.users.find().forEach (function (user) {
	print(user.username)
	var i = 1;
	db.timeline.find({username:user.username}).forEach(function (timeline) {
		var mytweet = db.tweets.findOne({tweet_id:timeline.tweet_id})
		print(i + ". [" + timeline.time +"] "+ mytweet.body)
		++i
	})
})
```