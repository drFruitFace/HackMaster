package hackmaster.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hackmaster.objects.CardClass;

public interface CardDataAccessInterface extends DBComponentInterface{
    String getCardSequential(List<CardClass> cardResult);
    String getRandomDeck(List<CardClass> cardResult, Random random);
    ArrayList<CardClass> getCardRandom(int cardID);
    String insertCard(CardClass card);
    String updateCard(CardClass card);
    String removeCard(int cardID);
}
