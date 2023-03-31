package org.myorg;

import static org.myorg.service.CollatzService.sequence;
import static spark.Spark.get;
import static spark.Spark.port;

public class CollatzApp {
    public static void main(String[] args) {
        port(getPort());
        get("/cz", ((request, response) -> {
            response.type("text/plain");
            int number = Integer.parseInt(request.queryParams("value"));
            System.out.println("Number: " + number);
            String res = sequence(number);
            System.out.println("Sequence: " + res);
            return res;
        }));
    }

    private static Integer getPort() {
        return System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 4567;
    }
}
