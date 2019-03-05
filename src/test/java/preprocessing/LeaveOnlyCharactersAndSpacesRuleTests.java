package preprocessing;

import knn.preprocessing.LeaveOnlyCharactersAndSpacesRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeaveOnlyCharactersAndSpacesRuleTests {
    @Test
    public void removalTest() {
        String text = "13..23.24.2,,,xas  das  dssdASD";
        int initialSize = text.length();

        text = new LeaveOnlyCharactersAndSpacesRule().applyTo(text);
        int postprocessingSize = text.length();


        assertEquals(initialSize, postprocessingSize + 14);
    }
}
