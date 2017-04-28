let express = require('express');
let yaml = require('yamljs');
let app = express();

let readConfig = function() {
    let contents = yaml.load('../secrets.yaml');
    return contents;
}

app.use(express.static('front'));

app.get('/callback', function(req, res) {
    console.log(req);
});

app.post('/callback', function(req, res) {
    console.log(req);
});

app.get('/getsecret', function(req, res) {
    res.send(readConfig());
});
app.listen(5678);