package preprocessing;

import knn.preprocessing.ComaRemoval;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ComalRemovalTests {
    @Test
    public void removalTest() {
        String text = "13..23.24.2,,,xasdas";
        int initialSize = text.length();

        text = new ComaRemoval().applyTo(text);
        int postprocessingSize = text.length();


        assertEquals(initialSize, postprocessingSize + 3);
    }
}
