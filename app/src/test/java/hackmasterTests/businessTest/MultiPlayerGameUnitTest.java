package hackmasterTests.businessTest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import hackmaster.application.Services;
import hackmaster.business.DeckManager;
import hackmaster.business.Game;
import hackmaster.business.MultiplayerGame;
import hackmaster.business.SetupGame;
import hackmaster.objects.CardClass;
import hackmaster.objects.EnemyAI;
import hackmaster.objects.PlayerClass;
import hackmaster.objects.ResourceClass;
import hackmasterTests.persistenceTest.DataAccessStub;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class MultiPlayerGameUnitTest {
    Game game;
    PlayerClass player1;
    PlayerClass player2;
    CardClass[] deck;
    ResourceClass player1Res;
    ResourceClass player2Res;
    CardClass[] player1Hand;
    CardClass[] player2Hand;

    @Before
    public void setup(){
        Services.closeDataAccess();
        DataAccessStub dbStub = new DataAccessStub("stub");
        Services.createDataAccess(dbStub,dbStub,dbStub);

        game = SetupGame.setUpMultiplayerGame();
        ArrayList<CardClass> listDeck = new ArrayList<>();
        dbStub.getCardSequential(listDeck);
        game.setDeck(listDeck.toArray(new CardClass[0]));
        player1 = game.getPlayer1();
        player2 = game.getPlayer2();
        DeckManager.resetIndex();
        player1.setHand(DeckManager.dealFirstHandOfGame());
        player2.setHand(DeckManager.dealFirstHandOfGame());
    }

    @Test
    public void testSetupInstances() {
        assert(game instanceof MultiplayerGame);
        assert(player1 instanceof PlayerClass);
        assert (player2 instanceof EnemyAI);
    }

    @Test
    public void testPlayer1Resources() {
        player1Res = game.getPlayer1Res();

        assertEquals(SetupGame.startOfGameResources().getBotnet(), player1Res.getBotnet());
        assertEquals(SetupGame.startOfGameResources().getBotnetRate(), player1Res.getBotnetRate());

        assertEquals(SetupGame.startOfGameResources().getCpu(), player1Res.getCpu());
        assertEquals(SetupGame.startOfGameResources().getCpuRate(), player1Res.getCpuRate());

        assertEquals(SetupGame.startOfGameResources().gethCoin(), player1Res.gethCoin());
        assertEquals(SetupGame.startOfGameResources().gethCoinRate(), player1Res.gethCoinRate());

        assertEquals(SetupGame.startOfGameResources().getHealth(), player1Res.getHealth());
    }

    @Test
    public void testPlayer2Resources() {
        player2Res = game.getPlayer2Res();

        assertEquals(SetupGame.startOfGameResources().getBotnet(), player2Res.getBotnet());
        assertEquals(SetupGame.startOfGameResources().getBotnetRate(), player2Res.getBotnetRate());

        assertEquals(SetupGame.startOfGameResources().getCpu(), player2Res.getCpu());
        assertEquals(SetupGame.startOfGameResources().getCpuRate(), player2Res.getCpuRate());

        assertEquals(SetupGame.startOfGameResources().gethCoin(), player2Res.gethCoin());
        assertEquals(SetupGame.startOfGameResources().gethCoinRate(), player2Res.gethCoinRate());

        assertEquals(SetupGame.startOfGameResources().getHealth(), player2Res.getHealth());
    }

    @Test
    public void testPlayer1Hand() {
        player1Hand = game.getPlayer1().getCards();
        deck = game.getDeck();

        assertEquals("CPU Boost", player1Hand[0].getName());
        assertEquals(deck[0].getName(), player1Hand[0].getName());

        assertEquals("More Cores", player1Hand[1].getName());
        assertEquals(deck[1].getName(), player1Hand[1].getName());

        assertEquals("bot.net", player1Hand[2].getName());
        assertEquals(deck[2].getName(), player1Hand[2].getName());

        assertEquals("Сut some wires", player1Hand[3].getName());
        assertEquals(deck[3].getName(), player1Hand[3].getName());

        assertEquals("Upgrade Botnet", player1Hand[4].getName());
        assertEquals(deck[4].getName(), player1Hand[4].getName());
    }

    @Test
    public void testPlayer2Hand() {
        player2Hand = game.getPlayer2().getCards();
        deck = game.getDeck();

        assertEquals("Upgrade CPU", player2Hand[0].getName());
        assertEquals(deck[5].getName(), player2Hand[0].getName());

        assertEquals("Upgrade Hash Rate", player2Hand[1].getName());
        assertEquals(deck[6].getName(), player2Hand[1].getName());

        assertEquals("DDOS", player2Hand[2].getName());
        assertEquals(deck[7].getName(), player2Hand[2].getName());

        assertEquals("File Transfer", player2Hand[3].getName());
        assertEquals(deck[8].getName(), player2Hand[3].getName());

        assertEquals("Pop-up", player2Hand[4].getName());
        assertEquals(deck[9].getName(), player2Hand[4].getName());
    }

    @Test
    public void testPlayCardEventReplaceCard()
    {
        CardClass beforePlayed = game.getPlayer1().getCard(4);
        game.playCardEvent(4);
        assertEquals(game.getPlayedCardOne().getName(), beforePlayed.getName());
        assertFalse(beforePlayed.getName().equals(game.getPlayer1().getCard(4).getName()));

        beforePlayed = game.getPlayer2().getCard(1);
        game.playCardEvent(1);
        assertEquals(game.getPlayedCardTwo().getName(), beforePlayed.getName());
        assertFalse(beforePlayed.getName().equals(game.getPlayer1().getCard(1).getName()));

        beforePlayed = game.getPlayer1().getCard(2);
        game.playCardEvent(2);
        assertEquals(game.getPlayedCardOne().getName(), beforePlayed.getName());
        assertFalse(beforePlayed.getName().equals(game.getPlayer1().getCard(2).getName()));

        beforePlayed = game.getPlayer2().getCard(3);
        game.playCardEvent(3);
        assertEquals(game.getPlayedCardTwo().getName(), beforePlayed.getName());
        assertFalse(beforePlayed.getName().equals(game.getPlayer1().getCard(3).getName()));
    }

    @Test
    public void testPlayerTurnAfterPlayedCard() {
        assert(game.getPlayer1Turn());
        game.playCardEvent(4);

        assert(!game.getPlayer1Turn());
        game.playCardEvent(3);

        assert(game.getPlayer1Turn());
        game.playCardEvent(3);

        assert(!game.getPlayer1Turn());
        game.playCardEvent(3);
    }
}
