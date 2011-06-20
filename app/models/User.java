package models;

import javax.persistence.Entity;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.data.validation.URL;
import play.db.jpa.Model;

@Entity
public class User extends Model {

	@Email
	@Required
	public String mail;

	@Password
	@Required
	public String password;

	@Required
	public String fullname;

	@URL
	public String website;

	public boolean isAdmin;

	public boolean isPro;

	public User(String mail, String password, String fullname) {
		this.mail = mail;
		this.password = password;
		this.fullname = fullname;
	}

	public static User connect(String mail, String password) {
		return find("byMailAndPassword", mail, password).first();
	}

	@Override
	public String toString() {
		return fullname;
	}

}
