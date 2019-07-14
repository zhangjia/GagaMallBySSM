package io.zhangjia.mall.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Commodity   {
    private Integer commodityId;
    private String commodityName;
    private String commodityAttribute;
    private Date commodityCreateTime;
    private Date commodityUpdateTIme;
    private Integer commodityView;
    private Integer level1MenuId;
    private Integer level2MenuId;
    private Integer commodityStatus;




//    ----------------------------------------

    private List<Map<String, Object>> commodityImgs;
    private List<Map<String, Object>> commodityDetailImgs;
    private List<String> commoditySpecs;
    private String level1MenuChineseName;
    private String level2MenuChineseName;
    private Integer commoditySales;
    private Integer commodityInventory;
    private Double commodityMinOriginalPrice;
    private Double commodityMaxOriginalPrice;
    private Double commodityMinPresentPrice;
    private Double commodityMaxPresentPrice;
    private Double commodityMaxMarkDown;//降价

    public Commodity() {
    }

    public List<String> getCommoditySpecs() {
        return commoditySpecs;
    }

    public void setCommoditySpecs(List<String> commoditySpecs) {
        this.commoditySpecs = commoditySpecs;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityAttribute() {
        return commodityAttribute;
    }

    public void setCommodityAttributes(String commodityAttribute) {
        this.commodityAttribute = commodityAttribute;
    }

    public Date getCommodityCreateTime() {
        return commodityCreateTime;
    }

    public void setCommodityCreateTime(Date commodityCreateTime) {
        this.commodityCreateTime = commodityCreateTime;
    }

    public Date getCommodityUpdateTIme() {
        return commodityUpdateTIme;
    }

    public void setCommodityUpdateTIme(Date commodityUpdateTIme) {
        this.commodityUpdateTIme = commodityUpdateTIme;
    }

    public Integer getCommodityView() {
        return commodityView;
    }

    public void setCommodityView(Integer commodityView) {
        this.commodityView = commodityView;
    }

    public Integer getLevel1MenuId() {
        return level1MenuId;
    }

    public void setLevel1MenuId(Integer level1MenuId) {
        this.level1MenuId = level1MenuId;
    }

    public Integer getLevel2MenuId() {
        return level2MenuId;
    }

    public void setLevel2MenuId(Integer level2MenuId) {
        this.level2MenuId = level2MenuId;
    }

    public Integer getCommodityStatus() {
        return commodityStatus;
    }

    public void setCommodityStatus(Integer commodityStatus) {
        this.commodityStatus = commodityStatus;
    }

    public void setCommodityAttribute(String commodityAttribute) {
        this.commodityAttribute = commodityAttribute;
    }

    public List<Map<String, Object>> getCommodityImgs() {
        return commodityImgs;
    }

    public void setCommodityImgs(List<Map<String, Object>> commodityImgs) {
        this.commodityImgs = commodityImgs;
    }

    public List<Map<String, Object>> getCommodityDetailImgs() {
        return commodityDetailImgs;
    }

    public void setCommodityDetailImgs(List<Map<String, Object>> commodityDetailImgs) {
        this.commodityDetailImgs = commodityDetailImgs;
    }

    public String getLevel1MenuChineseName() {
        return level1MenuChineseName;
    }

    public void setLevel1MenuChineseName(String level1MenuChineseName) {
        this.level1MenuChineseName = level1MenuChineseName;
    }

    public String getLevel2MenuChineseName() {
        return level2MenuChineseName;
    }

    public void setLevel2MenuChineseName(String level2MenuChineseName) {
        this.level2MenuChineseName = level2MenuChineseName;
    }

    public Integer getCommoditySales() {
        return commoditySales;
    }

    public void setCommoditySales(Integer commoditySales) {
        this.commoditySales = commoditySales;
    }

    public Integer getCommodityInventory() {
        return commodityInventory;
    }

    public void setCommodityInventory(Integer commodityInventory) {
        this.commodityInventory = commodityInventory;
    }

    public Double getCommodityMinOriginalPrice() {
        return commodityMinOriginalPrice;
    }

    public void setCommodityMinOriginalPrice(Double commodityMinOriginalPrice) {
        this.commodityMinOriginalPrice = commodityMinOriginalPrice;
    }

    public Double getCommodityMaxOriginalPrice() {
        return commodityMaxOriginalPrice;
    }

    public void setCommodityMaxOriginalPrice(Double commodityMaxOriginalPrice) {
        this.commodityMaxOriginalPrice = commodityMaxOriginalPrice;
    }

    public Double getCommodityMinPresentPrice() {
        return commodityMinPresentPrice;
    }

    public void setCommodityMinPresentPrice(Double commodityMinPresentPrice) {
        this.commodityMinPresentPrice = commodityMinPresentPrice;
    }

    public Double getCommodityMaxPresentPrice() {
        return commodityMaxPresentPrice;
    }

    public void setCommodityMaxPresentPrice(Double commodityMaxPresentPrice) {
        this.commodityMaxPresentPrice = commodityMaxPresentPrice;
    }

    public Double getCommodityMaxMarkDown() {
        return commodityMaxMarkDown;
    }

    public void setCommodityMaxMarkDown(Double commodityMaxMarkDown) {
        this.commodityMaxMarkDown = commodityMaxMarkDown;
    }


}
