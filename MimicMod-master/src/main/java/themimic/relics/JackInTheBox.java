package themimic.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import themimic.character.MimicCharacter;
import themimic.powers.JackInTheBoxPower;

import static themimic.TheMimicMod.makeID;

public class JackInTheBox extends BaseRelic {
    private static final String NAME = "JackInTheBox"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:StrangeSap
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private static final int STRENGTH = 2; //For convenience of changing it later and clearly knowing what the number means instead of just having a 10 sitting around in the code.

    public JackInTheBox() {
        super(ID, NAME, MimicCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.hasPower(VulnerablePower.POWER_ID)) {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new JackInTheBoxPower(AbstractDungeon.player, STRENGTH)));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STRENGTH + DESCRIPTIONS[1];
    }

}
