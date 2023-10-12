package Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import Model.Player;

public class PlayerTest {
    private Player player;
    @Before
    public void initialization() {
        player = new Player("George Burdell", "Burdell", 3);
    }

    @Test
    public void testName () {
        assertEquals("George Burdell", player.getName());
    }
    @Test
    public void testSprite () {
        assertEquals("Burdell", player.getSprite().getImageName());

    }
    @Test
    public void testHealth () {
        assertEquals(3, player.getHealth());
    }

    @Test
    public void testDefaultScore () {
        assertEquals(10, player.getScore());
    }

    @Test
    public void testSubScore () {
        player.subScore(4);
        assertEquals(6, player.getScore());
    }
    @Test
    public void testNegativeScore () {
        player.subScore(12);
        assertEquals(0, player.getScore());
    }




}
