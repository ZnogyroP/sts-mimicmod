package themimic.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnAnyPowerAppliedRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import themimic.character.MimicCharacter;
import themimic.powers.StrangeSapPower;
import themimic.powers.UnLeechPowerDEPRECATED;
import themimic.powers.ViscousOozePower;

import java.util.Objects;

import static themimic.TheMimicMod.makeID;

public class LookingGlass extends BaseRelic implements OnAnyPowerAppliedRelic {
    private static final String NAME = "LookingGlass"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:StrangeSap
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public LookingGlass() {
        super(ID, NAME, MimicCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onAnyPowerApply(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        if (target != null && target != AbstractDungeon.player && abstractPower.type == AbstractPower.PowerType.BUFF && abstractPower.amount > 0 && !Objects.equals(abstractPower.ID, StrangeSapPower.POWER_ID) && !Objects.equals(abstractPower.ID, ViscousOozePower.POWER_ID) && !Objects.equals(abstractPower.ID, UnLeechPowerDEPRECATED.POWER_ID)) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
        }
        return true;
    }
}
