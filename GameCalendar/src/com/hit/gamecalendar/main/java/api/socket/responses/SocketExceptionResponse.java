package com.hit.gamecalendar.main.java.api.socket.responses;

import com.hit.gamecalendar.main.java.api.socket.enums.ESocketResponseStatus;

public class SocketExceptionResponse<T> extends SocketResponse {
    public SocketExceptionResponse(String message, T data) {
        super(message, ESocketResponseStatus.EXCEPTION, data.toString());
    }

    public SocketExceptionResponse(String message) {
        super(ESocketResponseStatus.EXCEPTION, message);
    }
}
