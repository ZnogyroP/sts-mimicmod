package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class TransfusionAction extends AbstractGameAction {
    private final int damage;
    private final AbstractPlayer player;
    private final AbstractMonster targetMonster;
    private final boolean upgraded;
    private int weakStacks;
    private int vulnStacks;

    public TransfusionAction(int damage, AbstractPlayer p, AbstractMonster m, boolean upgraded) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.damage = damage;
        this.player = p;
        this.targetMonster = m;
        this.upgraded = upgraded;
        this.weakStacks = 0;
        this.vulnStacks = 0;
    }

    public void update() {
        if (player.hasPower(WeakPower.POWER_ID)) {
            this.weakStacks = player.getPower(WeakPower.POWER_ID).amount;
            this.addToBot(new RemoveSpecificPowerAction(player, player, WeakPower.POWER_ID));
        }
        if (this.upgraded && player.hasPower(VulnerablePower.POWER_ID)) {
            this.vulnStacks = player.getPower(VulnerablePower.POWER_ID).amount;
            this.addToBot(new RemoveSpecificPowerAction(player, player, VulnerablePower.POWER_ID));
        }
        this.addToBot(new DamageAction(targetMonster, new DamageInfo(player, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_VERTICAL));
        if (this.weakStacks > 0) {
            this.addToBot(new ApplyPowerAction(targetMonster, player, new WeakPower(targetMonster, this.weakStacks, false)));
        }
        if (this.vulnStacks > 0) {
            this.addToBot(new ApplyPowerAction(targetMonster, player, new VulnerablePower(targetMonster, this.vulnStacks, false)));
        }

        this.isDone = true;
    }
}
