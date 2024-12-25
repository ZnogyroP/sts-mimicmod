package themimic.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static themimic.TheMimicMod.makeID;

public class UnLeechPowerDEPRECATED extends BasePower implements NonStackablePower {

    public static final String POWER_ID = makeID("UnLeech");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    public int turns;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public UnLeechPowerDEPRECATED(AbstractCreature owner, AbstractCreature source, int amount, int turns) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount, turns);
        this.turns = turns;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        if (this.owner.hasPower(LeechPower.POWER_ID)) {
            this.owner.getPower(LeechPower.POWER_ID).updateDescription();
        }
        this.updateDescription();
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.turns = this.turns - 1;
            if (this.turns == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
                if (this.owner.getPower(LeechPower.POWER_ID).amount <= this.amount) {
                    this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.owner.getPower(LeechPower.POWER_ID)));
                } else {
                    this.addToBot(new ReducePowerAction(this.owner, this.owner, this.owner.getPower(LeechPower.POWER_ID), this.amount));
                }
            }
            if (this.owner.hasPower(LeechPower.POWER_ID)) {
                this.owner.getPower(LeechPower.POWER_ID).updateDescription();
            }
            this.updateDescription();
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!this.owner.hasPower(LeechPower.POWER_ID)) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void updateDescription() {
        if (this.turns > 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + this.turns + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + this.turns + DESCRIPTIONS[3];
        }
    }

    @Override
    public boolean isStackable(AbstractPower power) {
        return power instanceof UnLeechPowerDEPRECATED && ((UnLeechPowerDEPRECATED)power).turns == turns;
    }
}
