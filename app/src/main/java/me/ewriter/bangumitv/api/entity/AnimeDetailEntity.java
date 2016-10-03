package me.ewriter.bangumitv.api.entity;

import java.util.List;

/**
 * Created by Zubin on 2016/9/26.
 *
 * 动画详情实体
 */

public class AnimeDetailEntity {

    String nameCN;
    String nameJp;
    /** 顶部的小字， 一般是类别，如动画，书籍等*/
    String smallType;

    String largeImageUrl;
    String coverImageUrl;

    /**评分*/
    String globalScore;

    String summary;

    List<String> infoList;
    List<TagEntity> tagList;
    List<AnimeCharacterEntity> characterList;
    List<CommentEntity> commentList;


    public String getNameCN() {
        return nameCN;
    }

    public void setNameCN(String nameCN) {
        this.nameCN = nameCN;
    }

    public String getNameJp() {
        return nameJp;
    }

    public void setNameJp(String nameJp) {
        this.nameJp = nameJp;
    }

    public String getSmallType() {
        return smallType;
    }

    public void setSmallType(String smallType) {
        this.smallType = smallType;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getGlobalScore() {
        return globalScore;
    }

    public void setGlobalScore(String globalScore) {
        this.globalScore = globalScore;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<String> infoList) {
        this.infoList = infoList;
    }

    public List<TagEntity> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagEntity> tagList) {
        this.tagList = tagList;
    }

    public List<AnimeCharacterEntity> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<AnimeCharacterEntity> characterList) {
        this.characterList = characterList;
    }

    public List<CommentEntity> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentEntity> commentList) {
        this.commentList = commentList;
    }
}
