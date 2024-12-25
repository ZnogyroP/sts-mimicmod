package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class ElaborateDeceptionAction extends AbstractGameAction {

    private int numSkillsDrawn = 0;
    private int amount = 0;
    private int numSkillsNeeded = 0;

    public ElaborateDeceptionAction(int amount, int numSkills) {
        this.duration = 0.0F;
        this.amount = amount;
        this.actionType = ActionType.WAIT;
        this.numSkillsNeeded = numSkills;
    }

    public void update() {
        for(AbstractCard c : DrawCardAction.drawnCards) {
            if (c.type == AbstractCard.CardType.SKILL) {
                numSkillsDrawn++;
            }
        }
        if (numSkillsDrawn >= numSkillsNeeded) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, amount, false)));
            }
        }
        this.isDone = true;
    }
}
