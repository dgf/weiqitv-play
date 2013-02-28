package controllers;

import models.User;
import play.Logger;

public class Security extends Secure.Security {

    static boolean authenticate(String name, String password) {
        if (User.count() == 0) { // initial login -> creates an admin
            Logger.info("create new Administrator %s", name);
            User admin = new User(name, "admin@tv.g2d.de", password, "Administrator");
            admin.isAdmin = true;
            admin.isPro = true;
            admin.save();
            Logger.debug("create default admin user %s!", admin.name);
        }
        return User.connect(name, password) != null;
    }

    static boolean check(String profile) {
        if ("isAdmin".equals(profile)) {
            return User.findByName(connected()).isAdmin;
        }
        return false;
    }

}
