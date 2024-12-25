package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ActNaturalAction extends AbstractGameAction {

    private final int blockGain;

    public ActNaturalAction(int amount) {
        this.blockGain = amount;
        this.duration = 0.0F;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        for(AbstractCard c : DrawCardAction.drawnCards) {
            if (c.rarity == AbstractCard.CardRarity.BASIC || c.rarity == AbstractCard.CardRarity.COMMON) {
                AbstractDungeon.actionManager.addToTop(new GainBlockAction(AbstractDungeon.player, blockGain));
            }
        }
        this.isDone = true;
    }
}
