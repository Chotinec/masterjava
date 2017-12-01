package ru.javaops.masterjava.xml.model;

public class UserTo {

    private String fullName;
    private String email;
    private UserFlag flag;

    public UserTo(String fullName, String email, UserFlag flag) {
        this.fullName = fullName;
        this.email = email;
        this.flag = flag;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserFlag getFlag() {
        return flag;
    }

    public void setFlag(UserFlag flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTo userTo = (UserTo) o;

        if (fullName != null ? !fullName.equals(userTo.fullName) : userTo.fullName != null) return false;
        if (email != null ? !email.equals(userTo.email) : userTo.email != null) return false;
        return flag == userTo.flag;

    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (flag != null ? flag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", flag=" + flag +
                '}';
    }
}
