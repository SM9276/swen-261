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
    private Product[] mockProduct1;
    private Product[] mockProduct2;
    private Product[] mockProduct3;
    private Product mockProduct_1;
    private Product mockProduct_2;
    private Product mockProduct_3;
    private String username;
    private String empty_username;
    private List<Product> productList;

    private FundingBasket fundingBasket1;
    private FundingBasket fundingBasket2;
    private FundingBasket fundingBasketEmpty;
    // productList[0] = new ArrayList<Product>();


    @BeforeEach
    public void setupFundingBasket() {

        mockProduct_1 = new Product("1", 1, 1, 1);
        mockProduct_2 = new Product("2", 2, 2, 2);
        mockProduct_3 = new Product("3", 3, 3, 2);
        mockProduct1[0] = mockProduct_1;
        mockProduct1[1] = mockProduct_2;
        mockProduct1[2] = mockProduct_3;
        
        username = "Joe";

        empty_username = " ";
        fundingBasket1 = new FundingBasket(username, mockProduct1);
        fundingBasketEmpty = new FundingBasket(null,null);

    }
}
//     @Test
//     public void testConstructorNotNull() {
//         mockProduct_1 = new Product("1", 1, 1, 1);
//         mockProduct_2 = new Product("2", 2, 2, 2);
//         mockProduct_3 = new Product("3", 3, 3, 2);
//         mockProduct1[0] = mockProduct_1;
//         mockProduct1[1] = mockProduct_2;
//         mockProduct1[2] = mockProduct_3;
//         mockProduct3[0] = mockProduct_1;
//         fundingBasket1 = new FundingBasket(username, mockProduct1);
//         fundingBasket2 = new FundingBasket(username, mockProduct2);
//         fundingBasketEmpty = new FundingBasket(username, new Product[0]);

//         assertNotNull(fundingBasket1);
//         assertNotNull(fundingBasket2);
//         assertNotNull(fundingBasketEmpty);
//     }
// }