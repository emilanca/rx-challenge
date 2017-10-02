package org.sample.rx.deals.client;

import org.sample.rx.deals.client.response.ProductResponse;

import java.util.UUID;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class ProductClient {

    // get 1 product from its id
    public Single<ProductResponse> getProductById(UUID id) {
        throw new UnsupportedOperationException("To be mocked");
    }

    // get products from a comma separated list of ids
    public Flowable<ProductResponse> getProductsByIds(String productIds) {
        throw new UnsupportedOperationException("To be mocked");
    }
}
