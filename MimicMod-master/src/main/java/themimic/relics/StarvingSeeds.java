package themimic.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import themimic.character.MimicCharacter;

import static themimic.TheMimicMod.makeID;

public class StarvingSeeds extends BaseRelic {
    private static final String NAME = "StarvingSeeds"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:StrangeSap
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public StarvingSeeds() {
        super(ID, NAME, MimicCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public int onPlayerGainedBlock(float blockAmount) {
        if (!this.grayscale) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, (int) blockAmount));
            blockAmount = 0;
            this.grayscale = true;
        }
        return MathUtils.floor(blockAmount);
    }

    public void onVictory() {
        this.grayscale = false;
    }
}
