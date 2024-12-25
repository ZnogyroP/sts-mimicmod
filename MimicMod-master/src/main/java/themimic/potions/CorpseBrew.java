package themimic.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import themimic.TheMimicMod;
import themimic.actions.GainRandomBodyPartAction;
import themimic.character.MimicCharacter;

import static themimic.TheMimicMod.makeID;

public class CorpseBrew extends BasePotion {
    public static final String ID = makeID(CorpseBrew.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(73, 73, 58);
    private static final Color HYBRID_COLOR = null;
    private static final Color SPOTS_COLOR = CardHelper.getColor(96, 107, 14);

    public CorpseBrew() {
        super(ID, 3, PotionRarity.UNCOMMON, PotionSize.HEART, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
        playerClass = MimicCharacter.Meta.MIMIC_CHAR;
    }

    @Override
    public String getDescription() {
        return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
    }

    @Override
    public void addAdditionalTips() {
        this.tips.add(new PowerTip(TheMimicMod.keywords.get("bodyparts").PROPER_NAME, TheMimicMod.keywords.get("bodyparts").DESCRIPTION));
    }

    @Override
    public void use(AbstractCreature target) {
        for (int i = 0; i < potency; ++i) {
            this.addToBot(new GainRandomBodyPartAction());
        }
    }
}
