package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Eviscerate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.Objects;

public class OutreachAction extends AbstractGameAction {

    public OutreachAction(AbstractPlayer p) {
        this.duration = 0.0F;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        ArrayList<AbstractCard> redCards = CardLibrary.getCardList(CardLibrary.LibraryType.RED);
        ArrayList<AbstractCard> greenCards = CardLibrary.getCardList(CardLibrary.LibraryType.GREEN);
        ArrayList<AbstractCard> blueCards = CardLibrary.getCardList(CardLibrary.LibraryType.BLUE);
        ArrayList<AbstractCard> purpleCards = CardLibrary.getCardList(CardLibrary.LibraryType.PURPLE);

        redCards.removeIf(redCard -> redCard.hasTag(AbstractCard.CardTags.HEALING) || redCard.rarity == AbstractCard.CardRarity.BASIC);
        greenCards.removeIf(greenCard -> greenCard.hasTag(AbstractCard.CardTags.HEALING) || greenCard.rarity == AbstractCard.CardRarity.BASIC);
        blueCards.removeIf(blueCard -> blueCard.hasTag(AbstractCard.CardTags.HEALING) || blueCard.rarity == AbstractCard.CardRarity.BASIC);
        purpleCards.removeIf(purpleCard -> purpleCard.hasTag(AbstractCard.CardTags.HEALING) || purpleCard.rarity == AbstractCard.CardRarity.BASIC);

        AbstractCard newRedCard = redCards.get(AbstractDungeon.cardRandomRng.random(redCards.size() - 1)).makeCopy();
        AbstractCard newGreenCard = greenCards.get(AbstractDungeon.cardRandomRng.random(greenCards.size() - 1)).makeCopy();
        AbstractCard newBlueCard = blueCards.get(AbstractDungeon.cardRandomRng.random(blueCards.size() - 1)).makeCopy();
        AbstractCard newPurpleCard = purpleCards.get(AbstractDungeon.cardRandomRng.random(purpleCards.size() - 1)).makeCopy();

        newRedCard.setCostForTurn(0);
        this.addToBot(new MakeTempCardInHandAction(newRedCard, true));
        newGreenCard.setCostForTurn(0);
        this.addToBot(new MakeTempCardInHandAction(newGreenCard, true));
        newBlueCard.setCostForTurn(0);
        this.addToBot(new MakeTempCardInHandAction(newBlueCard, true));
        newPurpleCard.setCostForTurn(0);
        this.addToBot(new MakeTempCardInHandAction(newPurpleCard, true));

        this.isDone = true;
    }
}
