package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import java.util.UUID;

public class TransformCardAction extends AbstractGameAction {
    AbstractCard oldCard;
    UUID uuid;

    public TransformCardAction(AbstractCard cardToTransform, UUID targetUUID) {
        this.actionType = ActionType.SPECIAL;
        this.oldCard = cardToTransform;
        this.uuid = targetUUID;
    }

    public void update() {
        if (!this.isDone) {
            AbstractCard masterDeckRemove = null;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.uuid.equals(uuid)) {
                    masterDeckRemove = c;
                }
            }
            if (masterDeckRemove != null) {
                AbstractDungeon.player.masterDeck.removeCard(masterDeckRemove);
                AbstractDungeon.transformCard(oldCard, false, AbstractDungeon.miscRng);
                AbstractCard newCard = AbstractDungeon.getTransformedCard().makeCopy();
                AbstractDungeon.effectsQueue.add(new ShowCardAndObtainEffect(newCard, Settings.WIDTH * 2.0f / 3.0f, Settings.HEIGHT / 2.0f));

                replaceCard(oldCard, newCard, AbstractDungeon.player.drawPile);
                replaceCard(oldCard, newCard, AbstractDungeon.player.discardPile);
                replaceCard(oldCard, newCard, AbstractDungeon.player.exhaustPile);
                replaceCard(oldCard, newCard, AbstractDungeon.player.hand);
                replaceCard(oldCard, newCard, AbstractDungeon.player.limbo);

                this.isDone = true;
            }
        }
    }

    public void replaceCard(AbstractCard oldCard, AbstractCard newCard, CardGroup group) {
        for (int i = 0; i < group.group.size(); i++) {
            AbstractCard c = group.group.get(i);
            if (c.uuid.equals(uuid)) {
                group.group.set(i, newCard.makeStatEquivalentCopy());
            }
        }
    }
}
