package themimic.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import themimic.character.MimicCharacter;

import static themimic.TheMimicMod.makeID;

public class Antibiotics extends BaseRelic {
    private static final String NAME = "Antibiotics"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:Antibiotics
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    private static final int ANTIBIOTICS = 2; //For convenience of changing it later and clearly knowing what the number means instead of just having a 10 sitting around in the code.

    public Antibiotics() {
        super(ID, NAME, MimicCharacter.Meta.CARD_COLOR, RARITY, SOUND);
    }

    public void atBattleStart() {
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            this.addToTop(new RelicAboveCreatureAction(m, this));
            m.addPower(new ArtifactPower(m, 2));
        }
        AbstractDungeon.onModifyPower();
    }

    public void onSpawnMonster(AbstractMonster monster) {
        monster.addPower(new ArtifactPower(monster, 2));
        AbstractDungeon.onModifyPower();
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + ANTIBIOTICS + DESCRIPTIONS[1];
    }
}
