package homework6.auction_main;

import homework6.auction.*;

/**
 * Created by Eugene on 5/5/2014.
 */
public interface IBidMessageService {

    public void underMinimumPriceMessage(Bid bid);

    public void winningBidMessage(Bid bid);

    public void currentHighestBidMessage(Bid bid);

    public void outbidMessage(Bid bid);
}
