package themimic.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import themimic.character.MimicCharacter;
import themimic.powers.StrangeSapPower;
import themimic.powers.ViscousOozePower;

import static themimic.TheMimicMod.makeID;

public class StrangeSap extends BaseRelic {
    private static final String NAME = "StrangeSap"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:StrangeSap
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.
    private boolean firstTurn;
    private int turnOneMonsters;

    private static final int WEAK = 1; //For convenience of changing it later and clearly knowing what the number means instead of just having a 10 sitting around in the code.

    public StrangeSap() {
        super(ID, NAME, MimicCharacter.Meta.CARD_COLOR, RARITY, SOUND);
        this.firstTurn = true;
        this.turnOneMonsters = 0;
    }

    public void atPreBattle() {
        this.firstTurn = true;
        this.turnOneMonsters = 0;
    }

    @Override
    public void atBattleStart()
    {
        this.flash();
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo != null) {
                this.turnOneMonsters++;
            }
        }
    }

    @Override
    public void onCreateIntent(AbstractMonster ___instance) {
        if (this.firstTurn) {
            if (___instance != null && ___instance.getIntentBaseDmg() >= 0) {
                this.addToBot(new RelicAboveCreatureAction(___instance, this));
                this.addToBot(new ApplyPowerAction(___instance, AbstractDungeon.player, new WeakPower(___instance, WEAK, false), WEAK, true));
            } else if (___instance != null && ___instance.getIntentBaseDmg() < 0) {
                this.addToBot(new ApplyPowerAction(___instance, AbstractDungeon.player, new StrangeSapPower(___instance, AbstractDungeon.player, WEAK), WEAK, true));
            }
            this.turnOneMonsters--;
            if (this.turnOneMonsters <= 0) {
                this.firstTurn = false;
            }
        }
    }

    @Override
    public void onSpawnMonster(AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new StrangeSapPower(m, AbstractDungeon.player, WEAK), WEAK, true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + WEAK + DESCRIPTIONS[1];
    }

}
