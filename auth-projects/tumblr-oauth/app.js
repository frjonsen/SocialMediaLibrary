var express = require('express')
var session = require('express-session')

var Grant = require('grant-express')
var config = require('./config.json');
let yaml = require('yamljs');
var y = yaml.load('../../secrets.yaml');

config.tumblr.secret = y.TUMBLR.CONSUMERSECRET;
config.tumblr.key = y.TUMBLR.CONSUMERKEY;

var grant = new Grant(config);

var app = express()
app.use(session({secret: 'very secret'}))
app.use(grant);

app.get('/handle_tumblr_callback', function (req, res) {
  console.log(req.query)
  res.end(JSON.stringify(req.query, null, 2))
})

app.listen(3000, function () {
  console.log('Express server listening on port ' + 3000)
})