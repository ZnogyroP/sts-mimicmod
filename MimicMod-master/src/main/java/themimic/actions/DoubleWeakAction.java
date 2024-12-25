package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.WeakPower;

public class DoubleWeakAction extends AbstractGameAction {
    public DoubleWeakAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.source = source;
        this.actionType = ActionType.DEBUFF;
        this.attackEffect = AttackEffect.NONE;
    }

    public void update() {
        if (this.target != null && this.target.hasPower(WeakPower.POWER_ID)) {
            this.addToTop(new ApplyPowerAction(this.target, this.source, new WeakPower(this.target, this.target.getPower(WeakPower.POWER_ID).amount, false), this.target.getPower(WeakPower.POWER_ID).amount));
        }
        this.isDone = true;
    }
}
