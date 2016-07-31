package me.ewriter.bangumitv.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

import me.ewriter.bangumitv.constants.MyConstants;
import me.ewriter.bangumitv.utils.Md5Util;
import me.ewriter.bangumitv.utils.SerializeUtils;

/**
 * Created by Zubin on 2016/7/26.
 */
public class Token{
    private int id;

    private String url;

    private String username;

    private String nickname;

    private AvatarBean avatar;

    private String sign;

    private String auth;

    private String authEncode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public AvatarBean getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarBean avatar) {
        this.avatar = avatar;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getAuthEncode() {
        return authEncode;
    }

    public void setAuthEncode(String authEncode) {
        this.authEncode = authEncode;
    }

    public static class AvatarBean{

        private String large;

        private String medium;

        private String small;

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }
    }
}
