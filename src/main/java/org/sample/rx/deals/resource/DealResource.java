package org.sample.rx.deals.resource;

import com.google.common.base.MoreObjects;

import org.sample.rx.deals.model.Deal;
import org.sample.rx.deals.model.Product;
import org.sample.rx.deals.service.DealService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DealResource {

    public enum DealProductsSortBy {
        title_asc,
        title_desc,
        discount_asc,
        discount_desc
    }

    private DealService dealService;

    public DealResource() {}

    public DealResource(DealService dealService) {
        this.dealService = dealService;
    }

    // GET /deals/{dealId}
    public Deal getDealById(UUID dealId) {
        return dealService.getDealById(dealId).blockingGet();
    }

    // GET /merchant/{merchantId}/deals?onlyEnabled={true|false}&sortBy={title_asc|title_desc\discount_asc|discount_desc}
    public MerchantDeals getMerchantDeals(UUID merchantId, DealProductsSortBy sortBy, boolean onlyEnabled) {
        // return the deals for a merchant
        // - deals sorted by start date desc
        // -- products sorted by parameter
        // -- enabled products filtered by parameter
        // TODO : prepare comparators
        Optional<Comparator<Deal>> dealComparator = Optional.empty(); // TODO
        Optional<Comparator<Product>> productComparator = Optional.empty(); // TODO
        return dealService
                .getDealsByMerchantId(
                        merchantId,
                        dealComparator,
                        productComparator,
                        onlyEnabled
                )
                .toList()
                .map(deals -> new MerchantDeals(merchantId, deals))
                .blockingGet();
    }

    // TODO: refactor to model package
    public static class MerchantDeals {
        private UUID       merchantId;
        private List<Deal> deals;

        public MerchantDeals(UUID merchantId, List<Deal> deals) {
            this.merchantId = merchantId;
            this.deals = deals;
        }

        public UUID getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(UUID merchantId) {
            this.merchantId = merchantId;
        }

        public List<Deal> getDeals() {
            return deals;
        }

        public void setDeals(List<Deal> deals) {
            this.deals = deals;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                              .add("merchantId", merchantId)
                              .add("deals", deals)
                              .toString();
        }
    }
}
