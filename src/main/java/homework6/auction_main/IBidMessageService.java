package homework6.auction_main;

import homework6.auction.*;

/**
 * Created by Eugene on 5/5/2014.
 */
public interface IBidMessageService {

    public void UnderMinimumPriceMessage(Bid bid);

    public void WinningBidMessage(Bid bid);

    public void CurrentHighestBidMessage(Bid bid);

    public void OutbidMessage(Bid bid);
}
