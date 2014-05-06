package homework6.auction_main;

/**
 * Created by Eugene on 4/21/2014.
 */

import homework6.auction.*;
import static homework6.auction_main.BidUtils.*;

import java.util.*;

public class BidProcessor {

    private List<Bid> bids = new ArrayList<>();

    private static final IBidMessageService bms = new BidMessageService();
    private static final IBidService as = new BidService();


    public void processBid() {

        Bid maxBid;

        // Retrieve a bid
        Bid bid = as.getNextBid();

        // Add to the list
        bids.add(bid);

        // Check for under Min
        if (bid.getAmount().compareTo(getBidProductMinimalPrice(bid)) < 0 ) {
            bms.UnderMinimumPriceMessage(bid);
        } else {
            // Check for over Reserve
            if ( bid.getAmount().compareTo(getBidProductReservedPrice(bid)) >= 0) {
               bms.WinningBidMessage(bid);
            } else {
                // Check for currently winning
                maxBid = bids.stream()
                        .filter(bd -> getBidProductId(bd) == getBidProductId(bid))
                        .max((bd1, bd2) -> bd1.getAmount().compareTo(bd2.getAmount()))
                        .orElse(null);
                if (bid == maxBid) {
                    // Current winner
                    bms.CurrentHighestBidMessage(bid);
                }
            }
        }

        // Notify willing losers
        bids.stream()
                .filter(bd -> getBidProductId(bd) == getBidProductId(bid) &&
                              bd.getAmount().compareTo(bid.getAmount()) < 0 &&
                              getBidUserGetOverbidNotifications(bd))
                .forEach(bd -> bms.OutbidMessage(bd));

    }


}
