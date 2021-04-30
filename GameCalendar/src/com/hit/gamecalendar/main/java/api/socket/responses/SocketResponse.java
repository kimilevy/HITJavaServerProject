package com.hit.gamecalendar.main.java.api.socket.responses;

import com.google.gson.Gson;
import com.hit.gamecalendar.main.java.api.Startup;
import com.hit.gamecalendar.main.java.api.socket.enums.ESocketResponseStatus;
import com.hit.gamecalendar.main.java.api.socket.interfaces.ISocketResponse;

public class SocketResponse implements ISocketResponse<String> {
    public ESocketResponseStatus statusCode;
    public String data;

    public SocketResponse(String message, ESocketResponseStatus statusCode, String data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public SocketResponse(ESocketResponseStatus statusCode, String data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    @Override
    public ESocketResponseStatus getStatusCode() {
        return statusCode;
    }

    @Override
    public String getDataJson() {
        return data;
    }

    public <T> T getData(Class<T> c) {
        return Startup.gson.fromJson(this.getDataJson(), c);
    }

    public Boolean isSuccessful() {
        return this.statusCode == ESocketResponseStatus.OK;
    }

    @Override
    public String toString() {
        return "SocketResponse{" +
                "statusCode=" + statusCode +
                ", data='" + data + '\'' +
                '}';
    }
}
