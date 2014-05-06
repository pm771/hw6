package homework6.auction_main;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Eugene on 5/6/2014.
 */
public class Runner {

    public static void main(String[] args) {
        BidProcessor ar = new BidProcessor();
        Timer tmr = new Timer();
        TimerTask placeBids = new TimerTask() {
            @Override
            public void run() {
                ar.processBid();
            }
        };

        tmr.schedule(placeBids, 1000, 5000);

    }
}
