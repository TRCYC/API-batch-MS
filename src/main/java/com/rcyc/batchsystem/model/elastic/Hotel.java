package com.rcyc.batchsystem.model.elastic;

public class Hotel {
    private String operatorId;
    private int duration;
    private String endDate;
    private String salePeriod;
    private String typeCode;
    private String createdDate;
    private String typeName;
    private String facilityName;
    private String facilityCode;
    private String modifyDate;
    private String hotelName;
    private String begDate;
    private String portCode;
    private String endTimeZone;
    private String facilityId;
    private String begTimeZone;
    private String enabled;
    private String providerName;
    private String hotelCode;
    private String providerCode;
    private String infoPrice;
    private String providerId;
    private int eventId;
    private String sort;

    public String getOperatorId() { return operatorId; }
    public void setOperatorId(String operatorId) { this.operatorId = operatorId; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public String getSalePeriod() { return salePeriod; }
    public void setSalePeriod(String salePeriod) { this.salePeriod = salePeriod; }

    public String getTypeCode() { return typeCode; }
    public void setTypeCode(String typeCode) { this.typeCode = typeCode; }

    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }

    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }

    public String getFacilityName() { return facilityName; }
    public void setFacilityName(String facilityName) { this.facilityName = facilityName; }

    public String getFacilityCode() { return facilityCode; }
    public void setFacilityCode(String facilityCode) { this.facilityCode = facilityCode; }

    public String getModifyDate() { return modifyDate; }
    public void setModifyDate(String modifyDate) { this.modifyDate = modifyDate; }

    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }

    public String getBegDate() { return begDate; }
    public void setBegDate(String begDate) { this.begDate = begDate; }

    public String getPortCode() { return portCode; }
    public void setPortCode(String portCode) { this.portCode = portCode; }

    public String getEndTimeZone() { return endTimeZone; }
    public void setEndTimeZone(String endTimeZone) { this.endTimeZone = endTimeZone; }

    public String getFacilityId() { return facilityId; }
    public void setFacilityId(String facilityId) { this.facilityId = facilityId; }

    public String getBegTimeZone() { return begTimeZone; }
    public void setBegTimeZone(String begTimeZone) { this.begTimeZone = begTimeZone; }

    public String getEnabled() { return enabled; }
    public void setEnabled(String enabled) { this.enabled = enabled; }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getHotelCode() { return hotelCode; }
    public void setHotelCode(String hotelCode) { this.hotelCode = hotelCode; }

    public String getProviderCode() { return providerCode; }
    public void setProviderCode(String providerCode) { this.providerCode = providerCode; }

    public String getInfoPrice() { return infoPrice; }
    public void setInfoPrice(String infoPrice) { this.infoPrice = infoPrice; }

    public String getProviderId() { return providerId; }
    public void setProviderId(String providerId) { this.providerId = providerId; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public String getSort() { return sort; }
    public void setSort(String sort) { this.sort = sort; }
    @Override
    public String toString() {
        return "Hotel [operatorId=" + operatorId + ", duration=" + duration + ", endDate=" + endDate + ", salePeriod="
                + salePeriod + ", typeCode=" + typeCode + ", createdDate=" + createdDate + ", typeName=" + typeName
                + ", facilityName=" + facilityName + ", facilityCode=" + facilityCode + ", modifyDate=" + modifyDate
                + ", hotelName=" + hotelName + ", begDate=" + begDate + ", portCode=" + portCode + ", endTimeZone="
                + endTimeZone + ", facilityId=" + facilityId + ", begTimeZone=" + begTimeZone + ", enabled=" + enabled
                + ", providerName=" + providerName + ", hotelCode=" + hotelCode + ", providerCode=" + providerCode
                + ", infoPrice=" + infoPrice + ", providerId=" + providerId + ", eventId=" + eventId + ", sort=" + sort
                + "]";
    }
    
}
