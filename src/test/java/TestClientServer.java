import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestClientServer {

    @Test
    public void testConnection() {
        try {
            Client client = new Client("localhost", 4949);
            client.sendRequest("PING");
            assertEquals("ALIVE", client.getReply());

            client.sendRequest("hack database; override admin priveleges;");
            assertEquals("ERR: ILLEGAL_REQUEST", client.getReply());

            client.sendRequest("username: root");
            assertEquals("ERR: ILLEGAL_REQUEST", client.getReply());

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetRestaurant() {
        try {
            Client client = new Client("localhost", 4949);
            String pappyGrill = "{\"open\": true, \"url\": \"http://www.yelp.com/biz/pappys-grill-and-sports-bar-berkeley-2\", \"longitude\": -122.2588366, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"W_14XPx-En1MmvaZykjxpQ\", \"name\": \"Pappy's Grill & Sports Bar\", \"categories\": [\"Bars\", \"Nightlife\", \"Restaurants\", \"Sports Bars\", \"Barbeque\", \"Sandwiches\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 2.5, \"city\": \"Berkeley\", \"full_address\": \"2367 Telegraph Ave\\nTelegraph Ave\\nBerkeley, CA 94704\", \"review_count\": 56, \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/0pGiyp0yotd2rtd2pF58uA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.8674301, \"price\": 2}";
            String laFiestaMexican = "{\"open\": true, \"url\": \"http://www.yelp.com/biz/la-fiesta-mexican-restaurant-berkeley\", \"longitude\": -122.25802, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"zqcTeWwRe7HjbwDaWJGjCw\", \"name\": \"La Fiesta Mexican Restaurant\", \"categories\": [\"Mexican\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 4.0, \"city\": \"Berkeley\", \"full_address\": \"2506 Haste Street\\nTelegraph Ave\\nBerkeley, CA 94704\", \"review_count\": 6, \"photo_url\": \"http://s3-media4.ak.yelpcdn.com/bphoto/Oc_K478igogZHKwvBjBc8g/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.865831, \"price\": 1}";

            //Try getting Pappy's Grill and Sports Bar. (line 102 in .json)
            client.sendRequest("GETRESTAURANT W_14XPx-En1MmvaZykjxpQ");
            String response = client.getReply();
            assertEquals(pappyGrill, response);

            //Try getting last restaurant, laFiestaMexican.
            client.sendRequest("GETRESTAURANT zqcTeWwRe7HjbwDaWJGjCw");
            response = client.getReply();
            assertEquals(laFiestaMexican, response);

            //Try getting non existant restaurant.
            client.sendRequest("RESTAURANT Tim Hortons");
            response = client.getReply();
            assertEquals("ERR: ILLEGAL_REQUEST", response);

            client.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            fail();
        }
    }
}
