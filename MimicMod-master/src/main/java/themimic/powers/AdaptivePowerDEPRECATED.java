package themimic.powers;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static themimic.TheMimicMod.makeID;

public class AdaptivePowerDEPRECATED extends BasePower {

    public static final String POWER_ID = makeID("Adaptive");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public AdaptivePowerDEPRECATED(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, amount);
        this.updateDescription();
    }

    @Override
    public void beforeMonsterAttacks(AbstractMonster m) {
        if (ReflectionHacks.<Integer>getPrivate(m, AbstractMonster.class, "intentMultiAmt") > 1 && m.getIntentBaseDmg() >= 0){
            int firstHitBlock = m.getIntentDmg();
            addToTop(new GainBlockAction(AbstractDungeon.player, firstHitBlock));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
