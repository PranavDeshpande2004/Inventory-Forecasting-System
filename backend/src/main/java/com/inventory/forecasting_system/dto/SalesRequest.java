package com.inventory.forecasting_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SalesRequest {
    @JsonProperty("product_name")
    private String product_name;

    @JsonProperty("sale_date")
    private String sale_date;

    public String getProduct_name() {
        return product_name;
    }

    public SalesRequest(String product_name, String sale_date) {
        this.product_name = product_name;
        this.sale_date = sale_date;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getSale_date() {
        return sale_date;
    }

    public void setSale_date(String sale_date) {
        this.sale_date = sale_date;
    }
}
