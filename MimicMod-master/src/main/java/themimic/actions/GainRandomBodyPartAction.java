package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import themimic.cards.Special.Eye;
import themimic.cards.Special.Heart;
import themimic.cards.Special.Teeth;
import themimic.cards.Special.Ribs;

import java.util.ArrayList;

public class GainRandomBodyPartAction extends AbstractGameAction {
    private AbstractMonster targetMonster;

    public GainRandomBodyPartAction() {
        this.duration = 0.0F;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        list.add(new Teeth());
        list.add(new Eye());
        list.add(new Heart());
        list.add(new Ribs());
        AbstractCard newCard = list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1)).makeCopy();
        this.addToBot(new MakeTempCardInHandAction(newCard, 1, false));
        this.isDone = true;
    }
}
