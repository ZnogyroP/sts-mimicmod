package themimic.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import themimic.relics.MutatingGarden;

public class CombatRewardsRelicPatch {
    @SpirePatch(
            clz = CombatRewardScreen.class,
            method = "setupItemReward"
    )
    public static class onCombatRewards {
        @SpireInsertPatch (locator=Locator2.class)
        public static void Insert (CombatRewardScreen __instance) {
            if (AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoom && AbstractDungeon.player.hasRelic(MutatingGarden.ID) &&
                    !(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomElite) &&
                    !(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.rooms.MonsterRoomBoss)) {

                RewardItem cardReward = new RewardItem();
                RewardItem cardReward2 = new RewardItem();
                if (!cardReward.cards.isEmpty()) {
                    __instance.rewards.add(cardReward);
                    __instance.rewards.add(cardReward2);
                }
            }
        }
        private static class Locator2 extends SpireInsertLocator {

            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "getCurrRoom");
                return new int[] {LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[9]};}
        }
    }
}

