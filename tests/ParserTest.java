import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parsePossibilities() {
        Parser parser = new Parser();
        boolean[] result = parser.parsePossibilities("\"down\":\"0\",\n" +
                "    \"left\":\"+\",\n" +
                "    \"right\":\"0\",\n" +
                "    \"up\":\"+\"\n");

        boolean[] expected = new boolean[]{false, false, true, true};

        assertArrayEquals(expected, result);
    }

    @Test
    void parseSize() {
        Parser parser = new Parser();
        int[] result = parser.parseSize("3x1");

        int[] expected = new int[]{7, 3};

        assertArrayEquals(expected, result);
    }

    @Test
    void parseStartPosition() {
        Parser parser = new Parser();
        int[] result = parser.parseStartPosition("2,3");

        int[] expected = new int[]{3, 5};

        assertArrayEquals(expected, result);
    }

    @Test
    void parseMovesCorrect() {
        Parser parser = new Parser();
        int result = parser.parseMoves("23");

        int expected = 23;

        assertEquals(expected, result);
    }

    @Test
    void parseMovesIncorrect() {
        Parser parser = new Parser();
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            parser.parseMoves("duck");
        });

        String expectedMsg = "Cannot format to Integer!";
        String resultMsg = exception.getMessage();

        assertEquals(expectedMsg, resultMsg);
    }
}