package themimic.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import themimic.character.MimicCharacter;
import themimic.powers.JackInTheBoxPower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static themimic.TheMimicMod.makeID;

public class FacePaint extends BaseRelic {
    private static final String NAME = "FacePaint"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:StrangeSap
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public FacePaint() {
        super(ID, NAME, MimicCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        if (AbstractDungeon.player.chosenClass != AbstractPlayer.PlayerClass.DEFECT && AbstractDungeon.player.masterMaxOrbs == 0)
            AbstractDungeon.player.masterMaxOrbs = 1;
    }

    public AbstractCard getRewardCard(AbstractCard.CardRarity rarity) {
        ArrayList<AbstractCard> tmpPool = new ArrayList<>();
        CardLibrary.addRedCards(tmpPool);
        CardLibrary.addGreenCards(tmpPool);
        CardLibrary.addBlueCards(tmpPool);
        CardLibrary.addPurpleCards(tmpPool);

        Map<AbstractCard.CardRarity, CardGroup> chosenPools = new HashMap<>();
        for (AbstractCard c : tmpPool) {
            if (!chosenPools.containsKey(c.rarity)) {
                chosenPools.put(c.rarity, new CardGroup(CardGroup.CardGroupType.CARD_POOL));
            }
            chosenPools.get(c.rarity).addToTop(c);
        }
        if (chosenPools.containsKey(rarity)) {
            return chosenPools.get(rarity).getRandomCard(true);
        } else {
            return null;
        }
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }
}
