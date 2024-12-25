package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class ReformAction extends AbstractGameAction {
    private final boolean upgraded;
    private final AbstractPlayer p;

    public ReformAction(boolean upgraded) {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.upgraded = upgraded;
    }

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        }
        else if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.gridSelectScreen.open(this.generateCardChoices(), 1, CardRewardScreen.TEXT[1], false);
            this.tickDuration();
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    AbstractCard choice = c.makeStatEquivalentCopy();
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(choice, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
            }
            this.tickDuration();
        }
    }

    private CardGroup generateCardChoices() {
        ArrayList<AbstractCard> reformOptions = new ArrayList<>();

        while(reformOptions.size() != 20) {
            boolean dupe = false;
            AbstractCard tmp = AbstractDungeon.returnTrulyRandomCardInCombat();
            for (AbstractCard c : reformOptions) {
                if (c.cardID.equals(tmp.cardID)) {
                    dupe = true;
                    break;
                }
            }
            if (!dupe) {
                reformOptions.add(tmp.makeCopy());
            }
        }
        if (AbstractDungeon.player.hasPower("MasterRealityPower") || this.upgraded) {
            for (AbstractCard c : reformOptions) {
                c.upgrade();
            }
        }
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c : reformOptions) {
            tmp.addToRandomSpot(c);
        }
        return tmp;
    }
}
