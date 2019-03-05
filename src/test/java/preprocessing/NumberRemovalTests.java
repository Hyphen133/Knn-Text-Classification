package preprocessing;

import knn.preprocessing.NumberRemoval;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberRemovalTests {
    @Test
    public void removalTest() {
        String text = "13..23.24698.2,,,xasdas";
        int initialSize = text.length();

        text = new NumberRemoval().applyTo(text);
        int postprocessingSize = text.length();


        assertEquals(initialSize, postprocessingSize + 10);
    }
}
