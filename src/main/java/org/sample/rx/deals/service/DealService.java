package org.sample.rx.deals.service;

import org.sample.rx.deals.client.DealClient;
import org.sample.rx.deals.client.ProductClient;
import org.sample.rx.deals.client.response.DealResponse;
import org.sample.rx.deals.client.response.ProductResponse;
import org.sample.rx.deals.model.Deal;
import org.sample.rx.deals.model.Product;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class DealService {

    private DealClient    dealClient;
    private ProductClient productClient;

    public DealService() {}

    public DealService(DealClient dealClient, ProductClient productClient) {
        this.dealClient = dealClient;
        this.productClient = productClient;
    }

    public Flowable<Deal> getDealsByMerchantId(UUID merchantId, Optional<Comparator<Deal>> dealComparator) {
        // done: use comparator to sort ; if comparator is not present, sort by start_date desc
        return getDealsByMerchantId(merchantId, dealComparator, Optional.<Comparator<Product>>empty(), true).sorted(dealComparator.orElse(Comparator.comparing(Deal::getStartAt)));
    }

    public Flowable<Deal> getDealsByMerchantId(UUID merchantId,
                                               Optional<Comparator<Deal>> dealComparator,
                                               Optional<Comparator<Product>> productComparator,
                                               boolean onlyEnabled) {
        // done: use dealComparator to sort deals (comments: defended against misuse by providing sensible default sort even if not explicitly requested)
        return dealClient.getDealsByMerchant(merchantId)
                         .flatMapMaybe(dealId -> getDealById(dealId, productComparator)
                                 .toMaybe()
                                         // TODO: switch error handling to :
                                         // return Maybe.empty() when it's a SocketException
                                         // return Maybe.error(error) when it's another type of throwable
                                 .onErrorResumeNext(Maybe.empty())).sorted(dealComparator.orElse(Comparator.comparing(Deal::getStartAt)));
    }

    public Single<Deal> getDealById(UUID dealId) {
        return getDealById(dealId, Optional.<Comparator<Product>>empty());
    }

    public Single<Deal> getDealById(UUID dealId, Optional<Comparator<Product>> productComparator) {
        // TODO : use productComparator to sort products of each deal
        return dealClient.getDealById(dealId)
                         .flatMap(dealResponse ->
                                          getProductsByIds(dealResponse.getProducts())
                                                  .toList()
                                                  .map(productsList -> {
                                                      Deal deal = mapDealResponseToDeal(
                                                              dealResponse);
                                                      deal.setProducts(
                                                              productsList);
                                                      return deal;
                                                  }));
    }

    protected Flowable<Product> getProductsByIds(List<UUID> ids) {
        // TODO: switch to ProductClient#getProductsByIds to make 1 single call
        return Flowable.fromIterable(ids)
                // TODO : add retries when callint the ProductClient here, up to 5
                .flatMapSingle(productClient::getProductById)
                .map(this::mapProductResponseToProduct);
    }

    private Deal mapDealResponseToDeal(DealResponse dealResponse) {
        // done : complete mapper
        Deal deal = new Deal();
        deal.setId(dealResponse.getId());
        deal.setMerchantId(dealResponse.getMerchantId());
        deal.setTitle(dealResponse.getTitle()); //2017-01-01T00:00:00Z
        deal.setStartAt(OffsetDateTime.parse(dealResponse.getStartAt()));
        deal.setEndAt(OffsetDateTime.parse(dealResponse.getEndAt()));
        return deal;
    }

    private Product mapProductResponseToProduct(ProductResponse productResponse) {
        // TODO : complete mapper
        Product product = new Product();
        return product;
    }
}
