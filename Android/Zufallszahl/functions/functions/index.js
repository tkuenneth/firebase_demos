const functions = require('firebase-functions');

exports.zufallszahl = functions.https.onRequest((req, res) => {
	console.log(req.body);
	var max = req.body.data.max;
	var result = randomInt(max);
	res.contentType("application/json");
	res.status(200);
	var s = JSON.stringify({ 
		data: {
			max: max,
			result: result 
		}
	});
	console.log("Output: " + s);
	res.send(s);
});

function randomInt(max) {
	return Math.floor(Math.random() * Math.floor(max));
}
