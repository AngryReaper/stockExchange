import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.*;


public class MainTest {
    @Test
    public void buy() throws Exception {
        LinkedList<Integer> testList = new LinkedList<>(Arrays.asList(4350,370,120,950,560));
        String testStock = "B";
        int testPrice = 7;
        int testAmount = 3;
        LinkedList<Integer> expectedNormalResult = new LinkedList<>(Arrays.asList(4329,370,123,950,560));
        LinkedList<Integer> result = Main.buy(testList,testStock,testPrice,testAmount);

        assertEquals(expectedNormalResult,result);

    }

    @Test
    public void sell() throws Exception {
        LinkedList<Integer> testList = new LinkedList<>(Arrays.asList(1000,130,240,760,320));
        String testStock = "C";
        int testPrice = 15;
        int testAmount = 3;

        LinkedList<Integer> expectedNormalResult = new LinkedList<>(Arrays.asList(1045,130,240,757,320));
        LinkedList<Integer> result = Main.sell(testList,testStock,testPrice,testAmount);

        assertEquals(expectedNormalResult,result);

    }

}