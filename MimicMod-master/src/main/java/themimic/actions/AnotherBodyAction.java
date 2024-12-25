package themimic.actions;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class AnotherBodyAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cannotTransform = new ArrayList<>();
    private final boolean upgraded;
    private final AbstractCard thisCard;
    private final UUID thisUUID;

    public AnotherBodyAction(boolean upgraded, AbstractCard thisCard, UUID thisUUID) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.upgraded = upgraded;
        this.thisCard = thisCard;
        this.thisUUID = thisUUID;
        AbstractCard d = null;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup group = getCardGroup();
            if (group.isEmpty()) {
                isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(group, 1, "transform", false, true, false, true);
            tickDuration();
            return;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() == 1) {
            for (int i = 0; i <AbstractDungeon.gridSelectScreen.selectedCards.size(); ++i) {
                AbstractDungeon.gridSelectScreen.selectedCards.get(i).unhover();
                addToBot(new TransformCardAction(AbstractDungeon.gridSelectScreen.selectedCards.get(i),AbstractDungeon.gridSelectScreen.selectedCards.get(i).uuid));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            if (!upgraded) {
                addToBot(new TransformCardAction(thisCard, thisUUID));
            }
        }
        isDone = true;
        this.tickDuration();
    }

    private static CardGroup getCardGroup() {
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!Objects.equals(c.cardID, AscendersBane.ID) && !Objects.equals(c.cardID, Necronomicurse.ID) && !Objects.equals(c.cardID, CurseOfTheBell.ID)) {
                AbstractCard masterDeckCard = StSLib.getMasterDeckEquivalent(c);
                if (masterDeckCard != null) {
                    group.addToTop(masterDeckCard);
                }
            }
        }
        return group;
    }
}
