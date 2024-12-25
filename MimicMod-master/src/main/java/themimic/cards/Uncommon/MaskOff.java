package themimic.cards.Uncommon;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import themimic.cards.BaseCard;
import themimic.character.MimicCharacter;
import themimic.powers.NutrientsPowerDEPRECATED;
import themimic.util.CardStats;

public class MaskOff extends BaseCard {
    public static final String ID = makeID(MaskOff.class.getSimpleName()); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            MimicCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int MAGIC = 1;

    public MaskOff() {
        super(ID, info); //Pass the required information to the BaseCard constructor.\
        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded || p.currentHealth <= (p.maxHealth / 2)) {
            for(AbstractPower pow : p.powers) {
                if (pow.type == AbstractPower.PowerType.DEBUFF) {
                    addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
                    addToTop(new RemoveSpecificPowerAction(p, p, pow.ID));
                }
            }
            addToTop(new VFXAction(p, new InflameEffect(p), 1.0F));
        }
    }
}
