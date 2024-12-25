package themimic.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import themimic.TheMimicMod;
import themimic.character.MimicCharacter;
import themimic.powers.BloomPower;
import themimic.powers.LeechPower;
import themimic.powers.UnLeechPowerDEPRECATED;

import static themimic.TheMimicMod.makeID;

public class ParasitePotion extends BasePotion {
    public static final String ID = makeID(ParasitePotion.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(114, 202, 6);
    private static final Color HYBRID_COLOR = CardHelper.getColor(98, 132, 77);
    private static final Color SPOTS_COLOR = null;

    public ParasitePotion() {
        super(ID, 8, PotionRarity.COMMON, PotionSize.SPHERE, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        playerClass = MimicCharacter.Meta.MIMIC_CHAR;
        isThrown = true;
        targetRequired = true;
    }

    @Override
    public String getDescription() {
        return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
    }

    @Override
    public void addAdditionalTips() {
        this.tips.add(new PowerTip(TheMimicMod.keywords.get("leech").PROPER_NAME, TheMimicMod.keywords.get("leech").DESCRIPTION));
    }

    @Override
    public void use(AbstractCreature target) {
        this.addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new LeechPower(target, AbstractDungeon.player, this.potency), this.potency));
    }
}
