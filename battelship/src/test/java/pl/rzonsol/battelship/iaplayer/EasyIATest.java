package pl.rzonsol.battelship.iaplayer;

import org.junit.Before;
import org.junit.Test;
import pl.rzonsol.battelship.board.Board;

import static org.junit.Assert.assertNotNull;

/**
 * Created by rzonsol on 09.12.2016.
 */
public class EasyIATest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board();
    }

    @Test
    public void EasyIaShoot() throws Exception {
        //arrange
        EasyIA iaLavel =new EasyIA();
        //act
        assertNotNull(iaLavel.IaShoot(board));
    }

}