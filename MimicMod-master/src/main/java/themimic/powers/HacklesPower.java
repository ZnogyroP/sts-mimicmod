package themimic.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static themimic.TheMimicMod.makeID;

public class HacklesPower extends BasePower {

    public static final String POWER_ID = makeID("Hackles");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public HacklesPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, amount);
        this.updateDescription();
    }

    public void atStartOfTurnPostDraw() {
        boolean allEnemiesAttacking = true;
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo != null && mo.getIntentBaseDmg() < 0&& !mo.isDead && !mo.isDying) {
                allEnemiesAttacking = false;
            }
        }
        if (allEnemiesAttacking) {
            this.flash();
            this.addToBot(new GainEnergyAction(amount));
        }
    }



    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);

        for(int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }

        sb.append(DESCRIPTIONS[1]);
        this.description = sb.toString();
    }
}
