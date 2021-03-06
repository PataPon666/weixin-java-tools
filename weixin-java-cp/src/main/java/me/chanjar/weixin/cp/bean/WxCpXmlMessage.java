package me.chanjar.weixin.cp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil;
import me.chanjar.weixin.cp.util.xml.XStreamTransformer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 微信推送过来的消息，也是同步回复给用户的消息，xml格式
 * 相关字段的解释看微信开发者文档：
 * http://mp.weixin.qq.com/wiki/index.php?title=接收普通消息
 * http://mp.weixin.qq.com/wiki/index.php?title=接收事件推送
 * http://mp.weixin.qq.com/wiki/index.php?title=接收语音识别结果
 * </pre>
 *
 * @author Daniel Qian
 */
@XStreamAlias("xml")
public class WxCpXmlMessage implements Serializable {

  ///////////////////////
  // 以下都是微信推送过来的消息的xml的element所对应的属性
  ///////////////////////

  @XStreamAlias("AgentID")
  private Integer agentId;

  @XStreamAlias("ToUserName")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String toUserName;

  @XStreamAlias("FromUserName")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String fromUserName;

  @XStreamAlias("CreateTime")
  private Long createTime;

  @XStreamAlias("MsgType")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String msgType;

  @XStreamAlias("Content")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String content;

  @XStreamAlias("MsgId")
  private Long msgId;

  @XStreamAlias("PicUrl")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String picUrl;

  @XStreamAlias("MediaId")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String mediaId;

  @XStreamAlias("Format")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String format;

  @XStreamAlias("ThumbMediaId")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String thumbMediaId;

  @XStreamAlias("Location_X")
  private Double locationX;

  @XStreamAlias("Location_Y")
  private Double locationY;

  @XStreamAlias("Scale")
  private Double scale;

  @XStreamAlias("Label")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String label;

  @XStreamAlias("Title")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String title;

  @XStreamAlias("Description")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String description;

  @XStreamAlias("Url")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String url;

  @XStreamAlias("Event")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String event;

  @XStreamAlias("EventKey")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String eventKey;

  @XStreamAlias("Ticket")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String ticket;

  @XStreamAlias("Latitude")
  private Double latitude;

  @XStreamAlias("Longitude")
  private Double longitude;

  @XStreamAlias("Precision")
  private Double precision;

  @XStreamAlias("Recognition")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String recognition;

  ///////////////////////////////////////
  // 群发消息返回的结果
  ///////////////////////////////////////
  /**
   * 群发的结果
   */
  @XStreamAlias("Status")
  @XStreamConverter(value=XStreamCDataConverter.class)
  private String status;
  /**
   * group_id下粉丝数；或者openid_list中的粉丝数
   */
  @XStreamAlias("TotalCount")
  private Integer totalCount;
  /**
   * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数，原则上，filterCount = sentCount + errorCount
   */
  @XStreamAlias("FilterCount")
  private Integer filterCount;
  /**
   * 发送成功的粉丝数
   */
  @XStreamAlias("SentCount")
  private Integer sentCount;
  /**
   * 发送失败的粉丝数
   */
  @XStreamAlias("ErrorCount")
  private Integer errorCount;

  @XStreamAlias("ScanCodeInfo")
  private ScanCodeInfo scanCodeInfo = new ScanCodeInfo();

  @XStreamAlias("SendPicsInfo")
  private SendPicsInfo sendPicsInfo = new SendPicsInfo();

  @XStreamAlias("SendLocationInfo")
  private SendLocationInfo sendLocationInfo = new SendLocationInfo();

  public Integer getAgentId() {
    return agentId;
  }

  public void setAgentId(Integer agentId) {
    this.agentId = agentId;
  }

  public String getToUserName() {
    return toUserName;
  }

