package me.ewriter.bangumitv.event;

import me.ewriter.bangumitv.api.response.Token;

/**
 * Created by Zubin on 2016/8/4.
 */
public class UserLoginEvent {
    private Token token;

    public UserLoginEvent(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
