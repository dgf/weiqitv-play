package controllers.crud;

import controllers.CRUD;
import controllers.Secure;
import play.mvc.With;

@With(Secure.class)
public class Games extends CRUD {

}