  public void setToUserName(String toUserName) {
    this.toUserName = toUserName;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  /**
   * <pre>
   * 当接受用户消息时，可能会获得以下值：
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_TEXT}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_IMAGE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VOICE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VIDEO}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_LOCATION}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_LINK}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_EVENT}
   * </pre>
   *
   * @return
   */
  public String getMsgType() {
    return msgType;
  }

  /**
   * <pre>
   * 当发送消息的时候使用：
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_TEXT}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_IMAGE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VOICE}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_VIDEO}
   * {@link me.chanjar.weixin.common.api.WxConsts#XML_MSG_NEWS}
   * </pre>
   *
   * @param msgType
   */
  public void setMsgType(String msgType) {
    this.msgType = msgType;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getMsgId() {
    return msgId;
  }

  public void setMsgId(Long msgId) {
    this.msgId = msgId;
  }

  public String getPicUrl() {
    return picUrl;
  }

  public void setPicUrl(String picUrl) {
    this.picUrl = picUrl;
  }

  public String getMediaId() {
    return mediaId;
  }

  public void setMediaId(String mediaId) {
    this.mediaId = mediaId;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getThumbMediaId() {
    return thumbMediaId;
  }

  public void setThumbMediaId(String thumbMediaId) {
    this.thumbMediaId = thumbMediaId;
  }

  public Double getLocationX() {
    return locationX;
  }

  public void setLocationX(Double locationX) {
    this.locationX = locationX;
  }

  public Double getLocationY() {
    return locationY;
  }

  public void setLocationY(Double locationY) {
    this.locationY = locationY;
  }

  public Double getScale() {
    return scale;
  }

  public void setScale(Double scale) {
    this.scale = scale;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public String getEventKey() {
    return eventKey;
  }

  public void setEventKey(String eventKey) {
    this.eventKey = eventKey;
  }

  public String getTicket() {
    return ticket;
  }

  public void setTicket(String ticket) {
    this.ticket = ticket;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getPrecision() {
    return precision;
  }

  public void setPrecision(Double precision) {
    this.precision = precision;
  }

  public String getRecognition() {
    return recognition;
  }

  public void setRecognition(String recognition) {
    this.recognition = recognition;
  }

  public String getFromUserName() {
    return fromUserName;
  }

  public void setFromUserName(String fromUserName) {
    this.fromUserName = fromUserName;
  }

  protected static WxCpXmlMessage fromXml(String xml) {
    return XStreamTransformer.fromXml(WxCpXmlMessage.class, xml);
  }

  protected static WxCpXmlMessage fromXml(InputStream is) {
    return XStreamTransformer.fromXml(WxCpXmlMessage.class, is);
  }

  /**
   * 从加密字符串转换
   *
   * @param encryptedXml
   * @param wxCpConfigStorage
   * @param timestamp
   * @param nonce
   * @param msgSignature
   * @return
   */
  public static WxCpXmlMessage fromEncryptedXml(
      String encryptedXml,
      WxCpConfigStorage wxCpConfigStorage,
      String timestamp, String nonce, String msgSignature) {
    WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
    String plainText = cryptUtil.decrypt(msgSignature, timestamp, nonce, encryptedXml);
    return fromXml(plainText);
  }

  public static WxCpXmlMessage fromEncryptedXml(
      InputStream is,
      WxCpConfigStorage wxCpConfigStorage,
      String timestamp, String nonce, String msgSignature) {
    try {
      return fromEncryptedXml(IOUtils.toString(is, "UTF-8"), wxCpConfigStorage, timestamp, nonce, msgSignature);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public Integer getFilterCount() {
    return filterCount;
  }

  public void setFilterCount(Integer filterCount) {
    this.filterCount = filterCount;
  }

  public Integer getSentCount() {
    return sentCount;
  }

  public void setSentCount(Integer sentCount) {
    this.sentCount = sentCount;
  }

  public Integer getErrorCount() {
    return errorCount;
  }

  public void setErrorCount(Integer errorCount) {
    this.errorCount = errorCount;
  }

  public WxCpXmlMessage.ScanCodeInfo getScanCodeInfo() {
    return scanCodeInfo;
  }

  public void setScanCodeInfo(WxCpXmlMessage.ScanCodeInfo scanCodeInfo) {
    this.scanCodeInfo = scanCodeInfo;
  }

  public WxCpXmlMessage.SendPicsInfo getSendPicsInfo() {
    return sendPicsInfo;
  }

  public void setSendPicsInfo(WxCpXmlMessage.SendPicsInfo sendPicsInfo) {
    this.sendPicsInfo = sendPicsInfo;
  }

  public WxCpXmlMessage.SendLocationInfo getSendLocationInfo() {
    return sendLocationInfo;
  }

  public void setSendLocationInfo(WxCpXmlMessage.SendLocationInfo sendLocationInfo) {
    this.sendLocationInfo = sendLocationInfo;
  }

  @Override
  public String toString() {
    return "WxCpXmlMessage{" +
        "agentId=" + agentId +
        ", toUserName='" + toUserName + '\'' +
        ", fromUserName='" + fromUserName + '\'' +
        ", createTime=" + createTime +
        ", msgType='" + msgType + '\'' +
        ", content='" + content + '\'' +
        ", msgId=" + msgId +
        ", picUrl='" + picUrl + '\'' +
        ", mediaId='" + mediaId + '\'' +
        ", format='" + format + '\'' +
        ", thumbMediaId='" + thumbMediaId + '\'' +
        ", locationX=" + locationX +
        ", locationY=" + locationY +
        ", scale=" + scale +
        ", label='" + label + '\'' +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", url='" + url + '\'' +
        ", event='" + event + '\'' +
        ", eventKey='" + eventKey + '\'' +
        ", ticket='" + ticket + '\'' +
        ", latitude=" + latitude +
        ", longitude=" + longitude +
        ", precision=" + precision +
        ", recognition='" + recognition + '\'' +
        ", status='" + status + '\'' +
        ", totalCount=" + totalCount +
        ", filterCount=" + filterCount +
        ", sentCount=" + sentCount +
        ", errorCount=" + errorCount +
        ", scanCodeInfo=" + scanCodeInfo +
        ", sendPicsInfo=" + sendPicsInfo +
        ", sendLocationInfo=" + sendLocationInfo +
        '}';
  }

  @XStreamAlias("ScanCodeInfo")
  public static class ScanCodeInfo {

    @XStreamAlias("ScanType")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String scanType;

    @XStreamAlias("ScanResult")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String scanResult;

    /**
     * 扫描类型，一般是qrcode
     * @return
     */
    public String getScanType() {

      return scanType;
    }

    public void setScanType(String scanType) {
      this.scanType = scanType;
    }

    /**
     * 扫描结果，即二维码对应的字符串信息
     * @return
     */
    public String getScanResult() {
      return scanResult;
    }

    public void setScanResult(String scanResult) {
      this.scanResult = scanResult;
    }

  }

  @XStreamAlias("SendPicsInfo")
  public static class SendPicsInfo {

    @XStreamAlias("Count")
    private Long count;

    @XStreamAlias("PicList")
    protected final List<Item> picList = new ArrayList<Item>();

    public Long getCount() {
      return count;
    }

    public void setCount(Long count) {
      this.count = count;
    }

    public List<Item> getPicList() {
      return picList;
    }

    @XStreamAlias("item")
    public static class Item {

      @XStreamAlias("PicMd5Sum")
      @XStreamConverter(value=XStreamCDataConverter.class)
      private String PicMd5Sum;

      public String getPicMd5Sum() {
        return PicMd5Sum;
      }

      public void setPicMd5Sum(String picMd5Sum) {
        PicMd5Sum = picMd5Sum;
      }
    }
  }

  @XStreamAlias("SendLocationInfo")
  public static class SendLocationInfo {

    @XStreamAlias("Location_X")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String locationX;

    @XStreamAlias("Location_Y")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String locationY;

    @XStreamAlias("Scale")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String scale;

    @XStreamAlias("Label")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String label;

    @XStreamAlias("Poiname")
    @XStreamConverter(value=XStreamCDataConverter.class)
    private String poiname;

    public String getLocationX() {
      return locationX;
    }

    public void setLocationX(String locationX) {
      this.locationX = locationX;
    }

    public String getLocationY() {
      return locationY;
    }

    public void setLocationY(String locationY) {
      this.locationY = locationY;
    }

    public String getScale() {
      return scale;
    }

    public void setScale(String scale) {
      this.scale = scale;
    }

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public String getPoiname() {
      return poiname;
    }

    public void setPoiname(String poiname) {
      this.poiname = poiname;
    }
  }

}
