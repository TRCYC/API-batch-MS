package com.rcyc.batchsystem.model.elastic;

public class Pricing {
    private String pricingId;
    private String price;
    private String currency;
    private String description;
    // Add more fields as needed

    public String getPricingId() { return pricingId; }
    public void setPricingId(String pricingId) { this.pricingId = pricingId; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Pricing [pricingId=" + pricingId + ", price=" + price + ", currency=" + currency + ", description=" + description + "]";
    }
} 