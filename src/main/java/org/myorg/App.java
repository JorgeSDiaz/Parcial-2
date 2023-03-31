package org.myorg;

import java.nio.file.Files;
import java.nio.file.Path;

import static spark.Spark.*;

import static org.myorg.service.CollatzService.*;

public class App
{
    public static void main( String[] args )
    {
        port(getPort());
        get("/", ((request, response) -> {
            response.type("text/html");

            return "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Collatz Sequence</title>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1>Collatz Sequence</h1>\n" +
                    "<form action=\"/request\">\n" +
                    "    <label for=\"value\">Number:</label><br>\n" +
                    "    <input type=\"text\" id=\"value\" name=\"value\"><br><br>\n" +
                    "    <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                    "</form>\n" +
                    "<div id=\"getrespmsg\"></div>\n" +
                    "<script>\n" +
                    "            function loadGetMsg(){\n" +
                    "                let value = document.getElementById(\"value\");\n" +
                    "                let url = \"/request?value=\" + value.value;\n" +
                    "\n" +
                    "                fetch (url, {method: 'GET'})\n" +
                    "                    .then(x => x.text())\n" +
                    "                    .then(y => document.getElementById(\"getrespmsg\").innerHTML = y);\n" +
                    "            }\n" +
                    "</script>\n" +
                    "</body>\n" +
                    "</html>";
        }));

        get("/request", ((request, response) -> {
            response.type("application/json");

            String param = request.queryParams("value");
            System.out.println(param);
            String res = sequence(Integer.parseInt(param));
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
}
