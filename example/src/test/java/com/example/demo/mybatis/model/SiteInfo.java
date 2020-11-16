package com.example.demo.mybatis.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteInfo {
    private String id;

    /**
    * url
    */
    private String url;

    /**
    * 网站标题
    */
    private String title;

    /**
    * icp信息
    */
    private String icpInfo;

    /**
    * whois信息
    */
    private String whoisInfo;

    /**
    * 一级域名
    */
    private String domain1;

    /**
    * 域名
    */
    private String domain;

    /**
    * 行政归属地址
    */
    private String adminLocation;

    /**
    * 端口
    */
    private Integer port;

    /**
    * 指纹信息
    */
    private String finger;

    /**
    * 协议
    */
    private Boolean isHttps;

    /**
    * 添加人(登录名称)
    */
    private String createUser;

    /**
    * 添加时间
    */
    private Date createTime;

    /**
    * ip
    */
    private String ip;

    /**
    * 网站归属单位
    */
    private String unit;

    /**
    * 站点类型，1：云防护 2：url 3:ip
    */
    private Integer type;

    /**
    * 归属行业
    */
    private String industry;

    /**
    * 自定义名称
    */
    private String cname;

    /**
    * 是否解析 1-是 0-否
    */
    private Boolean isParse;

    /**
    * 1 已审核  其他 未审核
    */
    private Integer checked;

    /**
    * 审核状态  -未通过—0，通过—1, 已转正式—2
    */
    private Integer checkedStatus;

    /**
    * 审核人
    */
    private String checkedUser;

    /**
    * 审核时候
    */
    private Date checkedTime;

    /**
    * 审核失败原因
    */
    private String failReason;

    /**
    * 是否失效 1-是  0-否
    */
    private Boolean isDeleted;

    /**
    * 最后更新时间
    */
    private Date lastModifiedTime;

    /**
    * 是否自动补全过信息
    */
    private Integer autoParseTag;

    /**
    * 真实地址
    */
    private String realUrl;

    /**
    * 备注信息
    */
    private String mark;

    /**
    * 端口分离 访问端口
    */
    private Integer fPort;

    /**
    * 是否开启端口分离 0-否 1-是
    */
    private Boolean isFport;

    /**
    * 是否回源   0-非回源  1-回源
    */
    private Boolean isSrc;

    private String entityId;

    /**
    * 最后修改人
    */
    private String lastModifiedUser;

    /**
    * 资产类型:1-云主机 2-私有云
    */
    private Integer assetType;

    /**
    * 资产名称
    */
    private String assetName;

    /**
    * 部署位置
    */
    private String deployLocation;

    /**
    * 部署类型1-域名 2-ip(站点资产特有)
    */
    private Integer deployType;

    /**
    * 资产来源
    */
    private Integer assetSource;

    /**
    * 内网 ip(主机资产特有)
    */
    private String privateNetIp;

    /**
    * 网络
    */
    private String network;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcpInfo() {
        return icpInfo;
    }

    public void setIcpInfo(String icpInfo) {
        this.icpInfo = icpInfo;
    }

    public String getWhoisInfo() {
        return whoisInfo;
    }

    public void setWhoisInfo(String whoisInfo) {
        this.whoisInfo = whoisInfo;
    }

    public String getDomain1() {
        return domain1;
    }

    public void setDomain1(String domain1) {
        this.domain1 = domain1;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAdminLocation() {
        return adminLocation;
    }

    public void setAdminLocation(String adminLocation) {
        this.adminLocation = adminLocation;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getFinger() {
        return finger;
    }

    public void setFinger(String finger) {
        this.finger = finger;
    }

    public Boolean getIsHttps() {
        return isHttps;
    }

    public void setIsHttps(Boolean isHttps) {
        this.isHttps = isHttps;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Boolean getIsParse() {
        return isParse;
    }

    public void setIsParse(Boolean isParse) {
        this.isParse = isParse;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(Integer checkedStatus) {
        this.checkedStatus = checkedStatus;
    }

    public String getCheckedUser() {
        return checkedUser;
    }

    public void setCheckedUser(String checkedUser) {
        this.checkedUser = checkedUser;
    }

    public Date getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Integer getAutoParseTag() {
        return autoParseTag;
    }

    public void setAutoParseTag(Integer autoParseTag) {
        this.autoParseTag = autoParseTag;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getfPort() {
        return fPort;
    }

    public void setfPort(Integer fPort) {
        this.fPort = fPort;
    }

    public Boolean getIsFport() {
        return isFport;
    }

    public void setIsFport(Boolean isFport) {
        this.isFport = isFport;
    }

    public Boolean getIsSrc() {
        return isSrc;
    }

    public void setIsSrc(Boolean isSrc) {
        this.isSrc = isSrc;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setLastModifiedUser(String lastModifiedUser) {
        this.lastModifiedUser = lastModifiedUser;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getDeployLocation() {
        return deployLocation;
    }

    public void setDeployLocation(String deployLocation) {
        this.deployLocation = deployLocation;
    }

    public Integer getDeployType() {
        return deployType;
    }

    public void setDeployType(Integer deployType) {
        this.deployType = deployType;
    }

    public Integer getAssetSource() {
        return assetSource;
    }

    public void setAssetSource(Integer assetSource) {
        this.assetSource = assetSource;
    }

    public String getPrivateNetIp() {
        return privateNetIp;
    }

    public void setPrivateNetIp(String privateNetIp) {
        this.privateNetIp = privateNetIp;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }
}