package me.ewriter.bangumitv.api.response;

import java.util.List;

/**
 * Created by Zubin on 2016/8/6.
 */
public class SubjectComment {
    /**
     * id : 3
     * type : do
     * name : 在看
     */

    private StatusBean status;
    /**
     * status : {"id":3,"type":"do","name":"在看"}
     * rating : 9
     * comment : one老师的作品故事实在太棒了
     * tag : [""]
     * ep_status : 4
     * lasttouch : 1470204729
     * user : {"id":271485,"url":"http://bgm.tv/user/271485","username":"271485","nickname":"Zubin","avatar":{"large":"http://lain.bgm.tv/pic/user/l/000/27/14/271485.jpg?r=1453971142","medium":"http://lain.bgm.tv/pic/user/m/000/27/14/271485.jpg?r=1453971142","small":"http://lain.bgm.tv/pic/user/s/000/27/14/271485.jpg?r=1453971142"},"sign":""}
     */

    private int rating;
    private String comment;
    private int ep_status;
    private int lasttouch;
    /**
     * id : 271485
     * url : http://bgm.tv/user/271485
     * username : 271485
     * nickname : Zubin
     * avatar : {"large":"http://lain.bgm.tv/pic/user/l/000/27/14/271485.jpg?r=1453971142","medium":"http://lain.bgm.tv/pic/user/m/000/27/14/271485.jpg?r=1453971142","small":"http://lain.bgm.tv/pic/user/s/000/27/14/271485.jpg?r=1453971142"}
     * sign :
     */

    private UserBean user;
    private List<String> tag;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getEp_status() {
        return ep_status;
    }

    public void setEp_status(int ep_status) {
        this.ep_status = ep_status;
    }

    public int getLasttouch() {
        return lasttouch;
    }

    public void setLasttouch(int lasttouch) {
        this.lasttouch = lasttouch;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public static class StatusBean {
        private int id;
        private String type;
        private String name;
        // 1, wish, 想看
        // 2 collect 看过
        // 3 do 在看
        // 4 on_hold 搁置
        // 5 drop 弃番

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class UserBean {
        private int id;
        private String url;
        private String username;
        private String nickname;
        /**
         * large : http://lain.bgm.tv/pic/user/l/000/27/14/271485.jpg?r=1453971142
         * medium : http://lain.bgm.tv/pic/user/m/000/27/14/271485.jpg?r=1453971142
         * small : http://lain.bgm.tv/pic/user/s/000/27/14/271485.jpg?r=1453971142
         */

        private AvatarBean avatar;
        private String sign;

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

        public static class AvatarBean {
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
}
