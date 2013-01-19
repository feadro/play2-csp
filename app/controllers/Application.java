package controllers;

import csp.ContentSecurityPolicy;
import org.codehaus.jackson.JsonNode;
import play.Logger;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

@ContentSecurityPolicy
public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Hi from index."));
    }

    @ContentSecurityPolicy("default-src 'self' ; img-src http://www.playframework.org ; style-src 'unsafe-inline' 'self'")
    public static Result anotherPolicy() {
        return ok(index.render("Hi from anotherPolicy."));
    }

    @ContentSecurityPolicy("default-src 'self' ; report-uri /report")
    public static Result secureWithReporting() {
        return ok(index.render("Hi from secureWithReporting."));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result cspReportParser() {
        JsonNode json = request().body().asJson();
        if(json == null) {
            Logger.debug("no JSON payload");
            return badRequest("Expecting Json data");
        } else {
            Logger.debug(json.toString());
            return ok();
        }
    }
}
