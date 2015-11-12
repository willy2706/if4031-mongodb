Eric (13512021)
Willy (13512065)

# Cara menjalankan program
1. Clone repo https://github.com/willy2706/if4031-mongodb
```
git clone https://github.com/willy2706/if4031-mongodb
```

2. Masuk ke folder program
```
cd if4031-mongodb
```

3. Build dependencies
```
gradle build
```

4. Buka project dengan Intellij IDEA

5. Run class src/main/java/if4031/client/ClientProgram.java

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
db.followers.insert({username: "a", follower: "b", since :NumberLong(ISODate().getTime())})
```

3. Tweet dengan id UUID("12345678901234567890123456789012"), username "willy" dan body "halo halo bandung"
```
var x = UUID("12345678901234567890123456789012")
//masukkan ke tweet
db.tweets.insert({tweet_id : x, username: "willy", body: "halo halo bandung"})
//masukkan ke userline sendiri
db.userline.insert({username: "willy", tweet_id: x, time: NumberLong(ISODate().getTime())})
//masukkan ke timeline sendiri
db.timeline.insert({username: "willy", tweet_id: x, time: NumberLong(ISODate().getTime())})
//masukkan ke timeline orang lain
db.followers.find({follower: "willy"}).forEach(function(followers) {
	db.timeline.insert({username: followers.username, tweet_id: x, time: NumberLong(ISODate().getTime())})
})
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