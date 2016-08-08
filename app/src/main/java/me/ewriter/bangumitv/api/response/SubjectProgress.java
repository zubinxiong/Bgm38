package me.ewriter.bangumitv.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Zubin on 2016/8/8.
 * 获取每一集的状态
 */
public class SubjectProgress {
    /**
     * subject_id : 158316
     * eps : [{"id":638065,"status":{"id":2,"css_name":"Watched","url_name":"watched","cn_name":"看过"}},{"id":638066,"status":{"id":2,"css_name":"Watched","url_name":"watched","cn_name":"看过"}},{"id":638067,"status":{"id":2,"css_name":"Watched","url_name":"watched","cn_name":"看过"}},{"id":638068,"status":{"id":2,"css_name":"Watched","url_name":"watched","cn_name":"看过"}},{"id":642705,"status":{"id":1,"css_name":"Queue","url_name":"queue","cn_name":"想看"}}]
     */

    @SerializedName("subject_id")
    private int subjectId;
    /**
     * id : 638065
     * status : {"id":2,"css_name":"Watched","url_name":"watched","cn_name":"看过"}
     */

    @SerializedName("eps")
    private List<EpsBean> eps;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public List<EpsBean> getEps() {
        return eps;
    }

    public void setEps(List<EpsBean> eps) {
        this.eps = eps;
    }

    public static class EpsBean {
        @SerializedName("id")
        private int id;
        /**
         * id : 2
         * css_name : Watched
         * url_name : watched
         * cn_name : 看过
         */

        @SerializedName("status")
        private StatusBean status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public StatusBean getStatus() {
            return status;
        }

        public void setStatus(StatusBean status) {
            this.status = status;
        }

        public static class StatusBean {
            @SerializedName("id")
            private int id;
            @SerializedName("css_name")
            private String cssName;
            @SerializedName("url_name")
            private String urlName;
            @SerializedName("cn_name")
            private String cnName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCssName() {
                return cssName;
            }

            public void setCssName(String cssName) {
                this.cssName = cssName;
            }

            public String getUrlName() {
                return urlName;
            }

            public void setUrlName(String urlName) {
                this.urlName = urlName;
            }

            public String getCnName() {
                return cnName;
            }

            public void setCnName(String cnName) {
                this.cnName = cnName;
            }
        }
    }
}
