package model.enums;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import model.enums.TravelDistance;

import org.junit.Test;

public class TravelDistanceTest {

    @Test
    public void test() {
        TravelDistance travelDistance = TravelDistance.ONE_UNIT_SQUARE;
        assertFalse(travelDistance.edgeOfBoard());

        travelDistance = TravelDistance.EDGE_OF_BOARD;
        assertTrue(travelDistance.edgeOfBoard());
    }

}
