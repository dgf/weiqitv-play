package controllers;

import json.MyExclusionStrategy;
import json.MyRenderJson;
import play.mvc.Controller;

public abstract class AbstractController extends Controller {

	protected static void renderJSONExpose(Object o) {
		throw new MyRenderJson(o);
	}

	protected static void renderJSONHide(Object o) {
		throw new MyRenderJson(o, new MyExclusionStrategy());
	}
}
