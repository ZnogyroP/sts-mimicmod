package themimic.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import themimic.relics.JackInTheBox;

import static themimic.TheMimicMod.makeID;

public class JackInTheBoxPower extends BasePower implements InvisiblePower {

    public static final String POWER_ID = makeID("JackInTheBox");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public JackInTheBoxPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, amount);
        this.updateDescription();
    }

    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (info.owner != null && damageAmount > 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.player.hasRelic(JackInTheBox.ID)) {
            AbstractDungeon.player.getRelic(JackInTheBox.ID).flash();
        }
        this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, amount)));
        if (AbstractDungeon.player.hasRelic(JackInTheBox.ID)) {
            this.addToTop(new RelicAboveCreatureAction(this.owner, AbstractDungeon.player.getRelic(JackInTheBox.ID)));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
