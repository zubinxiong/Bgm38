package me.ewriter.bangumitv.api.entity;

/**
 * Created by Zubin on 2016/9/26.
 *
 * 单个角色动画页介绍实体
 */

public class AnimeCharacterEntity {
    String roleImageUrl;
    String roleLargeImageUrl;
    String roleNameJp;
    String roleUrl;
    String roleType;
    String roleNameCn;
    String cvName;
    String cvUrl;

    public String getRoleLargeImageUrl() {
        return roleLargeImageUrl;
    }

    public void setRoleLargeImageUrl(String roleLargeImageUrl) {
        this.roleLargeImageUrl = roleLargeImageUrl;
    }

    public String getRoleImageUrl() {
        return roleImageUrl;
    }

    public void setRoleImageUrl(String roleImageUrl) {
        this.roleImageUrl = roleImageUrl;
    }

    public String getRoleNameJp() {
        return roleNameJp;
    }

    public void setRoleNameJp(String roleNameJp) {
        this.roleNameJp = roleNameJp;
    }

    public String getRoleUrl() {
        return roleUrl;
    }

    public void setRoleUrl(String roleUrl) {
        this.roleUrl = roleUrl;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getRoleNameCn() {
        return roleNameCn;
    }

    public void setRoleNameCn(String roleNameCn) {
        this.roleNameCn = roleNameCn;
    }

    public String getCvName() {
        return cvName;
    }

    public void setCvName(String cvName) {
        this.cvName = cvName;
    }

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
    }
}
