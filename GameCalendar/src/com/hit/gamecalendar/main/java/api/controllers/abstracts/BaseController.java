package com.hit.gamecalendar.main.java.api.controllers.abstracts;

import com.hit.gamecalendar.main.java.api.Startup;
import com.hit.gamecalendar.main.java.api.controllers.interfaces.IController;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;

public abstract class BaseController implements IController {

    public BaseController() {
    }

    protected static String getBodyAsText(HttpExchange exchange) throws IOException, InterruptedException {
        Thread.sleep(10);
        StringBuilder requestBody = new StringBuilder();
        InputStream ios = exchange.getRequestBody();
        int i;
        while ((i = ios.read()) != -1) {
            requestBody.append((char) i);
        }
        return requestBody.toString();
    }

    protected static <T> T getBodyAsEntity(HttpExchange exchange, Class<T> t) {
        try {
            var json = getBodyAsText(exchange);
            return Startup.gson.fromJson(json, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
