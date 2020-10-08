package com.simple.account.dto;

import java.io.Serializable;

/**
 * 资讯返回数据
 * @author hejinguo
 * @version $Id: BannerVO.java, v 0.1 2020年7月25日 下午12:07:54
 */
public class AdvertVO implements Serializable {
    /**  */
    private static final long serialVersionUID = -3472377270266559027L;

    private Integer           id;
    /**
     * 广告标题
     */
    private String            advTitle;
    /**
     * 广告图片url
     */
    private String            advPicPath;

    private String            advSource;
    /**
     * 点击量
     */
    private Integer           clickNumber;

    private String            advDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdvTitle() {
        return advTitle;
    }

    public void setAdvTitle(String advTitle) {
        this.advTitle = advTitle;
    }

    public String getAdvPicPath() {
        return advPicPath;
    }

    public void setAdvPicPath(String advPicPath) {
        this.advPicPath = advPicPath;
    }

    public String getAdvSource() {
        return advSource;
    }

    public void setAdvSource(String advSource) {
        this.advSource = advSource;
    }

    public Integer getClickNumber() {
        return clickNumber;
    }

    public void setClickNumber(Integer clickNumber) {
        this.clickNumber = clickNumber;
    }

    public String getAdvDesc() {
        return advDesc;
    }

    public void setAdvDesc(String advDesc) {
        this.advDesc = advDesc;
    }

}
