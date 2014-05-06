package homework6.auction_main;

import homework6.auction.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Eugene on 5/5/2014.
 */
public class BidService implements IBidService {

    private static Random rn = new Random();
    private static int id = rn.nextInt(1000);

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();

    public BidService() {
        setAuctionUsers();
        setAuctionProducts();
    }

    // Existing users
    private void setAuctionUsers() {

        User user;

        // Hard-codes Users

        user = new User();
        user.setId(101);
        user.setName("First User");
        users.add(user);


        user = new User();
        user.setId(102);
        user.setName("Second User");
        user.setGetOverbidNotifications(true);
        users.add(user);

    }
    // Existing products
    private void setAuctionProducts() {
        Product product;

        // Hard-coded Products

        product = new Product();
        product.setId(1001);
        product.setTitle("Product #1");
        product.setMinimalPrice(new BigDecimal(535.00));
        product.setReservedPrice(new BigDecimal(999.99));
        products.add(product);

        product = new Product();
        product.setId(1002);
        product.setTitle("Product #2");
        product.setMinimalPrice(new BigDecimal(10000));
        product.setReservedPrice(new BigDecimal(45500));
        products.add(product);
    }

    public Bid getNextBid() {
        return generateRandomBid();
    }

    private Bid generateRandomBid() {

        return new Bid(id++,
                products.get(rn.nextInt(products.size())),
                new BigDecimal((rn.nextDouble() + 1) * (Math.pow(10, rn.nextInt(6)))),
                rn.nextInt(100) + 1,
                users.get(rn.nextInt(users.size())),
                java.time.LocalDateTime.now(java.time.Clock.systemDefaultZone()),
                false);
    }

}
