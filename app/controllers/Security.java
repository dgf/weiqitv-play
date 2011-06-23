package controllers;

import models.User;

public class Security extends Secure.Security {

	static boolean authenticate(String name, String password) {
		return User.connect(name, password) != null;
	}

	static boolean check(String profile) {
		if ("admin".equals(profile)) {
			return User.find("name", connected()).<User> first().isAdmin;
		}
		return false;
	}

}
