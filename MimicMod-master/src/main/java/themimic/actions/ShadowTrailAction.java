package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ShadowTrailAction extends AbstractGameAction {
    private final DamageInfo info;
    private final AbstractMonster targetMonster;

    public ShadowTrailAction(AbstractMonster m, DamageInfo info) {
        this.duration = 0.0F;
        this.setValues(m, info);
        this.actionType = ActionType.WAIT;
        this.info = info;
        this.targetMonster = m;
    }

    public void update() {
        if (this.targetMonster == null) {
            this.isDone = true;
        } else {
            if (this.targetMonster.getIntentBaseDmg() >= 0) {
                if (this.duration == 0.01F && this.targetMonster.currentHealth > 0) {
                    if (this.info.type != DamageInfo.DamageType.THORNS && this.info.owner.isDying) {
                        this.isDone = true;
                        return;
                    }

                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.targetMonster.hb.cX, this.targetMonster.hb.cY, AttackEffect.BLUNT_HEAVY));
                }

                this.tickDuration();
                if (this.isDone && this.targetMonster.currentHealth > 0) {
                    this.target.damage(this.info);
                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.clearPostCombatActions();
                    }

                    this.addToTop(new WaitAction(0.1F));
                }
            } else {
                this.isDone = true;
            }

        }
    }
}
