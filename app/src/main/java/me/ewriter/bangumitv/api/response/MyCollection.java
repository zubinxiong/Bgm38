package me.ewriter.bangumitv.api.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zubin on 2016/8/4.
 */
public class MyCollection {
    /**
     * name : モブサイコ100
     * ep_status : 4
     * lasttouch : 1470204729
     * subject : {"id":158316,"url":"http://bgm.tv/subject/158316","type":2,"name":"モブサイコ100","name_cn":"灵能百分百","summary":"","eps":12,"air_date":"2016-07-11","air_weekday":1,"rating":{"total":0,"count":{"1":0,"2":0,"3":0,"4":0,"5":0,"6":0,"7":0,"8":0,"9":0,"10":0},"score":0},"rank":0,"images":{"large":"http://lain.bgm.tv/pic/cover/l/2b/85/158316_gz1mM.jpg","common":"http://lain.bgm.tv/pic/cover/c/2b/85/158316_gz1mM.jpg","medium":"http://lain.bgm.tv/pic/cover/m/2b/85/158316_gz1mM.jpg","small":"http://lain.bgm.tv/pic/cover/s/2b/85/158316_gz1mM.jpg","grid":"http://lain.bgm.tv/pic/cover/g/2b/85/158316_gz1mM.jpg"},"collection":{"wish":0,"collect":0,"doing":1069,"on_hold":0,"dropped":0}}
     */

    private String name;
    private int ep_status;
    private int lasttouch;
    /**
     * id : 158316
     * url : http://bgm.tv/subject/158316
     * type : 2
     * name : モブサイコ100
     * name_cn : 灵能百分百
     * summary :
     * eps : 12
     * air_date : 2016-07-11
     * air_weekday : 1
     * rating : {"total":0,"count":{"1":0,"2":0,"3":0,"4":0,"5":0,"6":0,"7":0,"8":0,"9":0,"10":0},"score":0}
     * rank : 0
     * images : {"large":"http://lain.bgm.tv/pic/cover/l/2b/85/158316_gz1mM.jpg","common":"http://lain.bgm.tv/pic/cover/c/2b/85/158316_gz1mM.jpg","medium":"http://lain.bgm.tv/pic/cover/m/2b/85/158316_gz1mM.jpg","small":"http://lain.bgm.tv/pic/cover/s/2b/85/158316_gz1mM.jpg","grid":"http://lain.bgm.tv/pic/cover/g/2b/85/158316_gz1mM.jpg"}
     * collection : {"wish":0,"collect":0,"doing":1069,"on_hold":0,"dropped":0}
     */

    private SubjectBean subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public SubjectBean getSubject() {
        return subject;
    }

    public void setSubject(SubjectBean subject) {
        this.subject = subject;
    }

    public static class SubjectBean {
        private int id;
        private String url;
        private int type;
        private String name;
        private String name_cn;
        private String summary;
        private int eps;
        private String air_date;
        private int air_weekday;
        /**
         * total : 0
         * count : {"1":0,"2":0,"3":0,"4":0,"5":0,"6":0,"7":0,"8":0,"9":0,"10":0}
         * score : 0
         */

        private RatingBean rating;
        private int rank;
        /**
         * large : http://lain.bgm.tv/pic/cover/l/2b/85/158316_gz1mM.jpg
         * common : http://lain.bgm.tv/pic/cover/c/2b/85/158316_gz1mM.jpg
         * medium : http://lain.bgm.tv/pic/cover/m/2b/85/158316_gz1mM.jpg
         * small : http://lain.bgm.tv/pic/cover/s/2b/85/158316_gz1mM.jpg
         * grid : http://lain.bgm.tv/pic/cover/g/2b/85/158316_gz1mM.jpg
         */

        private ImagesBean images;
        /**
         * wish : 0
         * collect : 0
         * doing : 1069
         * on_hold : 0
         * dropped : 0
         */

        private CollectionBean collection;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName_cn() {
            return name_cn;
        }

