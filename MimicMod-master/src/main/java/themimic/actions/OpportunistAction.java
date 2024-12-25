package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OpportunistAction extends AbstractGameAction {
    private final int damage;
    private final int block;
    private final AbstractPlayer player;
    private final AbstractMonster targetMonster;

    public OpportunistAction(int damage, int block, AbstractPlayer p, AbstractMonster m) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.damage = damage;
        this.block = block;
        this.player = p;
        this.targetMonster = m;
    }

    public void update() {
        if (this.targetMonster != null && this.targetMonster.getIntentBaseDmg() >= 0) {
            this.addToBot(new GainBlockAction(player, player, block));
        } else {
            this.addToBot(new DamageAction(targetMonster, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_DIAGONAL));
        }

        this.isDone = true;
    }
}
