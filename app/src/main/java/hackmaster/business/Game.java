package hackmaster.business;

import hackmaster.objects.CardClass;
import hackmaster.objects.EnemyAI;
import hackmaster.objects.PlayerClass;
import hackmaster.objects.PlayerStatsSaves;
import hackmaster.objects.ResourceClass;
import hackmaster.presentation.DrawToScreen;

public abstract class Game {
    public static final int hand = 5;

    private PlayerStatsSaves pStats = null;
    private PlayerClass player1;
    private PlayerClass player2;

    private boolean player1Turn = true;
    private boolean paused = false;
    private boolean inGame = false;
    private boolean singlePlayer = false;
    private boolean delayAi = false;

    private static CardClass playedCard = null;
    private static CardClass playedCardAi = null;

    public Game(PlayerClass p1, PlayerClass p2) {
        player1 = p1;
        player2 = p2;
    }

    public void playCardEvent(int playerCard) {
        System.out.print("Error in game. Please try starting a new game.");
    }

    public boolean cantPlayCard(PlayerClass player) {
        for (int i = 0; i < player.cardsSize(); i++)
            if (checkCard(i, player))
                return false;
        return true;
    }

    private void playerTurn(int playerCard, PlayerClass player) {
        CardClass nextCard = DeckManager.dealNextCard();
        CardClass playedCard = player.getCard(playerCard);
        ResourceManager.applyCard(player1Turn, player1, player2, playedCard);
        player.setCard(playerCard, nextCard);
    }

    public void discardCard(int playerCard, PlayerClass player) {
        CardClass nextCard = DeckManager.dealNextCard();
        player.setCard(playerCard, nextCard);
    }

    public boolean checkCard(int playerCard, PlayerClass player) {
        boolean canPlay = true;
        CardClass card = player.getCard(playerCard);

        ResourceClass cardResource = card.getPlayerR();
        ResourceClass playerResource = player.getResources();

        if(playerResource.getHealth() + cardResource.getHealth() < 0)
            canPlay = false;
        if(playerResource.gethCoin() + cardResource.gethCoin() < 0)
            canPlay = false;
        if(playerResource.getBotnet() + cardResource.getBotnet() < 0)
            canPlay = false;
        if(playerResource.getCpu() + cardResource.getCpu() < 0)
            canPlay = false;

        if(playerResource.gethCoin() + cardResource.gethCoinRate() < 1)
            canPlay = false;
        if(playerResource.getBotnet() + cardResource.getBotnetRate() < 1)
            canPlay = false;
        if(playerResource.getCpu() + cardResource.getCpuRate() < 1)
            canPlay = false;

        return canPlay;
    }

    public int getPlayerNum() {
        if(player1Turn)
            return 0;
        else
            return 1;
    }

    public int getPlayer1Health() {
        if (inGame)
            return player1.getHealth();
        return -1;
    }
    public int getPlayer2Health() {
        if (inGame)
            return player2.getHealth();
        return -1;
    }

    //test this (marc)
    public boolean gameDone() {
        boolean result = false;
        if (player2.getHealth() < 1) {
            result = true;
        }
        if (player1.getHealth() < 1) {
            result = true;
        }
        return result;
    }

    public void initStats() {
        if(pStats == null)
            pStats = new PlayerStatsSaves();

        pStats.setPlayerName("Player_1");
    }


    public String getPlayerName() {
        return pStats.getName();
    }
    public int getWin() {
        return pStats.getWin();
    }
    public void addWin() { pStats.addWin();}
    public void addLoss() { pStats.addLoss();}

    public void setDelayAi(boolean b) { delayAi = b; }
    public boolean getDelayAi() { return delayAi; }
    public CardClass getPlayedCard() { return playedCard; }
    public CardClass getPlayedCardAi() { return playedCardAi; }
    public void setInGame(boolean value) { inGame = value; }
    public void pauseGame() { paused = true; }
    public void unpauseGame() { paused = false; }
    public boolean gamePaused() { return paused; }
    public void setSinglePlayer(boolean set) { singlePlayer = set; }
    public boolean inGame() { return inGame; }
    public PlayerClass getPlayer1(){ return player1; }
    public PlayerClass getPlayer2(){ return player2; }
    public boolean getPlayer1Turn() { return player1Turn; }
    public void setPlayer1Turn(boolean turn) { player1Turn = turn; }
    public void setDeck(CardClass[] set) { DeckManager.setDeck(set); }
    public CardClass getDeckCardAt(int i) { return DeckManager.getCardAt(i); }
    public int getDeckMangerDealNextCard() { return DeckManager.getNextIndex(); }
}
