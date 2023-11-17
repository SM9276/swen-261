package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * The unit test suite for the Hero class
 * 
 * @author SWEN Faculty
 */
@Tag("Model-tier")
public class FundingBasketTests{
    @Test
    public void testCtor() {
        // Setup Name
        String khoi = "khoi";
        //Setup Needs
        Need need1 = new Need(1, "Cookie", 1, 1, 1);
        Need need2 = new Need(2, "Milk", 2, 3, 4);
        //Setup Need lists
        Need[] needList = new Need[1];
        Need[] boughtList = new Need[1];
        //Add to Need lists
        needList[0] = need1;
        boughtList[0] = need2;
        // Invoke
        FundingBasket fundingBasket = new FundingBasket(khoi, needList, boughtList);

        // Analyze
        assertEquals(khoi, fundingBasket.getFundingBasket());
        assertEquals(boughtList,fundingBasket.getBought());
        assertEquals(needList, fundingBasket.getNeeds());
    }

    @Test
    public void testSetNeeds() {
        // Setup original Need List
        String khoi = "khoi";
        Need need = new Need(1, "khoi", 1, 2, 3);
        Need[] needList = new Need[1];
        needList[0] = need;
        //Setup fundingBasket
        FundingBasket fundingBasket = new FundingBasket(khoi, needList, needList);

        //Set new need list
        Need new_need = new Need(2, "boy", 3, 2, 1);
        Need[] expected_needs = new Need[1];
        expected_needs[0] = new_need;

        // Invoke
        fundingBasket.setNeeds(expected_needs);

        // Analyze
        assertEquals(expected_needs,fundingBasket.getNeeds());
    }

    @Test
    public void testSetBought() {
        // Setup original Need List
        String khoi = "khoi";
        Need bought = new Need(1, "khoi", 1, 2, 3);
        Need[] needList = new Need[1];
        needList[0] = bought;
        //Setup fundingBasket
        FundingBasket fundingBasket = new FundingBasket(khoi, needList, needList);

        //Set new need list
        Need new_bought = new Need(2, "boy", 3, 2, 1);
        Need[] expected_bought = new Need[1];
        expected_bought[0] = new_bought;

        // Invoke
        fundingBasket.setBought(expected_bought);

        // Analyze
        assertEquals(expected_bought,fundingBasket.getBought());
    }

    // @Test
    // public void testToString() {
    //     // Setup
    //     String khoi = "khoi";
    //     Need[] needList = new Need[1];
    //     Need[] boughtList = new Need[1];
    //     String expected_string = String.format(FundingBasket.STRING_FORMAT,khoi, needList);
        
    //     FundingBasket fundingBasket = new FundingBasket(expected_string, needList, boughtList);
        
    //     // Invoke
    //     String actual_string = fundingBasket.toString();

    //     // Analyze
    //     assertEquals(expected_string,actual_string);
    // }
}