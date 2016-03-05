var express = require('express');
var app = express();

app.use('/', express.static(__dirname + '/'));

app.all('/*', function(req, res, next) {
  res.sendFile('index.html', { root: __dirname });
});

app.listen(9000);
console.log('Listening to localhost:9000');
