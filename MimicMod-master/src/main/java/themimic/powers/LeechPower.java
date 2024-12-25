package themimic.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import themimic.actions.LeechLoseHpAction;

import java.util.ArrayList;

import static themimic.TheMimicMod.makeID;

public class LeechPower extends BasePower implements HealthBarRenderPower {

    public static final String POWER_ID = makeID("Leech");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.
    private final ArrayList<int[]> stacks = new ArrayList<int[]>();
    private int turns;

    public LeechPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, source, amount);
        if (this.amount >= 999) {
            this.amount = 999;
        }
        this.turns = 2;
        if (AbstractDungeon.player.hasPower(BloomPower.POWER_ID)) {
            this.turns += AbstractDungeon.player.getPower(BloomPower.POWER_ID).amount;
        }
    }

    @Override
    public void onInitialApplication() {
        this.stacks.clear();
        this.stacks.add(new int[]{this.amount, this.turns});
    }

    @Override
    public void onStackPower(AbstractPower powerToApply, AbstractCreature target, AbstractCreature source, int amount) {

        boolean doNewStack = true;
        LeechPower existingLeech = (LeechPower) target.getPower(LeechPower.POWER_ID);

        for (int[] stack : existingLeech.stacks) {
            if (stack[1] == this.turns) {
                stack[0] += amount;
                doNewStack = false;
                break;
            }
        }
        if (doNewStack) {
            existingLeech.stacks.add(new int[]{amount, this.turns});
        }
    }

    @Override
    public void onDeath() {
        this.stacks.clear();
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            this.addToBot(new LeechLoseHpAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.POISON));
            for (int i = this.stacks.size() - 1; i >= 0; i--) {
                this.stacks.get(i)[1]--;
                if (this.stacks.get(i)[1] == 0) {
                    if (this.stacks.get(i)[0] >= this.amount) {
                        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.source, this));
                    } else {
                        this.addToBot(new ReducePowerAction(this.owner, this.source, this, this.stacks.get(i)[0]));
                    }
                    this.stacks.remove(i);
                }
            }
            this.updateDescription();
        }
    }

    public void updateDescription() {
        int leechLostThisTurn = 0;
        if (this.stacks != null) {
            for (int[] stack : this.stacks) {
                if (stack[1] == 1) {
                    leechLostThisTurn = stack[0];
                    break;
                }
            }
        }
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + (int) Math.floor((float) amount / 2) + DESCRIPTIONS[2] + leechLostThisTurn + DESCRIPTIONS[3];
    }

    @Override
    public int getHealthBarAmount() {
        return this.amount;
    }

    @Override
    public Color getColor() {
        return new Color(0.73F, 0.82F, 0.0F, 0.8F);
    }
}
