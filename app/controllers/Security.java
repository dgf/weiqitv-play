package controllers;

import models.User;
import play.Logger;

public class Security extends Secure.Security {

	static boolean authenticate(String name, String password) {
		if (User.count() == 0) { // initial login -> creates an admin
			Logger.info("create new Administrator %s", name);
			User admin = new User(name, "admin@g2d.de", password, "Administrator");
			admin.isAdmin = true;
			admin.isPro = true;
			admin.save();
		}
		return User.connect(name, password) != null;
	}

	static boolean check(String profile) {
		if ("admin".equals(profile)) {
			return User.find("name", connected()).<User> first().isAdmin;
		}
		return false;
	}

}
