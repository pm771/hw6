package homework6.auction_main

import homework6.auction.*
import spock.lang.*


/**
 * Created by Eugene on 5/1/2014.
 */

class SetUpTests extends Specification {

    @Shared List<Bid> bids
    @Shared BidProcessor bidProcessor

    def setup() {
        bidProcessor = new BidProcessor()
        bids = bidProcessor.bids
    }

    def "List of bids exists and initially empty"() {
        expect:
        bids != null

        and:
        bids.size() == 0
    }

    def "Bids are added to the list"() {
        setup:
        IBidMessageService messageService = Mock()
        bidProcessor.messageService = Mock(IBidMessageService)

        def num = 10

        when:
        num.times  { bidProcessor.processBid() }

        then:
        bids.size() == old(bids.size()) + num
    }

    def "Bids have not null properties" () {

        when:
        Bid bid = bidProcessor.bidService.getNextBid()

        then:
        bid.user != null
        bid.product != null
    }
}

class BiddingTests extends Specification {


    @Shared IBidMessageService messageService
    @Shared IBidService bidService
    @Shared Bid bid
    @Shared BidProcessor bidProcessor


    def setup() {
        bidProcessor = new BidProcessor()
        bid = bidProcessor.bidService.getNextBid()
        bid.product.minimalPrice = 100
        bid.product.reservedPrice = 500

        bidService = Mock()
        bidProcessor.bidService = bidService

        messageService = Mock()
        bidProcessor.messageService = messageService
    }


    def "below a minimum price"() {
        setup:
        bid.amount = 10
        bidService.getNextBid() >> bid

        when:
        bidProcessor.processBid()

        then:
        1 * messageService.underMinimumPriceMessage(bid)
        0 * messageService.winningBidMessage(_)
        0 * messageService.currentHighestBidMessage(_)
        0 * messageService.outbidMessage(_)
    }

    def "above minimum price and below reserved price"() {
        setup:
        bid.amount = 400
        bidService.getNextBid() >> bid

        when:
        bidProcessor.processBid()

        then:
        0 * messageService.underMinimumPriceMessage(_)
        0 * messageService.winningBidMessage(_)
        1 * messageService.currentHighestBidMessage(bid)
        0 * messageService.outbidMessage(_)
    }

    def "above reserved price"() {
        setup:
        bid.amount = 600
        bidService.getNextBid() >> bid

        when:
        bidProcessor.processBid()

        then:
        0 * messageService.underMinimumPriceMessage(_)
        1 * messageService.winningBidMessage(bid)
        0 * messageService.currentHighestBidMessage(_)
        0 * messageService.outbidMessage(_)
    }

    def "outbid"() {
        setup:
        bid.amount = 200
        bid.user.id = 10
        bid.user.getOverbidNotifications = false

        Bid bid1 = new Bid(bid)
        bid1.product = bid.product
        bid1.user = bid.user
        bid1.user.id = 20
        bid1.user.getOverbidNotifications = true
        bid1.amount = 300

        Bid bid2 = new Bid(bid)
        bid2.product = bid.product
        bid1.user = bid.user
        bid2.user.id = 30
        bid2.user.getOverbidNotifications = true
        bid2.amount = 400

        bidProcessor.bids << bid
        bidProcessor.bids << bid1
        bidService.getNextBid() >> bid2

        when:
        bidProcessor.processBid()

        then:
        bidProcessor.bids.size() == 3
        0 * messageService.underMinimumPriceMessage(_)
        0 * messageService.winningBidMessage(_)
        1 * messageService.currentHighestBidMessage(bid2)
        1 * messageService.outbidMessage(_)
        1 * messageService.outbidMessage(bid1)
    }

}