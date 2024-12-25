package themimic.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;
import themimic.powers.BasePower;

public class OnStackPowerPatch {
    public OnStackPowerPatch() {
    }
    static SpireReturn<Void> StackPowerPower(AbstractGameAction action, AbstractCreature target, AbstractCreature source, float[] duration, AbstractPower powerToApply, int amount) {
        if (powerToApply instanceof BasePower) {
            ((BasePower) powerToApply).onStackPower(powerToApply, target, source, amount);
        }
        return SpireReturn.Continue();
    }

    @SpirePatch(
            clz = ApplyPowerAction.class,
            method = "update"
    )
    public static class StackPowerPower {
        public StackPowerPower() {
        }

        @SpireInsertPatch(
                locator = StackPowerPower.Locator.class,
                localvars = {"duration", "powerToApply"}
        )
        public static SpireReturn<Void> Insert(ApplyPowerAction __instance, @ByRef float[] duration, AbstractPower powerToApply) {
            return OnStackPowerPatch.StackPowerPower(__instance, __instance.target, __instance.source, duration, powerToApply, __instance.amount);
        }

        private static class Locator extends SpireInsertLocator {
            private Locator() {
            }

            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "updateDescription");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}

