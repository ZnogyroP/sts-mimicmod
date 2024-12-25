package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class OrganTheftAction extends AbstractGameAction {
    private final int energyGainAmt;
    private final int cardDrawAmt;
    private final DamageInfo info;

    public OrganTheftAction(AbstractPlayer player, AbstractCreature target, DamageInfo info, int energyAmt, int cardDrawAmt) {
        this.info = info;
        this.setValues(target, player);
        this.energyGainAmt = energyAmt;
        this.cardDrawAmt = cardDrawAmt;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER && this.target != null) {
            this.target.damage(this.info);
            if (this.target.isDying || this.target.currentHealth <= 0) {
                this.addToBot(new GainEnergyAction(this.energyGainAmt));
                this.addToBot(new DrawCardAction(player, this.cardDrawAmt));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
