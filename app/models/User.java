package models;

import javax.persistence.Entity;

import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Required;
import play.data.validation.URL;
import play.db.jpa.Model;

//@Indexed
@Entity
public class User extends Model {

	// @Field
	@Required
	public String name;

	// @Field
	@Required
	public String fullname;

	@Email
	@Required
	public String mail;

	@Password
	@Required
	public String password;

	@URL
	public String website;

	public boolean isAdmin;

	public boolean isPro;

	public User(String mail, String password, String fullname) {
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

}
