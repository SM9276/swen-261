package com.ufund.api.ufundapi.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

@Tag("Model-tier")
public class FundingBasketTests {
    private Need[] mockNeed1;
    private Need[] mockNeed2;
    private Need[] mockNeed3;
    private Need mockNeed_1;
    private Need mockNeed_2;
    private Need mockNeed_3;
    private String username;
    private String empty_username;
    private List<Need> NeedList;

    private FundingBasket fundingBasket1;
    private FundingBasket fundingBasket2;
    private FundingBasket fundingBasketEmpty;
    // NeedList[0] = new ArrayList<Need>();


    @BeforeEach
    public void setupFundingBasket() {

        mockNeed_1 = new Need("1", 1, 1, 1);
        mockNeed_2 = new Need("2", 2, 2, 2);
        mockNeed_3 = new Need("3", 3, 3, 2);
        mockNeed1[0] = mockNeed_1;
        mockNeed1[1] = mockNeed_2;
        mockNeed1[2] = mockNeed_3;
        
        username = "Joe";

        empty_username = " ";
        fundingBasket1 = new FundingBasket(username, mockNeed1);
        fundingBasketEmpty = new FundingBasket(null,null);

    }
}
//     @Test
//     public void testConstructorNotNull() {
//         mockNeed_1 = new Need("1", 1, 1, 1);
//         mockNeed_2 = new Need("2", 2, 2, 2);
//         mockNeed_3 = new Need("3", 3, 3, 2);
//         mockNeed1[0] = mockNeed_1;
//         mockNeed1[1] = mockNeed_2;
//         mockNeed1[2] = mockNeed_3;
//         mockNeed3[0] = mockNeed_1;
//         fundingBasket1 = new FundingBasket(username, mockNeed1);
//         fundingBasket2 = new FundingBasket(username, mockNeed2);
//         fundingBasketEmpty = new FundingBasket(username, new Need[0]);

//         assertNotNull(fundingBasket1);
//         assertNotNull(fundingBasket2);
//         assertNotNull(fundingBasketEmpty);
//     }
// }