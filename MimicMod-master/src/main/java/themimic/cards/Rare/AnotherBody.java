package themimic.cards.Rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import themimic.actions.AnotherBodyAction;
import themimic.cards.BaseCard;
import themimic.character.MimicCharacter;
import themimic.util.CardStats;

public class AnotherBody extends BaseCard {
    public static final String ID = makeID(AnotherBody.class.getSimpleName()); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            MimicCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.

    public AnotherBody() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setExhaust(false, true);
        this.tags.add(CardTags.HEALING); // not a healing card, just here so card generation effects don't generate it
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AnotherBodyAction(this.upgraded, this, this.uuid));
    }

}
