package homework6

import homework6.auction_main.AuctionRunner
import spock.lang.*


/**
 * Created by Eugene on 5/1/2014.
 */
class AuctionTest extends Specification {

    def @Shared ar = new AuctionRunner()


    def "non-empty products created"() {
        expect: ar.products.length > 0
    }

    def "non-empty users created"() {
        expect: ar.users.length > 0
    }


    /*
    def "two consecutive bid IDs are not equal"() {
        setup:
            def id1 = ar.randomBid().id
            def id2 = ar.randomBid().id
        expect:
            id1 != id2

    }

    def "multiple consecutive bid IDs are unique"() {
        setup: "10,000 IDs"
            def ids = (1..10000).collect { ar.randomBid().id }

        expect: "are unique"
            // use unique(false) not to mutate the list
            ids.unique(false) == ids
    }
    */

    def "bidding below a minimum price"() {
        given:
        def messageService = Mock(IMessageService)


    }
}