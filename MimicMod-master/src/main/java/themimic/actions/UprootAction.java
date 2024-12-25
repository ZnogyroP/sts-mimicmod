package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import themimic.powers.LeechPower;
import themimic.powers.UnLeechPowerDEPRECATED;

import java.util.Objects;

public class UprootAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final AbstractMonster m;
    private final int magicNumber;

    public UprootAction(AbstractPlayer p, AbstractMonster m, int magicNumber) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.magicNumber = magicNumber;
        this.p = p;
        this.m = m;
    }

    public void update() {
        if (m.hasPower(LeechPower.POWER_ID)) {
            int totalLeech = m.getPower(LeechPower.POWER_ID).amount;
            if (magicNumber > 0 && totalLeech > 0) {
                for (int i = 0; i < magicNumber; ++i) {
                    this.addToBot(new LeechLoseHpAction(m, p, totalLeech, AbstractGameAction.AttackEffect.POISON));
                }
            }
            this.addToBot(new RemoveSpecificPowerAction(m, m, m.getPower(LeechPower.POWER_ID)));
        }
        this.isDone = true;
    }
}
