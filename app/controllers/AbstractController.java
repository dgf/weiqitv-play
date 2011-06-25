package controllers;

import json.MyExclusionStrategy;
import json.MyRenderJson;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public abstract class AbstractController extends Controller {

	@Before
	static void setConnectedUser() {
		if (Security.isConnected() == false) {
			renderArgs.put("user", "anonymous");
			renderArgs.put("role", "watcher");
		} else {
			User user = User.findByName(Security.connected());
			renderArgs.put("user", user.fullname);
			if (user.isAdmin) {
				renderArgs.put("role", "admin");
			} else if (user.isPro) {
				renderArgs.put("role", "pro");
			} else {
				renderArgs.put("role", "user");
			}
		}
	}

	protected static void renderJSONExpose(Object o) {
		throw new MyRenderJson(o);
	}

	protected static void renderJSONHide(Object o) {
		throw new MyRenderJson(o, new MyExclusionStrategy());
	}
}
