package models;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.data.validation.URL;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends Model implements Cloneable {

    @Required
    @Column(unique = true, nullable = false)
    public String name;

    @Required
    @Column(unique = true, nullable = false)
    public String fullname;

    @Email
    @Required
    @Column(unique = true, nullable = false)
    public String mail;

    @Password
    @Required
    public String password;

    @URL
    @Column(unique = true)
    public String website;

    public boolean isAdmin;

    public boolean isPro;

    public User(String name, String mail, String password, String fullname) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.fullname = fullname;
    }

    public static User connect(String name, String password) {
        return find("byNameAndPassword", name, password).first();
    }

    @Override
    public String toString() {
        return fullname;
    }

    public static User findByName(String name) {
        return User.find("name", name).first();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        User user = (User) super.clone();
        user.id = null;
        return user;
    }
}
