package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class StealBlockAction extends AbstractGameAction {
    public StealBlockAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.source = source;
        this.actionType = ActionType.BLOCK;
        this.attackEffect = AttackEffect.NONE;
        this.duration = 0.25F;
    }

    public void update() {
        if (!this.target.isDying && !this.target.isDead && this.duration == 0.25F) {
            int blockSteal = this.target.currentBlock;
            this.target.loseBlock(blockSteal);
            this.source.addBlock(blockSteal);
        }

        this.tickDuration();
    }
}
