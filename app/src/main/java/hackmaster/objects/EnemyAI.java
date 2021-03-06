package hackmaster.objects;

public class EnemyAI extends PlayerClass {
    private int nextCard;

    public EnemyAI(int id, String n, ResourceClass r, CardClass[] c) {
        super(id, n, r, c);
    }

    public int playNextCard() {
        int nextCard =  -1;
        CardClass[] playable = playableCards();

        if (playable.length == 0) {
            nextCard = bestCard(getCards());
        }
        else {
            nextCard = bestCard(playable);
            nextCard = getCardIndex(playable[nextCard].getID());
        }

        return nextCard;
    }

    public int bestCard(CardClass[] playable) {
        int bestCard = -1;
        int bestCost = -1;

        for (int i = 0; i < playable.length; i++) {
            ResourceClass cardR = playable[i].getPlayerR();
            int testCost = Math.abs(cardR.getBotnet()) + Math.abs(cardR.gethCoin()) + Math.abs(cardR.getCpu()) + Math.abs(cardR.getHealth());
            if (testCost >= bestCost) {
                bestCost = testCost;
                bestCard = i;
            }
        }

        return bestCard;
    }
}
