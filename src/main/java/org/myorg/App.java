package org.myorg;

import java.nio.file.Files;
import java.nio.file.Path;

import static spark.Spark.*;

import static org.myorg.service.HttpConnection.*;

public class App 
{
    public static void main( String[] args )
    {
        port(getPort());
        get("/", ((request, response) -> {
            response.type("text/html");

            return Files.readAllBytes(Path.of("src/main/resources/static/index.html"));
        }));

        get("/request", ((request, response) -> {
            response.type("application/json");

            String param = request.queryParams("value");
            System.out.println(param);
            System.out.println(getUrlService() + ":" + String.valueOf(getPort()) + "/cz?value=" + param);
            String res = connect(getUrlService() + "/cz?value=" + param);
            System.out.println(res);
            return resFormat("collatzsequence", param, res);
        }));
    }

    private static String resFormat(String operation, String input, String output) {
        String op = "\"operation\": \"" + operation + "\"";
        String in = "\"input\": \"" + input + "\"";
        String out = "\"output\": \"" + output + "\"";
        return "{" +
                op + ", " +
                in + ", " +
                out +
                "}";
    }

    private static Integer getPort() {
        return System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 7654;
    }

    private static String getUrlService() {
        return System.getenv("URLSERVICE") != null ? System.getenv("URLSERVICE") : "http://localhost:4567";
    }
}
