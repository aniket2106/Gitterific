package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    final Logger logger = LoggerFactory.getLogger("play");

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/
     */
    public Result index() {
        logger.info("working");
        return ok(views.html.index.render());
    }

}
