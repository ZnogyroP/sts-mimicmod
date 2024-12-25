package themimic.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import themimic.character.MimicCharacter;
import themimic.powers.LeechPower;
import themimic.powers.SpeedUpPower;

import static themimic.TheMimicMod.makeID;

public class BogBody extends BaseRelic implements BetterOnLoseHpRelic {
    private static final String NAME = "BogBody"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:StrangeSap
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.
    private static boolean activated;

    public BogBody() {
        super(ID, NAME, MimicCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        activated = false;
    }

    public void atTurnStart() {
        activated = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public int betterOnLoseHp(DamageInfo damageInfo, int i) {
        {
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !activated && damageInfo.owner != null && damageInfo.owner != AbstractDungeon.player) {
                this.flash();
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDead && !m.isDying && !m.hasPower(SlowPower.POWER_ID)) {
                        if (!m.hasPower(ArtifactPower.POWER_ID)) {
                            this.addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new SpeedUpPower(m, -1), -1));
                        }
                        this.addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new SlowPower(m, 0), 0));
                    }
                }
                activated = true;
            }
            return i;
        }
    }
}
