package com.hit.gamecalendar.test;


import com.google.gson.Gson;
import com.hit.gamecalendar.main.java.api.Startup;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.api.socket.SocketExchange;
import com.hit.gamecalendar.main.java.api.socket.requests.ParamRequestMap;
import com.hit.gamecalendar.main.java.api.socket.requests.SocketRequest;
import com.hit.gamecalendar.main.java.api.socket.responses.CreateItemDBResponse;
import com.hit.gamecalendar.main.java.api.socket.responses.SocketResponse;
import com.hit.gamecalendar.main.java.dao.GameModel;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameControllerTest {
    public static InetAddress clientAddress;
    public static final int port = 9110;
    public static final Gson gson = new Gson();
    public static SocketResponse createdGameResponse = null;
    public static SocketResponse deletedGameResponse = null;

    @BeforeClass
    public static void StartServer() throws IOException, InterruptedException {
        Startup.main(null);

        // wait one second for server to start (because it starts on another thread)
        Thread.sleep(1000);

        // set this pc current ip address for sending requests
        clientAddress = InetAddress.getLocalHost();
    }

    @Test
    public void getAllGamesTest() {

        // use the client to send the request
        try {
            SocketResponse games = getAllGames();

            // should get any response
            Assert.assertNotEquals(null, games);

            // if not null should get more than one game (assuming db is not empty)
            if (games != null)
                Assert.assertNotEquals(null, games.data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getGameTest() {

        // use the client to send the request
        try {

            SocketResponse response = getGame();

            Assert.assertTrue("Response should return successful true", response.isSuccessful());
            if (response.isSuccessful()) {
                var data = response.getData(GameModel.class);
                Logger.logDebug("response = " + data);
            }
            // should get any response
            Assert.assertNotEquals(null, response);

            // game number 1 should exist (assuming db is not empty)
            if (response != null)
                Assert.assertNotEquals(null, response.data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createGameTest() {

        // use the client to send the request
        try {
            createdGameResponse = createGame(gson);

            // should get any response
            Assert.assertNotEquals(null, createdGameResponse);

            // game number 1 should exist (assuming db is not empty)
            if (createdGameResponse != null)
                Assert.assertNotEquals(null, createdGameResponse.data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteGameTest() {

        // use the client to send the request
        try {
            deletedGameResponse = deleteGameCreated(gson, createdGameResponse);

            // should get any response
            Assert.assertNotEquals(null, deletedGameResponse);

            // game number 1 should exist (assuming db is not empty)
            if (deletedGameResponse != null)
                Assert.assertEquals(true, gson.fromJson(deletedGameResponse.data, Boolean.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SocketResponse getAllGames() throws IOException {
        var request = new SocketRequest("GET", "/api/game/", null, "Testing");
        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);
        return (SocketResponse)exchange.get(SocketResponse.class);
    }

    private SocketResponse getGame() throws IOException {
        var query = new ParamRequestMap();
        query.put("id", 2);
        var request = new SocketRequest("GET", "/api/game/", query ,"Testing");

        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        var response = (SocketResponse)exchange.get(SocketResponse.class);
        return response;
    }

    private SocketResponse createGame(Gson gson) throws IOException {
        var newGame = new GameModel();
        newGame.cool_name = "TestingCreation" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        var newGameJson = gson.toJson(newGame);
        var request = new SocketRequest("CREATE", "/api/game/", null, newGameJson);

        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }

    private SocketResponse deleteGameCreated(Gson gson, SocketResponse gameResponse) throws IOException {
        SocketRequest request;
        SocketExchange exchange;
        var creationResponse = gson.fromJson(gameResponse.data, CreateItemDBResponse.class);

        var query = new ParamRequestMap();
        query.put("id", creationResponse.id.intValue());

        request = new SocketRequest("DELETE", "/api/game/", query, null);
        exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }
}
