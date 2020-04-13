import freemarker.cache.ClassTemplateLoader;

import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        ParsingPart.parser();
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration freeMarkerConfiguration = new Configuration();
        freeMarkerConfiguration.setTemplateLoader(new ClassTemplateLoader(Main.class, "/"));
        freeMarkerEngine.setConfiguration(freeMarkerConfiguration);
        get("/", (request, response) -> {
            String req = request.queryParams("search");
            if(req == null) {
                Map<String, Object> model = new HashMap<>();
                return freeMarkerEngine.render(new ModelAndView(model, "index.ftl"));
            } else {
                response.redirect("/response?search="+req);
                return null;
            }
        });
        get("/response", (request, response) -> {
            Set<String> result = Ranging.selectRequest(request.queryParams("search"));
            if (result == null){
                response.redirect("/notfound");
                return null;
            }
            List<String> aList = new ArrayList<>();
            aList.addAll(result);
            Map<String, Object> model = new HashMap<>();
            model.put("urls", aList);
            return freeMarkerEngine.render(new ModelAndView(model, "response.ftl"));
        });
        get("/notfound", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return freeMarkerEngine.render(new ModelAndView(model, "notFound.ftl"));
        });
    }
}
