package org.sample.rx.deals.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sample.rx.deals.client.DealClient;
import org.sample.rx.deals.client.ProductClient;
import org.sample.rx.deals.client.response.DealResponse;
import org.sample.rx.deals.client.response.ProductResponse;
import org.sample.rx.deals.model.Deal;

import java.net.SocketException;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static java.util.stream.Collectors.toList;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;
import static org.sample.rx.deals.Helper.readFromFile;

@RunWith(MockitoJUnitRunner.class)
public class DealServiceTest {

    private static final UUID
            MERCHANT_ID    = UUID.fromString("e2fc2fe3-0d9b-4311-a9e7-43f5cf5f1427"),
            DEAL_001_ID    = UUID.fromString("11490d02-ae09-41ae-b03b-97194ccb7d0f"),
            DEAL_002_ID    = UUID.fromString("729b301f-b1e5-42f8-b069-24c678731b2d"),
            PRODUCT_001_ID = UUID.fromString("3b992f95-6bd8-4532-a112-5962952600d4"),
            PRODUCT_002_ID = UUID.fromString("2b046e34-737c-421d-8c01-0d55dee639f6"),
            PRODUCT_003_ID = UUID.fromString("68e340f2-9eb0-4139-8d3b-d19da8f81733"),
            PRODUCT_101_ID = UUID.fromString("31b3cc5c-d5d2-4f94-95a8-ea9ebf729a76"),
            PRODUCT_102_ID = UUID.fromString("3658d3ff-2f11-45a8-996b-d0e32d0dd8d8"),
            PRODUCT_103_ID = UUID.fromString("c7ae2929-bc65-4dfe-87d6-39b6259744d0");

    private static final UUID
            ERROR_DEAL_001_ID = UUID.fromString("4d6c6e83-40e4-488f-b330-8baf8f2aaf02"),
            ERROR_DEAL_002_ID = UUID.fromString("a92ca5c9-d60b-4772-aea0-285c6f78eaaa");
    @Mock
    private DealClient dealClient;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private DealService dealService;

    @Before
    public void setUp() throws Exception {
        List<UUID> merchantDealIds =
                ((List<String>) readFromFile("deal/merchant_deals.json", List.class)).stream()
                                                                                     .map(UUID::fromString)
                                                                                     .collect(toList());
        when(dealClient.getDealsByMerchant(MERCHANT_ID))
                .thenReturn(Flowable.fromIterable(merchantDealIds));
        //
        when(dealClient.getDealById(DEAL_001_ID))
                .thenReturn(Single.just(readFromFile("deal/deal_001.json",
                                                     DealResponse.class)));
        when(dealClient.getDealById(DEAL_002_ID))
                .thenReturn(Single.just(readFromFile("deal/deal_002.json",
                                                     DealResponse.class)));
        //
        when(productClient.getProductById(PRODUCT_001_ID))
                .thenReturn(Single.just(readFromFile("product/product_001.json",
                                                     ProductResponse.class)));
        when(productClient.getProductById(PRODUCT_002_ID))
                .thenReturn(Single.just(readFromFile("product/product_002.json",
                                                     ProductResponse.class)));
        when(productClient.getProductById(PRODUCT_003_ID))
                .thenReturn(Single.just(readFromFile("product/product_003.json",
                                                     ProductResponse.class)));
        when(productClient.getProductById(PRODUCT_101_ID))
                .thenReturn(Single.just(readFromFile("product/product_101.json",
                                                     ProductResponse.class)));
        when(productClient.getProductById(PRODUCT_102_ID))
                .thenReturn(Single.just(readFromFile("product/product_102.json",
                                                     ProductResponse.class)));
        when(productClient.getProductById(PRODUCT_103_ID))
                .thenReturn(Single.just(readFromFile("product/product_103.json",
                                                     ProductResponse.class)));
        //
        when(dealClient.getDealById(ERROR_DEAL_001_ID))
                .thenReturn(Single.error(new RuntimeException("Error")));
        when(dealClient.getDealById(ERROR_DEAL_002_ID))
                .thenReturn(Single.error(new SocketException("Cannot connect to host")));
    }

    @Test
    public void testGetDealById() throws Exception {
        Deal deal = dealService.getDealById(DEAL_001_ID).blockingGet();
        assertEquals(DEAL_001_ID, deal.getId());
        assertEquals(MERCHANT_ID, deal.getMerchantId());
        assertEquals(3, deal.getProducts().size());
        assertEquals("Deal title", deal.getTitle());
        assertEquals(OffsetDateTime.parse("2017-01-01T00:00:00Z"), deal.getStartAt());
        assertEquals(OffsetDateTime.parse("2017-01-05T00:00:00Z"), deal.getEndAt());
    }

    @Test
    public void testGetDealsByMerchantId() throws Exception {
        List<Deal> deals = dealService.getDealsByMerchantId(MERCHANT_ID, Optional.<Comparator<Deal>>empty())
                                      .toList().blockingGet();
        assertEquals("Another Deal title", deals.get(1).getTitle());
        assertEquals("Deal title", deals.get(0).getTitle());
        // TODO assert more ...
    }

    // TODO : more tests for DealService
}
