package org.sample.rx.deals.client;

import org.sample.rx.deals.client.response.DealResponse;

import java.util.UUID;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class DealClient {

    // get 1 deal by id
    public Single<DealResponse> getDealById(UUID id) {
        throw new UnsupportedOperationException("To be mocked");
    }

    // get all deals of a merchant by merchantId
    public Flowable<UUID> getDealsByMerchant(UUID merchantId) {
        throw new UnsupportedOperationException("To be mocked");
    }
}
