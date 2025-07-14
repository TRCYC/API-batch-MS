package com.rcyc.batchsystem.model.elastic;

public class Pricing {
    private String pricingId;
    private String price;
    private String currency;
    private String description;
    private String suiteAvailability;
    private String rateCode;
    private String rateName;
    private String createdDate;
    private String categoryCode;
    private String cruiseCode;
    private String ageCategory;
    private String categoryName;
    private Long pricePerson1;
    

    public String getPricingId() { return pricingId; }
    public void setPricingId(String pricingId) { this.pricingId = pricingId; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSuiteAvailability() { return suiteAvailability; }
    public void setSuiteAvailability(String suiteAvailability) { this.suiteAvailability = suiteAvailability; }
    public String getRateCode() { return rateCode; }
    public void setRateCode(String rateCode) { this.rateCode = rateCode; }
    public String getRateName() { return rateName; }
    public void setRateName(String rateName) { this.rateName = rateName; }
    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
    public String getCategoryCode() { return categoryCode; }
    public void setCategoryCode(String categoryCode) { this.categoryCode = categoryCode; }
    public String getCruiseCode() { return cruiseCode; }
    public void setCruiseCode(String cruiseCode) { this.cruiseCode = cruiseCode; }
    public String getAgeCategory() { return ageCategory; }
    public void setAgeCategory(String ageCategory) { this.ageCategory = ageCategory; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public Long getPricePerson1() { return pricePerson1; }
    public void setPricePerson1(Long pricePerson1) { this.pricePerson1 = pricePerson1; }

    @Override
    public String toString() {
        return "Pricing [pricingId=" + pricingId + ", price=" + price + ", currency=" + currency + ", description=" + description + "]";
    }
} 