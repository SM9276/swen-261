import { Need } from "./need";
export interface FundingBasket{
    username: String;
    needs: Need[];
    bought: Need[];
}