package homework6.main;

/**
 * Created by Eugene on 4/21/2014.
 */

import homework6.auction.*;
import static homework6.main.BidUtils.*;

import java.math.BigDecimal;
import java.util.*;

public class AuctionRunner {

    static Random rn = new Random();

    private User[] users = new User[2];
    private Product[] products = new Product[2];
    private List<Bid> bids = new ArrayList<>();



    public AuctionRunner() {

        // Hard-codes Users

        users[0] = new User();
        users[0].setId(101);
        users[0].setName("First User");


        users[1] = new User();
        users[1].setId(102);
        users[1].setName("Second User");
        users[1].setGetOverbidNotifications(true);

        // Hard-coded Products

        products[0] = new Product();
        products[0].setId(1001);
        products[0].setTitle("Product #1");
        products[0].setMinimalPrice(new BigDecimal(535.00));
        products[0].setReservedPrice(new BigDecimal(999.99));

        products[1] = new Product();
        products[1].setId(1002);
        products[1].setTitle("Product #2");
        products[1].setMinimalPrice(new BigDecimal(10000));
        products[1].setReservedPrice(new BigDecimal(45500));

    }

    private void printMessage(Bid bid, String message) {
        System.out.println("Dear " + getBidUserName(bid) + " Your bid for " + getBidProductTitle(bid) + " in the amount of " + bid.getAmount().toPlainString() + " " + message);
    }

    private void processRandomBid() {


        Bid maxBid;

        // Create a Random Bid
        Bid bid = new Bid();
        bid.setAmount( new BigDecimal((rn.nextDouble() + 1) * (Math.pow(10, rn.nextInt(6)))) );
        bid.setId( rn.nextInt() );
        bid.setUser( users[rn.nextInt(users.length)] );
        bid.setProduct( products[rn.nextInt(products.length)] );

        // Add to the list
        bids.add(bid);

        // Check for under Min
        if (bid.getAmount().compareTo(getBidProductMinimalPrice(bid)) < 0 ) {
            printMessage(bid, "did not reach the minimal price");
        } else {
            // Check for over Reserve
            if ( bid.getAmount().compareTo(getBidProductReservedPrice(bid)) >= 0) {
               printMessage(bid, "is the ultimate winning bid. Congratulations!!!" );
            } else {
                // Check for currently winning
                maxBid = bids.stream()
                        .filter(bd -> getBidProductId(bd) == getBidProductId(bid))
                        .max((bd1, bd2) -> bd1.getAmount().compareTo(bd2.getAmount()))
                        .orElse(null);
                if (bid == maxBid) {
                    // Current winner
                    printMessage(bid, "is currently a winning bid");
                }
            }
        }

        // Notify willing losers
        bids.stream()
                .filter(bd -> getBidProductId(bd) == getBidProductId(bid) &&
                              bd.getAmount().compareTo(bid.getAmount()) < 0 &&
                              getBidUserGetOverbidNotifications(bd))
                .forEach(bd -> printMessage(bd, "is below the current maximum bid. You've been outbid"));

    }

    public static void main(String[] args) {
        AuctionRunner ar = new AuctionRunner();
        Timer tmr = new Timer();
        TimerTask placeBids = new TimerTask() {
            @Override
            public void run() {
                ar.processRandomBid();
            }
        };

        tmr.schedule(placeBids, 1000, 5000);

    }
}
