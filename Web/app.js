var express = require('express');
var bodyParser = require('body-parser');
var app = express();
var Person = require('./person.js')
var result = [];
var index = null;
var port = 5500;

app.use(bodyParser.urlencoded({extended: true})); 

app.get('/', function(req, res) {
    res.sendFile(`${__dirname}/index.html`);
});

app.get('/verify', function(req, res) {
    index = req.query.index;
    currPerson = result[index];
    if (currPerson.passcode == "") {
        res.render('form.ejs', {currPerson: currPerson});
    } else {
        //console.log(index);
        res.render(`${__dirname}/passcode.ejs`, {message: ""});
    }
});

app.post('/search', (req, res) => {
    console.log('here');
    // console.log(req.body);
    var query = {};

    if (req.body.fname) {
        //query = {"fname": req.body.fname};
        query.firstName = new RegExp(`^${req.body.fname}$`, 'i');
    }

    if (req.body.lname) {
        query.lastName = new RegExp(`^${req.body.lname}$`, 'i');
    }

    if (req.body.address) {
        query.address = new RegExp(`${req.body.address}`, 'i');
    }

    //console.log(query);
    if (Object.keys(query).length == 0) {
        //console.log("empty");
        res.render('empty.ejs');
        return;
    }

    Person.find(query, (err, persons) => {
        if (err) {
            console.log(err);
        }
        else {
            if (persons.length == 0) {
                console.log("no match");
                res.type('html').status(200);
                res.write('No match found from search inputs.');
                res.end();
                return;
            }
            // console.log("find" + persons);
            result = persons;
            // res.json(persons);
            res.render('search.ejs', {persons: persons});
        }
    })
});

app.use('/verify', (req, res) => {
    var currPerson = result[index];
    var passcode = req.body.passcode;

    if (passcode == currPerson.passcode) {
        res.render('form.ejs', {currPerson: currPerson});
    } else {
        res.render(`${__dirname}/passcode.ejs`, {message: "The passcode you entered is incorrect, " + "please try again"});
    }
    console.log("pass" + passcode);
    // console.log(result[index]);
    //res.json(passcode);
    //res.json(result[index]);
});

app.use('/get', (req, res) => {
    var username = req.query.username;
    var query = {};
    query.username = username
    Person.find(query, (err, persons) => {
        if (err) {
            console.log(err);
        } else {
            console.log("Getting stuff...")
            if (persons.length != 1) {
                res.json({});
            } else {
                res.json(persons[0]);
                console.log("got this person:" + persons[0].username);
                console.log("here is their form: " + persons[0].form);
            }
        }
    })
})

app.post('/setpost', (req, res) => {
    if (!req.body.person) {
        console.log("person not there")
    }
    var person = JSON.parse(req.body.person);
    console.log("setting person: " + person.username);
    var dbPerson = new Person(person);
    var username = person.username;
    var query = {};
    query.username = username;
    Person.find(query, (err, persons) => {
        if (err) {
            console.log(err);
        } else {
            console.log("Finding old stuff for res...")
            if (persons.length == 0) {
                res.json({});
                dbPerson.save((err) => {});
            } else {
                res.json(persons[0]);
            }
        }
    })
    Person.deleteOne({"username": username}, (err, results) => {});
    dbPerson.save((err) => {});
})

app.use('/set', (req, res) => {
    var person = JSON.parse(req.query.person);
    var dbPerson = new Person(person);
    var username = person.username;
    var query = {};
    query.username = username;
    Person.find(query, (err, persons) => {
        if (err) {
            console.log(err);
        } else {
            console.log("Finding old stuff for res...")
            if (persons.length == 0) {
                res.json({});
                dbPerson.save((err) => {});
            } else {
                res.json(persons[0]);
            }
        }
    })
    Person.deleteOne({"username": username}, (err, results) => {});
    dbPerson.save((err) => {});
})

app.use('/remove', (req, res) => {
    var username = req.query.username;
    var query = {};
    query.username = username;
    Person.find(query, (err, persons) => {
        if (err) {
            console.log(err);
        } else {
            console.log("Removing stuff...")
            if (persons.length == 0) {
                res.json({});
            } else {
                res.json(persons[0]);
            }
        }
    })
    Person.deleteOne({"username": username}, (err, results) => {});
})

app.use('/all', (req, res) => {
    Person.find({}, (err, persons) => {
        console.log("Getting all stuffs...")
        if (err) {
            console.log(err);
        } else {
            res.json(persons);
        }
    })
})

app.use('/available', (req, res) => {
    var username = req.query.username;
    Person.find({}, (err, persons) => {
        if (err) {
            console.log(err);
        } else {
            console.log("Checking if stuff is available...")
            var avail = true;
            persons.forEach((person) => {
                if (person.username == username) {
                    avail = false;
                }
            })
            res.json({"available": avail});
        }
    })
})

app.listen(port, () => {
    console.log('Listening on port ' + port);
});



    // var newPerson = new Person ({
    // 	firstName: req.body.fname,
    // 	lastName: req.body.lname,
    //     });

    // //save the person to the database
    // newPerson.save( (err) => { 
    // 	if (err) {
    // 	    res.type('html').status(200);
    // 	    res.write('uh oh: ' + err);
    // 	    console.log(err);
    // 	    res.end();
    // 	}
    // 	else {
    //         // display the "successfull created" page using EJS
    //         console.log("saved");
    //         console.log(newPerson);
    // 	    res.json({person : newPerson});
    //     }
    //     } ); 

    //res.redirect('/search');