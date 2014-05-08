package homework6.auction_main;

import homework6.auction.*;
import static homework6.auction_main.BidUtils.*;

/**
 * Created by Eugene on 5/5/2014.
 */



public class BidMessageService implements IBidMessageService {

    private static final String UNDER_MIN = "did not reach the minimal price";
    private static final String YOU_WON = "is the winning bid. Congratulations!";
    private static final String HIGHEST = "is currently the highest bid";
    private static final String OUTBID = "is below the current maximum bid. You've been outbid";

    private void printMessage(Bid bid, String message) {
        System.out.println("Dear " + getBidUserName(bid) + " Your bid for " + getBidProductTitle(bid) + " in the amount of " + bid.getAmount().toPlainString() + " " + message);
    }


     public void underMinimumPriceMessage(Bid bid) {
        printMessage(bid, UNDER_MIN);
    }


    public void winningBidMessage(Bid bid) {
        printMessage(bid, YOU_WON);
    }


    public void currentHighestBidMessage(Bid bid) {
        printMessage(bid, HIGHEST);
    }


    public void outbidMessage(Bid bid) {
        printMessage(bid, OUTBID);
    }
}
