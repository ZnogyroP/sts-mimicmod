package themimic.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import themimic.relics.ViscousOoze;

import static themimic.TheMimicMod.makeID;

public class ViscousOozePower extends BasePower implements InvisiblePower {

    public static final String POWER_ID = makeID("ViscousOoze");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public ViscousOozePower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        if (this.amount >= 99) {
            this.amount = 99;
        }
        this.updateDescription();
    }

    public void atStartOfTurnPostDrawEnemyPowers(AbstractMonster owner) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractMonster mo = (AbstractMonster) this.owner;
            if (mo != null && mo.getIntentBaseDmg() >= 0) {
                for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                    if (AbstractDungeon.player.relics.get(i).relicId.equals(ViscousOoze.ID)) {
                        addToBot(new RelicAboveCreatureAction(mo, AbstractDungeon.player.relics.get(i)));
                        break;
                    }
                }
                addToBot(new RemoveSpecificPowerAction(mo, source, this));
                addToBot(new ApplyPowerAction(mo, source, new StrengthPower(mo, -amount)));
            }
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
