var express = require('express');
var bodyParser = require('body-parser');
var app = express();
var Person = require('./person.js')

app.use(bodyParser.urlencoded({extended: true})); 

app.get('/', function(req, res) {
    res.sendFile(__dirname + '/index.html');
});

// app.use('/search', function(req, res) {
//     var first = req.body.fname;
//     var last = req.body.lname;
//     p = new Person(first, last);
//     res.json(p);
// })

app.post('/search', (req, res) => {
    console.log('here');
    console.log(req.body);
    var query = {};

    if (req.body.fname) {
        //query = {"fname": req.body.fname};
        query.firstName = req.body.fname;
    }

    if (req.body.lname) {
        query.lastName = req.body.lname;
    }
    console.log(query);
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
            console.log("find" + persons);
            // res.json(persons);
            res.render('search.ejs', {persons: persons});
        }
    })
});



app.listen(5500, () => {
    console.log('Listening on port');
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