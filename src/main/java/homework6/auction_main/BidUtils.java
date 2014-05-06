package homework6.auction_main;

import homework6.auction.*;

import java.math.BigDecimal;


/**
 * Created by Eugene on 4/24/2014.
 */
public class BidUtils {


    // bidUser getters

    public static String getBidUserName(Bid bid) { return bid.getUser().getName(); }

    public static boolean getBidUserGetOverbidNotifications(Bid bid) { return bid.getUser().isGetOverbidNotifications(); }

    // bidProduct getters

    public static String getBidProductTitle(Bid bid) { return bid.getProduct().getTitle(); }

    public static int getBidProductId(Bid bid) { return bid.getProduct().getId(); }

    public static BigDecimal getBidProductMinimalPrice(Bid bid) { return bid.getProduct().getMinimalPrice(); }

    public static BigDecimal getBidProductReservedPrice(Bid bid) { return bid.getProduct().getReservedPrice(); }


}
