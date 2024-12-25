package themimic.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.IntentFlashAction;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import themimic.powers.BasePower;

public class EnemyTakeTurnPowerPatch {
    @SpirePatch(
            clz = GameActionManager.class,
            method = "getNextAction"
    )
    public static class beforeMonsterAttacksPowers {
        @SpireInsertPatch (locator=Locator2.class, localvars = {"m"})
        public static void Insert (GameActionManager __instance, AbstractMonster m) {
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof BasePower) {
                    ((BasePower)p).beforeMonsterAttacks(m);
                }
            }
        }
        private static class Locator2 extends SpireInsertLocator {

            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.NewExprMatcher(IntentFlashAction.class);
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);}
        }
    }
}

