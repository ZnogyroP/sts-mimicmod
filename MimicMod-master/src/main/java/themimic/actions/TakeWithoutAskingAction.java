package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import java.util.UUID;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class TakeWithoutAskingAction extends AbstractGameAction {
    private final DamageInfo info;
    private final boolean upgraded;
    private final AbstractCard thisCard;
    private final UUID thisUUID;

    public TakeWithoutAskingAction(AbstractPlayer player, AbstractCreature target, DamageInfo info, boolean upgraded, AbstractCard thisCard, UUID targetUUID) {
        this.info = info;
        this.upgraded = upgraded;
        this.thisCard = thisCard;
        this.thisUUID = targetUUID;
        this.setValues(target, player);
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER && this.target != null) {
            this.target.damage(this.info);
            if (((this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        AbstractRelic.RelicTier tier = returnRandomRelicTier();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(tier);
                        if (upgraded) {
                            AbstractRelic.RelicTier tier2 = returnRandomRelicTier();
                            AbstractDungeon.getCurrRoom().addRelicToRewards(tier2);
                        }
                    }
                });
                for (int i = 0; i < 20; ++i) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(player, this.target.hb.cX, this.target.hb.cY, this.source.hb.cX, this.source.hb.cY, false));
                }
                addToBot(new TransformCardAction(thisCard, thisUUID));
            }
        }
        this.tickDuration();
    }

    private AbstractRelic.RelicTier returnRandomRelicTier() {
        int roll = AbstractDungeon.relicRng.random(0, 99);
        if (roll < 50)
            return AbstractRelic.RelicTier.COMMON;
        if (roll > 85)
            return AbstractRelic.RelicTier.RARE;
        return AbstractRelic.RelicTier.UNCOMMON;
    }
}
