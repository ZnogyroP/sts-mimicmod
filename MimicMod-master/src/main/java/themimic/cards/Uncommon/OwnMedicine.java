package themimic.cards.Uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import themimic.cards.BaseCard;
import themimic.character.MimicCharacter;
import themimic.powers.BloomPower;
import themimic.powers.LeechPower;
import themimic.powers.UnLeechPowerDEPRECATED;
import themimic.util.CardStats;

public class OwnMedicine extends BaseCard {
    public static final String ID = makeID(OwnMedicine.class.getSimpleName()); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            MimicCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int MAGIC = 7;
    private static final int UPG_MAGIC = 3;
    private static int turns = 2;


    public OwnMedicine() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.intent == AbstractMonster.Intent.ATTACK_DEBUFF || m.intent == AbstractMonster.Intent.DEBUFF || m.intent == AbstractMonster.Intent.STRONG_DEBUFF || m.intent == AbstractMonster.Intent.DEFEND_DEBUFF) {
            this.addToBot(new ApplyPowerAction(m, p, new LeechPower(m, p, (magicNumber * 2)), (magicNumber * 2), false));
        } else {
            this.addToBot(new ApplyPowerAction(m, p, new LeechPower(m, p, magicNumber), magicNumber, false));
        }
    }

}
