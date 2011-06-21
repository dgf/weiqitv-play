package json;

import play.exceptions.UnexpectedException;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.results.Result;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyRenderJson extends Result {

	String json;

	public MyRenderJson(Object o) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		json = gson.toJson(o);
	}

	public MyRenderJson(Object o, ExclusionStrategy exclusionStrategy) {
		Gson gson = new GsonBuilder().setExclusionStrategies(exclusionStrategy).serializeNulls()
				.create();
		json = gson.toJson(o);
	}

	public void apply(Request request, Response response) {
		try {
			setContentTypeIfNotSet(response, "application/json; charset=utf-8");
			response.out.write(json.getBytes("utf-8"));
		} catch (Exception e) {
			throw new UnexpectedException(e);
		}
	}

}
