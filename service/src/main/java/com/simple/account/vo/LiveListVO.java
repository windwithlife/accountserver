package com.simple.account.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 会议实体VO
 * @author hejinguo
 * @version $Id: LiveListVO.java, v 0.1 2020年8月6日 下午11:51:15
 */
public class LiveListVO implements Serializable {
    /**  */
    private static final long serialVersionUID = -2841683563964535196L;

    private Integer           id;
    /**
     * 会议标题
     */
    private String            roomTitle;
    /**
     * 会议封面图片
     */
    private String            roomPicPath;
    /**
     * 会议二维码地址
     */
    private String            roomQrCodePath;
    /**
     * 日程图片地址
     */
    private String            roomSchedulePath;
    /**
     * 介绍图片地址
     */
    private String            roomDescPath;

    /**
     * 直播状态(0:未开始 1:直播中 2:已结束)
     */
    private Integer           roomStatus;
    /**
     * 直播开始时间
     */
    private Date              liveStartDate;

    /**
     * 播放量
     */
    private Integer           playNumber;

    /**
     * 是否发布(0:未发布 1:已发布 2:已下架)
     */
    private Integer           publishStatus;

    /**
     * 推流服务器地址
     */
    private String            pushServerUrl;

    /**
     * 推流秘钥串
     */
    private String            pushSecretKey;

    /**
     * RTMP拉流地址
     */
    private String            pullRtmpUrl;

    /**
     * FVL拉流地址
     */
    private String            pullFlvUrl;

    /**
     * HLSL拉流地址
     */
    private String            pullHlsUrl;

    /**
     * UDP拉流地址
     */
    private String            pullUdpUrl;

    /**
     * MP4录播地址
     */
    private String            videoMp4Url;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getRoomPicPath() {
        return roomPicPath;
    }

    public void setRoomPicPath(String roomPicPath) {
        this.roomPicPath = roomPicPath;
    }

    public String getRoomQrCodePath() {
        return roomQrCodePath;
    }

    public void setRoomQrCodePath(String roomQrCodePath) {
        this.roomQrCodePath = roomQrCodePath;
    }

    public String getRoomSchedulePath() {
        return roomSchedulePath;
    }

    public void setRoomSchedulePath(String roomSchedulePath) {
        this.roomSchedulePath = roomSchedulePath;
    }

    public String getRoomDescPath() {
        return roomDescPath;
    }

    public void setRoomDescPath(String roomDescPath) {
        this.roomDescPath = roomDescPath;
    }

    public Integer getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(Integer roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Date getLiveStartDate() {
        return liveStartDate;
    }

    public void setLiveStartDate(Date liveStartDate) {
        this.liveStartDate = liveStartDate;
    }

    public Integer getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(Integer playNumber) {
        this.playNumber = playNumber;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getPushServerUrl() {
        return pushServerUrl;
    }

    public void setPushServerUrl(String pushServerUrl) {
        this.pushServerUrl = pushServerUrl;
    }

    public String getPushSecretKey() {
        return pushSecretKey;
    }

    public void setPushSecretKey(String pushSecretKey) {
        this.pushSecretKey = pushSecretKey;
    }

    public String getPullRtmpUrl() {
        return pullRtmpUrl;
    }

    public void setPullRtmpUrl(String pullRtmpUrl) {
        this.pullRtmpUrl = pullRtmpUrl;
    }

    public String getPullFlvUrl() {
        return pullFlvUrl;
    }

    public void setPullFlvUrl(String pullFlvUrl) {
        this.pullFlvUrl = pullFlvUrl;
    }

    public String getPullHlsUrl() {
        return pullHlsUrl;
    }

    public void setPullHlsUrl(String pullHlsUrl) {
        this.pullHlsUrl = pullHlsUrl;
    }

    public String getPullUdpUrl() {
        return pullUdpUrl;
    }

    public void setPullUdpUrl(String pullUdpUrl) {
        this.pullUdpUrl = pullUdpUrl;
    }

    public String getVideoMp4Url() {
        return videoMp4Url;
    }

    public void setVideoMp4Url(String videoMp4Url) {
        this.videoMp4Url = videoMp4Url;
    }
}
