package org.sample.rx.deals.client.response;

import com.google.common.base.MoreObjects;

import java.util.UUID;

public class ProductResponse {

    private UUID id;
    private String type;
    private String title;
    private double discount;
    private boolean enabled;

    public ProductResponse() {}

    public ProductResponse(UUID id, String type, String title, double discount, boolean enabled) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.discount = discount;
        this.enabled = enabled;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("type", type).add("title", title)
            .add("discount", discount).add("enabled", enabled).toString();
    }
}
