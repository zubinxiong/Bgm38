package me.ewriter.bangumitv.api.entity;

/**
 * Created by Zubin on 2016/8/4.
 * 根据 BangumiDetail 返回的数据生成item
 * title 类型包括 type和 titleName
 * card 分 人物介绍 和 staff介绍，两者显示的一样，故用相同的名称（rolename, rolejob, imgurl)
 * grid 为每一集的信息(id, url, name, name_cn ,airdate, status, gridName)
 */
public class BangumiDetailEntity {

    /** 类型，包括 title，card和 grid*/
    private int type;
    /** 作品什么类型, 1 为漫画/小说， 2为 动画/二次元, 3 为音乐，4 为游戏，6为三次元番*/
    private int category_type;
    private String titleName;

    private String roleImageUrl;
    private String roleName;
    /** 传数据进来的时候要加上信息前缀比如 CV, JOB等*/
    private String roleJob;

    private int id;
    private String url;
    private String name;
    private String nameCn;
    private String airDate;
    private String status;
    private String girdName;
    private int epsType;

    /**标题和内容*/
    public BangumiDetailEntity(int type, String titleName) {
        this.type = type;
        this.titleName = titleName;
    }

    /** 卡片信息 stafff 和 角色*/
    public BangumiDetailEntity(int type, String roleName, String roleJob, String roleImageUrl) {
        this.type = type;
        this.roleName = roleName;
        this.roleImageUrl = roleImageUrl;
        this.roleJob = roleJob;
    }

    /** grid 观看信息*/
    public BangumiDetailEntity(int type, int id, String url, String nameCn, String name, String airDate, int epsType, String status, String girdName) {
        this.type = type;
        this.id = id;
        this.url = url;
        this.nameCn = nameCn;
        this.name = name;
        this.airDate = airDate;
        this.status = status;
        this.epsType = epsType;
        this.girdName = girdName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getRoleImageUrl() {
        return roleImageUrl;
    }

    public void setRoleImageUrl(String roleImageUrl) {
        this.roleImageUrl = roleImageUrl;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleJob() {
        return roleJob;
    }

    public void setRoleJob(String roleJob) {
        this.roleJob = roleJob;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameCn() {
        return nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGirdName() {
        return girdName;
    }

    public void setGirdName(String girdName) {
        this.girdName = girdName;
    }

    public int getEpsType() {
        return epsType;
    }

    public void setEpsType(int epsType) {
        this.epsType = epsType;
    }
}