        public void setName_cn(String name_cn) {
            this.name_cn = name_cn;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getEps() {
            return eps;
        }

        public void setEps(int eps) {
            this.eps = eps;
        }

        public String getAir_date() {
            return air_date;
        }

        public void setAir_date(String air_date) {
            this.air_date = air_date;
        }

        public int getAir_weekday() {
            return air_weekday;
        }

        public void setAir_weekday(int air_weekday) {
            this.air_weekday = air_weekday;
        }

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public CollectionBean getCollection() {
            return collection;
        }

        public void setCollection(CollectionBean collection) {
            this.collection = collection;
        }

        public static class RatingBean {
            private int total;
            /**
             * 1 : 0
             * 2 : 0
             * 3 : 0
             * 4 : 0
             * 5 : 0
             * 6 : 0
             * 7 : 0
             * 8 : 0
             * 9 : 0
             * 10 : 0
             */

            private CountBean count;
            private float score;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public CountBean getCount() {
                return count;
            }

            public void setCount(CountBean count) {
                this.count = count;
            }

            public float getScore() {
                return score;
            }

            public void setScore(float score) {
                this.score = score;
            }

            public static class CountBean {
                @SerializedName("1")
                private int value1;
                @SerializedName("2")
                private int value2;
                @SerializedName("3")
                private int value3;
                @SerializedName("4")
                private int value4;
                @SerializedName("5")
                private int value5;
                @SerializedName("6")
                private int value6;
                @SerializedName("7")
                private int value7;
                @SerializedName("8")
                private int value8;
                @SerializedName("9")
                private int value9;
                @SerializedName("10")
                private int value10;

                public int getValue1() {
                    return value1;
                }

                public void setValue1(int value1) {
                    this.value1 = value1;
                }

                public int getValue2() {
                    return value2;
                }

                public void setValue2(int value2) {
                    this.value2 = value2;
                }

                public int getValue3() {
                    return value3;
                }

                public void setValue3(int value3) {
                    this.value3 = value3;
                }

                public int getValue4() {
                    return value4;
                }

                public void setValue4(int value4) {
                    this.value4 = value4;
                }

                public int getValue5() {
                    return value5;
                }

                public void setValue5(int value5) {
                    this.value5 = value5;
                }

                public int getValue6() {
                    return value6;
                }

                public void setValue6(int value6) {
                    this.value6 = value6;
                }

                public int getValue7() {
                    return value7;
                }

                public void setValue7(int value7) {
                    this.value7 = value7;
                }

                public int getValue8() {
                    return value8;
                }

                public void setValue8(int value8) {
                    this.value8 = value8;
                }

                public int getValue9() {
                    return value9;
                }

                public void setValue9(int value9) {
                    this.value9 = value9;
                }

                public int getValue10() {
                    return value10;
                }

                public void setValue10(int value10) {
                    this.value10 = value10;
                }
            }
        }

        public static class ImagesBean {
            private String large;
            private String common;
            private String medium;
            private String small;
            private String grid;

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getCommon() {
                return common;
            }

            public void setCommon(String common) {
                this.common = common;
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

            public String getGrid() {
                return grid;
            }

            public void setGrid(String grid) {
                this.grid = grid;
            }
        }

        public static class CollectionBean {
            private int wish;
            private int collect;
            private int doing;
            private int on_hold;
            private int dropped;

            public int getWish() {
                return wish;
            }

            public void setWish(int wish) {
                this.wish = wish;
            }

            public int getCollect() {
                return collect;
            }

            public void setCollect(int collect) {
                this.collect = collect;
            }

            public int getDoing() {
                return doing;
            }

            public void setDoing(int doing) {
                this.doing = doing;
            }

            public int getOn_hold() {
                return on_hold;
            }

            public void setOn_hold(int on_hold) {
                this.on_hold = on_hold;
            }

            public int getDropped() {
                return dropped;
            }

            public void setDropped(int dropped) {
                this.dropped = dropped;
            }
        }
    }
}
