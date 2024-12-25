package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class PsychOutAction extends AbstractGameAction {
    private final int magicNumber;
    private final int damage;
    private final AbstractPlayer player;
    private final AbstractMonster targetMonster;

    public PsychOutAction(int damage, int magicNumber, AbstractPlayer p, AbstractMonster m) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.damage = damage;
        this.magicNumber = magicNumber;
        this.player = p;
        this.targetMonster = m;
    }

    public void update() {
        if (this.targetMonster != null && this.targetMonster.getIntentBaseDmg() >= 0) {
            this.addToBot(new DamageAction(targetMonster, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_LIGHT));
        } else {
            this.addToBot(new DamageAction(targetMonster, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), AttackEffect.BLUNT_HEAVY));
            this.addToBot(new ApplyPowerAction(targetMonster, player, new VulnerablePower(targetMonster, magicNumber, false), magicNumber, true, AttackEffect.NONE));
        }

        this.isDone = true;
    }
}
