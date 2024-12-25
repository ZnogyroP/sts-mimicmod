package themimic.patches;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import javassist.CtBehavior;
import themimic.potions.OnUsePotionPotion;

public class OnUsePotionPotionPatch {
    public OnUsePotionPotionPatch() {
    }

    private static void Do(AbstractPotion potion) {
        for(AbstractPotion pot : AbstractDungeon.player.potions) {
            if (pot instanceof OnUsePotionPotion) {
                ((OnUsePotionPotion)pot).onUsePotionPotion(potion);
            }
        }

    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "damage"
    )
    public static class FairyPotion {
        public FairyPotion() {
        }

        @SpireInsertPatch(
                locator = OnUsePotionPotionPatch.Locator.class,
                localvars = {"p"}
        )
        public static void Insert(AbstractPlayer __instance, DamageInfo info, AbstractPotion potion) {
            OnUsePotionPotionPatch.Do(potion);
        }
    }

    @SpirePatches({@SpirePatch(
            clz = PotionPopUp.class,
            method = "updateInput"
    ), @SpirePatch(
            clz = PotionPopUp.class,
            method = "updateTargetMode"
    )})
    public static class NormalPotions {
        public NormalPotions() {
        }

        @SpireInsertPatch(
                locator = OnUsePotionPotionPatch.Locator.class,
                localvars = {"potion"}
        )
        public static void Insert(PotionPopUp __instance, AbstractPotion potion) {
            OnUsePotionPotionPatch.Do(potion);
        }
    }

    private static class Locator extends SpireInsertLocator {
        private Locator() {
        }

        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(TopPanel.class, "destroyPotion");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
