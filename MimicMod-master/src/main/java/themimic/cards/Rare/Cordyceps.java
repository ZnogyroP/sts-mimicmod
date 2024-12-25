package themimic.cards.Rare;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import themimic.cards.BaseCard;
import themimic.character.MimicCharacter;
import themimic.powers.LeechPower;
import themimic.util.CardStats;

public class Cordyceps extends BaseCard {
    public static final String ID = makeID(Cordyceps.class.getSimpleName()); //makeID adds the mod ID, so the final ID will be something like "modID:MyCard"
    private static final CardStats info = new CardStats(
            MimicCharacter.Meta.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );
    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int MAGIC = 2;
    public static String[] TEXT = new String[0];

    static {
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("OpeningAction");
        TEXT = uiStrings.TEXT;
    }

    public Cordyceps() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.hasPower(LeechPower.POWER_ID) && m.getIntentBaseDmg() >= 0) {
            addToBot(new StunMonsterAction(m, p, 1));
            if (this.upgraded) {
                this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        } else if (!m.hasPower(LeechPower.POWER_ID) && m.getIntentBaseDmg() >= 0) {
            TEXT = new String[]{"That enemy does not have Leech!"};
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        } else {
            TEXT = new String[]{"That enemy does not intend to attack!"};
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        }
    }
}
