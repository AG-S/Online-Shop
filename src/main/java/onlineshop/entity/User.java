package onlineshop.entity;

public class User {
    private int id;
    private String login;
    private String password;
    private UserType userType;
    private String sole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


    public String getSole() {
        return sole;
    }

    public void setSole(String sole) {
        this.sole = sole;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", pasword='" + password + '\'' +
                '}';
    }


}
