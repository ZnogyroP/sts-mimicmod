package themimic.cards.Uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import themimic.cards.BaseCard;
import themimic.cards.Special.Eye;
import themimic.character.MimicCharacter;
import themimic.util.CardStats;

public class TapetumLucidum extends BaseCard {
    public static final String ID = makeID(TapetumLucidum.class.getSimpleName()); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            MimicCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.

    public TapetumLucidum() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.cardsToPreview = new Eye().makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded){
            AbstractCard s = (new Eye()).makeCopy();
            s.upgrade();
            this.addToBot(new MakeTempCardInHandAction(s, 1));
            this.addToBot(new MakeTempCardInDrawPileAction(s, 1, true, true));
        } else {
            this.addToBot(new MakeTempCardInHandAction(new Eye().makeCopy(), 1));
            this.addToBot(new MakeTempCardInDrawPileAction(new Eye().makeCopy(), 1, true, true));
        }
    }

    public AbstractCard makeCopy() {
        return new TapetumLucidum();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

    }

}
