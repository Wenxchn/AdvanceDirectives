package edu.upenn.cis350.advanceddirectives.data;

public class Person {
    private Form form;
    private String passcode;

    // basic info
    private String username;
    private String password;
    // Stuff you can search by
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String birthday;
    private MoodCalendar moodCalendar;
    private String image;

    public Person(String username, String password, String firstName, String lastName,
                  String email, String phone, String address, String birthday) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.image = "";
        this.form = new Form();
        this.moodCalendar = new MoodCalendar();
    }

    public Person(String username, String password, String firstName, String lastName,
                  String email, String phone, String address, String birthday, String image) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.image = image;
        this.form = new Form();
        this.moodCalendar = new MoodCalendar();
    }

    public MoodCalendar getMoodCalendar() {
        return moodCalendar;
    }

    public void setForm(String answers) {
        return;
    }

    public void setMoodCalendar(String s) {
        moodCalendar = new MoodCalendar(s);
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String encoding) {
        this.image = encoding;
    }

    public Form getForm() {
        return this.form;
    }

    public String getPasscode() {
        return this.passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
