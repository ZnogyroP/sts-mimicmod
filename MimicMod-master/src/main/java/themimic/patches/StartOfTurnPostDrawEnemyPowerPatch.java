package themimic.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import themimic.powers.BasePower;

public class StartOfTurnPostDrawEnemyPowerPatch {
    @SpirePatch(
            clz = GameActionManager.class,
            method = "getNextAction"
    )
    public static class atStartOfTurnPostDrawEnemyPowers {
        @SpireInsertPatch (locator=Locator2.class)
        public static void Insert (GameActionManager __instance) {
            for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                for (AbstractPower p : mo.powers) {
                    if (p instanceof BasePower) {
                        ((BasePower)p).atStartOfTurnPostDrawEnemyPowers(mo);
                    }
                }
            }
        }
        private static class Locator2 extends SpireInsertLocator {

            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.NewExprMatcher(EnableEndTurnButtonAction.class);
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);};
        }
    }
}

