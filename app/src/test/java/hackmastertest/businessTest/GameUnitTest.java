package hackmastertest.businessTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hackmaster.application.Services;
import hackmaster.business.DeckManager;
import hackmaster.business.Game;
import hackmaster.business.SetUpGame;
import hackmaster.objects.CardClass;
import hackmaster.objects.PlayerClass;
import hackmaster.objects.ResourceClass;
import hackmastertest.persistenceTest.DataAccessStub;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;


public class GameUnitTest {
    Game testGame;

    @Before
    public void setUp(){
        Services.closeDataAccess();
        DataAccessStub dbStub = new DataAccessStub("stub");
        Services.createDataAccess(dbStub,dbStub,dbStub);

        testGame = SetUpGame.setUpSinglePlayerGame();
    }

    private CardClass[] resetDeck() {
        CardClass[] testDeck = new CardClass[10];
        testDeck[0] = new CardClass(0, "Nothing", "Defense", "Do Nothing",
                new ResourceClass(0, 0, 0, 0, 0,0, 0), null);
        testDeck[1] = new CardClass(0, "Normal card", "Defense", "Costs a normal amount",
                new ResourceClass(0, -1, 0, -1, 0,-1, 0), null);
        testDeck[2] = new CardClass(0, "Expensive Health", "Attack", "Costs a lot of Health",
                new ResourceClass(-2000, 0, 0, 0, 0,0, 0), null);
        testDeck[3] = new CardClass(0, "Expensive HCoin", "Attack", "Costs a lot of HCoin",
                new ResourceClass(0, -2000, 0, 0, 0,0, 0), null);
        testDeck[4] = new CardClass(0, "Expensive BotNet", "Attack", "Costs a lot of Botnet",
                new ResourceClass(0, 0, 0, -2000, 0,0, 0), null);
        testDeck[5] = new CardClass(0, "Expensive GPU", "Attack", "Costs a lot of CPU",
                new ResourceClass(0, 0, 0, 0, 0,-2000, 0), null);
        testDeck[6] = new CardClass(0, "Generate Health", "Attack", "Makes a lot of Health",
                new ResourceClass(2000, 0, 0, 0, 0,0, 0), null);
        testDeck[7] = new CardClass(0, "Generate HCoin", "Attack", "Makes a lot of HCoin",
                new ResourceClass(0, 2000, 0, 0, 0,0, 0), null);
        testDeck[8] = new CardClass(0, "Generate BotNet", "Attack", "Makes a lot of BotNet",
                new ResourceClass(0, 0, 0, 2000, 0,0, 0), null);
        testDeck[9] = new CardClass(0, "Generate CPU", "Attack", "Makes a lot of CPU",
                new ResourceClass(0, 0, 0, 0, 0,2000, 0), null);

        return testDeck;
    }

    @Test
    public void testSetDeck() {
        CardClass[] testDeck = new CardClass[3];
        testDeck[0] = new CardClass(0, "Nothing", "Defense", "Do Nothing",
                new ResourceClass(0, 0, 0, 0, 0,0, 0), null);
        testDeck[1] = new CardClass(0, "Normal card", "Defense", "Costs a normal amount",
                new ResourceClass(0, -1, 0, -1, 0,-1, 0), null);
        testDeck[2] = new CardClass(0, "Expensive Health", "Attack", "Costs a lot of Health",
                new ResourceClass(-2000, 0, 0, 0, 0,0, 0), null);
        testGame.setDeck(testDeck);

        assertEquals(3, testGame.getDeck().length);

        testGame.setDeck(new CardClass[0]);
        assertEquals(0, testGame.getDeck().length);

        testGame.setDeck(resetDeck());
        assertEquals(10, testGame.getDeck().length);
    }

