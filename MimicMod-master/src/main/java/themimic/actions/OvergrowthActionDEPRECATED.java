package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import themimic.powers.BloomPower;
import themimic.powers.LeechPower;
import themimic.powers.UnLeechPowerDEPRECATED;

public class OvergrowthActionDEPRECATED extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final boolean upgraded;
    private final int magic;
    private final AbstractPlayer p;
    private final AbstractMonster m;
    private final int energyOnUse;
    private int turns;
    private int artStacks;

    public OvergrowthActionDEPRECATED(AbstractPlayer p, AbstractMonster m, int magic, boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.magic = magic;
        this.upgraded = upgraded;
        this.energyOnUse = energyOnUse;
        this.freeToPlayOnce = freeToPlayOnce;
        this.p = p;
        this.m = m;
        this.turns = 2;
        this.artStacks = 0;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.upgraded) {
            ++effect;
        }

        if (effect > 0) {
            if (p.hasPower(BloomPower.POWER_ID)) {
                turns += p.getPower(BloomPower.POWER_ID).amount;
            }
            if (m.hasPower(ArtifactPower.POWER_ID)) {
                artStacks += m.getPower(ArtifactPower.POWER_ID).amount;
            }
            for(int i = 0; i < effect; ++i) {
                this.addToTop(new ApplyPowerAction(m, p, new LeechPower(m, p, magic), magic, false));
                if (i >= artStacks) {
                    this.addToBot(new ApplyPowerAction(m, p, new UnLeechPowerDEPRECATED(m, p, magic, turns), magic, false));
                }
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
