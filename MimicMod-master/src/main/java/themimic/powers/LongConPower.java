package themimic.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static themimic.TheMimicMod.makeID;

public class LongConPower extends BasePower {

    public static final String POWER_ID = makeID("LongCon");
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public LongConPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        this.updateDescription();
    }

    public void onCreateIntent(AbstractMonster owner) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractMonster mo = (AbstractMonster) this.owner;
            if (mo != null) {
                if (mo.getIntentBaseDmg() < 0) {
                    this.flash();
                    addToBot(new DamageAction(mo, new DamageInfo(source, amount, DamageInfo.DamageType.HP_LOSS)));
                    addToTop(new RemoveSpecificPowerAction(mo, source, this));
                }
            }
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
