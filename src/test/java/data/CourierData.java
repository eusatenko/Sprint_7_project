package data;

public class CourierData {
    private String login = "Usatenko" + (10000 + (int)(Math.random() *99999));
    private String password = "Qwerty" + (100000 + (int)(Math.random() *999999));;
    private String firstName = "Test" + (100000 + (int)(Math.random() *999999));

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
