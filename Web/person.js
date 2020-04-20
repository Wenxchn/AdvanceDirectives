var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var personSchema = new Schema({
    firstName: String,
    lastName: String,
    birthday: String,
    address: String,
    email: String,
    passcode: String,
    username: String,
    password: String,
    phone: String,
    moodCalendar: String,
    form: String,
    image: String
});

var mongoURI = "mongodb+srv://zqiu:12345@cluster0-bmaew.mongodb.net/test?retryWrites=true&w=majority";

mongoose.connect(mongoURI, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
    dbName: 'CIS350'
});

mongoose.connection.on('connected', () => {
    console.log("MongoDB connected");
});

var People = mongoose.model('Person', personSchema, 'persons');
module.exports = People;