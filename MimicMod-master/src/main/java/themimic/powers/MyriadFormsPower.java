package themimic.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static themimic.TheMimicMod.makeID;

public class MyriadFormsPower extends BasePower {

    public static final String POWER_ID = makeID("MyriadForms");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static int cardsToPlay = 2;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public MyriadFormsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, owner, amount);
        this.updateDescription();
    }

    public void atStartOfTurn() {
        cardsToPlay = 2;
        this.updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (cardsToPlay == 1) {
            ++cardsToPlay;
            addToTop(new DrawCardAction(owner, this.amount));
            this.updateDescription();
        } else {
            --cardsToPlay;
            this.updateDescription();
        }
    }

    public void updateDescription() {
        if (this.amount > 1) {
            if (cardsToPlay == 1) {
                this.description = DESCRIPTIONS[0] + cardsToPlay + DESCRIPTIONS[3] + amount + DESCRIPTIONS[5];
            } else {
                this.description = DESCRIPTIONS[0] + cardsToPlay + DESCRIPTIONS[4] + amount + DESCRIPTIONS[5];
            }
        } else {
            if (cardsToPlay == 1) {
                this.description = DESCRIPTIONS[0] + cardsToPlay + DESCRIPTIONS[1];
            } else {
                this.description = DESCRIPTIONS[0] + cardsToPlay + DESCRIPTIONS[2];
            }
        }
    }
}
