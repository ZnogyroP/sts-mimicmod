package themimic.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static themimic.TheMimicMod.makeID;

public class NutrientsPowerDEPRECATED extends BasePower {

    public static final String POWER_ID = makeID("Nutrients");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static boolean playedPower = false;
    private static boolean playedAttack = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public NutrientsPowerDEPRECATED(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, amount);
        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        playedAttack = false;
        playedPower = false;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && !playedAttack) {
            if (playedPower) {
                this.addToBot(new RemoveSpecificPowerAction(owner, owner, this));
                this.addToBot(new RemoveDebuffsAction(AbstractDungeon.player));
                playedAttack = false;
                playedPower = false;
            } else {
                playedAttack = true;
                this.updateDescription();
            }
        } else if (card.type == AbstractCard.CardType.POWER && !playedPower) {
            if (playedAttack) {
                this.addToBot(new RemoveSpecificPowerAction(owner, owner, this));
                this.addToBot(new RemoveDebuffsAction(AbstractDungeon.player));
                playedAttack = false;
                playedPower = false;
            } else {
                playedPower = true;
                this.updateDescription();
            }
        }
    }

    public void updateDescription() {
        if (!playedAttack && !playedPower) {
            this.description = DESCRIPTIONS[0];
        } else if (!playedAttack) {
            this.description = DESCRIPTIONS[1];
        } else if (!playedPower) {
            this.description = DESCRIPTIONS[2];
        }
    }
}