    private void setPlayerHands() {
        testGame.getPlayer1().setCard(0, testGame.getDeckCardAt(0));
        testGame.getPlayer1().setCard(1, testGame.getDeckCardAt(1));
        testGame.getPlayer1().setCard(2, testGame.getDeckCardAt(2));
        testGame.getPlayer1().setCard(3, testGame.getDeckCardAt(3));
        testGame.getPlayer1().setCard(4, testGame.getDeckCardAt(4));

        testGame.getPlayer2().setCard(0, testGame.getDeckCardAt(5));
        testGame.getPlayer2().setCard(1, testGame.getDeckCardAt(6));
        testGame.getPlayer2().setCard(2, testGame.getDeckCardAt(7));
        testGame.getPlayer2().setCard(3, testGame.getDeckCardAt(8));
        testGame.getPlayer2().setCard(4, testGame.getDeckCardAt(9));
    }

    @Test
    public void testInvalidPlayCardEvent() {
        try {
            testGame.playCardEvent(-1);
            fail("ArrayIndexOutOfBoundsException Expected or RuntimeException Expected");
        } catch ( ArrayIndexOutOfBoundsException exp) {
        } catch ( RuntimeException exp) {
        }
        try {
            testGame.playCardEvent(6);
            fail("ArrayIndexOutOfBoundsException Expected or RuntimeException Expected");
        } catch ( ArrayIndexOutOfBoundsException exp) {
        } catch ( RuntimeException exp) {
        }
    }

    @Test
    public void testCheckCard() {
        testGame.setDeck(resetDeck());
        setPlayerHands();

        assertEquals( true, testGame.checkCard(0, testGame.getPlayer1()));
        assertEquals(true, testGame.checkCard(1, testGame.getPlayer1()));
        assertEquals(false, testGame.checkCard(2, testGame.getPlayer1()));
        assertEquals(false, testGame.checkCard(3, testGame.getPlayer1()));
        assertEquals(false, testGame.checkCard(4, testGame.getPlayer1()));

        assertEquals(false, testGame.checkCard(0, testGame.getPlayer2()));
        assertEquals(true, testGame.checkCard(1, testGame.getPlayer2()));
        assertEquals(true, testGame.checkCard(2, testGame.getPlayer2()));
        assertEquals(true, testGame.checkCard(3, testGame.getPlayer2()));
        assertEquals(true, testGame.checkCard(4, testGame.getPlayer2()));
    }

    @Test
    public void testDiscardCardPlayer1(){
        testGame.setDeck(resetDeck());
        setPlayerHands();
        DeckManager.resetIndex();

        assertEquals("Nothing", testGame.getPlayer1().getCard(0).getName());
        assertEquals("Normal card", testGame.getPlayer1().getCard(1).getName());
        assertEquals("Expensive Health", testGame.getPlayer1().getCard(2).getName());

        testGame.discardCard(4, testGame.getPlayer1());
        assert(testGame.getPlayer1().getCard(4).equals(testGame.getDeckCardAt(0)));
        assertEquals("Nothing", testGame.getPlayer1().getCard(4).getName());

        testGame.discardCard(3, testGame.getPlayer1());
        assert(testGame.getPlayer1().getCard(3).equals(testGame.getDeckCardAt(1)));
        assertEquals("Normal card", testGame.getPlayer1().getCard(3).getName());

        testGame.discardCard(2, testGame.getPlayer1());
        assert(testGame.getPlayer1().getCard(2).equals(testGame.getDeckCardAt(2)));
        assertEquals("Expensive Health", testGame.getPlayer1().getCard(2).getName());

        testGame.discardCard(4, testGame.getPlayer1());
        testGame.discardCard(4, testGame.getPlayer1());
        testGame.discardCard(4, testGame.getPlayer1());
        testGame.discardCard(4, testGame.getPlayer1());
        assert(testGame.getPlayer1().getCard(2).equals(testGame.getDeckCardAt(3)));
        assertEquals("Normal card", testGame.getPlayer1().getCard(1).getName());
    }

    @Test
    public void testCheckStats() {
        testGame.initStats();
        assertNotNull(testGame.getPlayerName());
        assertEquals("The player name should be Player_1", "Player_1", testGame.getPlayerName());
        assertEquals("The Players wins should be 0", 0, testGame.getWin());
        testGame.addWin();
        assertEquals("The Players wins should be 1", 1, testGame.getWin());
        testGame.addLoss();
        assertEquals("The Players wins should be 1", 1, testGame.getWin());
    }
}