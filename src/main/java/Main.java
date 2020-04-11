import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        ParsingPart.parser();
        staticFiles.location("/public");
        init();
        get("/", (request, response) -> {
            response.redirect("index.html");
            System.out.println(request.queryParams("search"));
            return null;
        });
    }
}
