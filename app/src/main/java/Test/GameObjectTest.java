package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.*;

import Model.GameObject;
import Model.Player;

public class GameObjectTest {
    @Test
    public void testingSingletonProperty() {
        GameObject instance1 = GameObject.getGameObject();
        GameObject instance2 = GameObject.getGameObject();

        assertEquals(instance1, instance2);
    }
    @Test
    public void testPlayer() {
        Player newPlayer = Player.getPlayer();
        newPlayer.setPlayer("Test name","Test Sprite",1);
        String testDifficulty = "Hard";

        GameObject gameObject = GameObject.getGameObject();
        gameObject.configGame(newPlayer,testDifficulty);

        assertEquals(newPlayer,  gameObject.getPlayer());


    }

    @Test
    public void testDifficulty() {
        Player newPlayer = Player.getPlayer();
        newPlayer.setPlayer("Test name","Test Sprite",1);
        String testDifficulty = "Hard";

        GameObject gameObject = GameObject.getGameObject();
        gameObject.configGame(newPlayer,testDifficulty);

        assertEquals(testDifficulty, gameObject.getDifficulty());
    }

    @Test
    public void testDefaultValues() {
        GameObject gameObject = GameObject.getGameObject();
        assertNull(gameObject.getPlayer());
        assertNull(gameObject.getDifficulty());
    }




}

