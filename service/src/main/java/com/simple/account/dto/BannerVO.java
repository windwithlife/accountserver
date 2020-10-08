package com.simple.account.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Banner返回数据
 * @author hejinguo
 * @version $Id: BannerVO.java, v 0.1 2020年7月25日 下午12:07:54
 */
public class BannerVO implements Serializable {
    /**  */
    private static final long serialVersionUID = -3472377270266559027L;

    private Integer id;
    /**
     * 广告标题
     */
    private String  advTitle;
    /**
     * 广告图片url
     */
    private String  advPicPath;
    /**
     * 来源(adv_source_type为0时是live_room表ID,为1时是链接地址)
     */
    private String            advSource;
    /**
     * 开始时间
     */
    private Date              startDate;

    /**
     * 结束时间
     */
    private Date              endDate;

    /**
     * 状态(0:禁用 1:正常 -1:删除)
     */
    private Integer           advStatus;

    /**
     * 排序
     */
    private Integer           advOrder;

    /**内容信息*/
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getAdvStatus() {
        return advStatus;
    }

    public void setAdvStatus(Integer advStatus) {
        this.advStatus = advStatus;
    }

    public Integer getAdvOrder() {
        return advOrder;
    }

    public void setAdvOrder(Integer advOrder) {
        this.advOrder = advOrder;
    }

    public String getAdvDesc() {
        return advDesc;
    }

    public void setAdvDesc(String advDesc) {
        this.advDesc = advDesc;
    }

}
