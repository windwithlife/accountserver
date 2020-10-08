package com.simple.account.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 直播信息返回数据实体
 * @author hejinguo
 * @version $Id: LiveRoomVO.java, v 0.1 2020年7月25日 下午12:10:39
 */
public class LiveDetailVO implements Serializable {
    /**  */
    private static final long serialVersionUID = 5790295049153044564L;

    private Integer           id;
    /**
     * 直播标题
     */
    private String            roomTitle;
    /**
     * 直播图片
     */
    private String            roomPicPath;
    /**
     * 直播内容描述
     */
    private String            roomDesc;
    /**
     * 直播状态(0:未开始 1:直播中 2:已结束)
     */
    private Integer           roomStatus;
    /**
     * 直播开始时间
     */
    private Date              liveStartDate;
    /**
     * 直播结束时间
     */
    private Date              liveEndDate;
    /**
     * 播放量
     */
    private Integer           playNumber;
    /**
     * 讲师名称
     */
    private String            userTrueName;
    /**医院名称*/
    private String            hospitalName;
    /**
     * 职位名称
     */
    private String            positionName;

    /**用户头像*/
    private String            headPic;

    /**用户简介*/
    private String            userDesc;

    /**
     * 是否可回放(0:不可回放 1:可回放)
     */
    private Integer           playAble;

    /**
     * 是否发布(0:未发布 1:已发布 2:已下架)
     */
    private Integer           publishStatus;

    /**
     * 房间ID(确保唯一性)
     */
    private String            roomCode;

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

    public String getRoomDesc() {
        return roomDesc;
    }

    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
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

    public Date getLiveEndDate() {
        return liveEndDate;
    }

    public void setLiveEndDate(Date liveEndDate) {
        this.liveEndDate = liveEndDate;
    }

    public Integer getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(Integer playNumber) {
        this.playNumber = playNumber;
    }

    public String getUserTrueName() {
        return userTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        this.userTrueName = userTrueName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public Integer getPlayAble() {
        return playAble;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getPushServerUrl() {
        return pushServerUrl;
    }

    public String getPushSecretKey() {
        return pushSecretKey;
    }

    public String getPullRtmpUrl() {
        return pullRtmpUrl;
    }

    public String getPullFlvUrl() {
        return pullFlvUrl;
    }

    public String getPullHlsUrl() {
        return pullHlsUrl;
    }

    public String getPullUdpUrl() {
        return pullUdpUrl;
    }

    public String getVideoMp4Url() {
        return videoMp4Url;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public void setPlayAble(Integer playAble) {
        this.playAble = playAble;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public void setPushServerUrl(String pushServerUrl) {
        this.pushServerUrl = pushServerUrl;
    }

    public void setPushSecretKey(String pushSecretKey) {
        this.pushSecretKey = pushSecretKey;
    }

    public void setPullRtmpUrl(String pullRtmpUrl) {
        this.pullRtmpUrl = pullRtmpUrl;
    }

    public void setPullFlvUrl(String pullFlvUrl) {
        this.pullFlvUrl = pullFlvUrl;
    }

    public void setPullHlsUrl(String pullHlsUrl) {
        this.pullHlsUrl = pullHlsUrl;
    }

    public void setPullUdpUrl(String pullUdpUrl) {
        this.pullUdpUrl = pullUdpUrl;
    }

    public void setVideoMp4Url(String videoMp4Url) {
        this.videoMp4Url = videoMp4Url;
    }

}
