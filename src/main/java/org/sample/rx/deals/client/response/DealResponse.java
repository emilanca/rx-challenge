package org.sample.rx.deals.client.response;

import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.UUID;

public class DealResponse {

    private UUID id;
    private UUID merchantId;
    private String title;
    private String startAt;
    private String endAt;
    private List<UUID> products;

    public DealResponse(UUID id, UUID merchantId, String title, String startAt, String endAt,
        List<UUID> products) {
        this.id = id;
        this.merchantId = merchantId;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.products = products;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public List<UUID> getProducts() {
        return products;
    }

    public void setProducts(List<UUID> products) {
        this.products = products;
    }

    @Override public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("merchantId", merchantId)
            .add("title", title).add("startAt", startAt).add("endAt", endAt)
            .add("products", products).toString();
    }
}
