package com.simple.account.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 直播录制通知VO
 * @author hejinguo
 * @version $Id: RecordLiveVO.java, v 0.1 2020年8月8日 下午2:07:38
 */
public class RecordLiveVO implements Serializable {
    /**  */
    private static final long serialVersionUID = -306099341275008205L;

    private Integer           event_type;

    private Integer           appid;

    private String            stream_id;

    private String            channel_id;

    private String            file_id;

    private String            file_format;

    private BigInteger        start_time;

    private BigInteger        end_time;

    private BigInteger        duration;

    private BigInteger        file_size;

    private String            stream_param;

    private String            video_url;

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getStream_id() {
        return stream_id;
    }

    public void setStream_id(String stream_id) {
        this.stream_id = stream_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getFile_format() {
        return file_format;
    }

    public void setFile_format(String file_format) {
        this.file_format = file_format;
    }

    public BigInteger getStart_time() {
        return start_time;
    }

    public void setStart_time(BigInteger start_time) {
        this.start_time = start_time;
    }

    public BigInteger getEnd_time() {
        return end_time;
    }

    public void setEnd_time(BigInteger end_time) {
        this.end_time = end_time;
    }

    public BigInteger getDuration() {
        return duration;
    }

    public void setDuration(BigInteger duration) {
        this.duration = duration;
    }

    public BigInteger getFile_size() {
        return file_size;
    }

    public void setFile_size(BigInteger file_size) {
        this.file_size = file_size;
    }

    public String getStream_param() {
        return stream_param;
    }

    public void setStream_param(String stream_param) {
        this.stream_param = stream_param;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public Integer getEvent_type() {
        return event_type;
    }

    public void setEvent_type(Integer event_type) {
        this.event_type = event_type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RecordLiveVO [event_type=");
        builder.append(event_type);
        builder.append(", appid=");
        builder.append(appid);
        builder.append(", stream_id=");
        builder.append(stream_id);
        builder.append(", channel_id=");
        builder.append(channel_id);
        builder.append(", file_id=");
        builder.append(file_id);
        builder.append(", file_format=");
        builder.append(file_format);
        builder.append(", start_time=");
        builder.append(start_time);
        builder.append(", end_time=");
        builder.append(end_time);
        builder.append(", duration=");
        builder.append(duration);
        builder.append(", file_size=");
        builder.append(file_size);
        builder.append(", stream_param=");
        builder.append(stream_param);
        builder.append(", video_url=");
        builder.append(video_url);
        builder.append("]");
        return builder.toString();
    }

}
