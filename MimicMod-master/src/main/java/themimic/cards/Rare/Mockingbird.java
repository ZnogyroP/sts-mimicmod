package themimic.cards.Rare;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import themimic.cards.BaseCard;
import themimic.character.MimicCharacter;
import themimic.tags.CustomTags;
import themimic.util.CardStats;

public class Mockingbird extends BaseCard {
    public static final String ID = makeID(Mockingbird.class.getSimpleName()); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            MimicCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OpeningAction");
    public static final String[] TEXT = uiStrings.TEXT;
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.


    public Mockingbird() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setCostUpgrade(0); //Sets the card's damage and how much it changes when upgraded.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null && m.getIntentBaseDmg() >= 0) {
            this.baseDamage = m.getIntentBaseDmg();
            this.calculateCardDamage(m);
            int timesToHit = ReflectionHacks.<Integer>getPrivate(m, AbstractMonster.class, "intentMultiAmt");
            if (timesToHit > 1) {
                for (int i = 0; i < timesToHit; ++i) {
                    this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
                }
            } else {
                this.addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
            }
        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        if (m != null && m.getIntentBaseDmg() >= 0) {
            this.baseDamage = m.getIntentBaseDmg();
            super.calculateCardDamage(m);
            this.rawDescription = cardStrings.DESCRIPTION;
            int timesToHit = ReflectionHacks.<Integer>getPrivate(m, AbstractMonster.class, "intentMultiAmt");
            if (timesToHit > 1) {
                this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1] + timesToHit + cardStrings.EXTENDED_DESCRIPTION[2];
            } else {
                this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
            }
        }
        else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        this.initializeDescription();
    }
}
