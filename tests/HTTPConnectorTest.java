import Exceptions.BadRequestException;
import Exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HTTPConnectorTest {

    @Test
    public void getSize() throws BadRequestException, NotFoundException {
        HTTPConnector con = new HTTPConnector("684219c0", 1);
        int[] sizeRes = con.getSize();

        int[] sizeExp = new int[]{7, 3};

        assertArrayEquals(sizeExp, sizeRes);
    }

    @Test
    void getStartPosition() throws BadRequestException, NotFoundException {
        HTTPConnector con = new HTTPConnector("684219c0", 2);
        int[] posRes = con.getStartPosition();

        int[] posExp = new int[]{3, 5};

        assertArrayEquals(posExp, posRes);
    }

    @Test
    void getMoves() throws NotFoundException, BadRequestException {
        HTTPConnector con = new HTTPConnector("684219c0", 3);
        int moves = con.getMoves();

        int exp = 0;

        assertEquals(exp, moves);
    }

    @Test
    void getPossibilities() throws BadRequestException, NotFoundException {
        HTTPConnector con = new HTTPConnector("684219c0", 4);
        boolean[] possRes = con.getPossibilities();

        boolean[] possExp = new boolean[]{false, false, true, true};

        assertArrayEquals(possExp, possRes);
    }
}
