package homework6.auction_main;

/**
 * Created by Eugene on 4/21/2014.
 */

import homework6.auction.*;
import static homework6.auction_main.BidUtils.*;

import java.util.*;

public class BidProcessor {

    List<Bid> bids = new ArrayList<>();

    IBidMessageService messageService = new BidMessageService();
    IBidService bidService = new BidService();


    public void processBid() {

        Bid maxBid;

        // Retrieve a bid
        Bid bid = bidService.getNextBid();

        // Add to the list
        bids.add(bid);

        // Check for under Min
        if (bid.getAmount().compareTo(getBidProductMinimalPrice(bid)) < 0 ) {
            messageService.underMinimumPriceMessage(bid);
        } else {
            // Check for over Reserve
            if ( bid.getAmount().compareTo(getBidProductReservedPrice(bid)) >= 0) {
               messageService.winningBidMessage(bid);
            } else {
                // Check for currently winning
                maxBid = bids.stream()
                        .filter(bd -> getBidProductId(bd) == getBidProductId(bid))
                        .max((bd1, bd2) -> bd1.getAmount().compareTo(bd2.getAmount()))
                        .orElse(null);
                if (bid == maxBid) {
                    // Current winner
                    messageService.currentHighestBidMessage(bid);
                }
            }
        }

        // Notify willing losers
        bids.stream()
                .filter(bd -> getBidProductId(bd) == getBidProductId(bid) &&
                              bd.getAmount().compareTo(bid.getAmount()) < 0 &&
                              getBidUserGetOverbidNotifications(bd))
                .forEach(bd -> messageService.outbidMessage(bd));

    }


}
