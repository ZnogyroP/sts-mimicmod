package themimic.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import themimic.relics.FacePaint;
import themimic.relics.MutatingGarden;

import java.util.ArrayList;
import java.lang.reflect.Field;

public class AnyColorCardRelicPatch {
    @SpirePatch(
            clz=RewardItem.class,
            method="claimReward"
    )
    public static class ClaimReward
    {
        public static void Postfix(RewardItem __instance)
        {
            if (__instance.type == RewardItem.RewardType.CARD && AbstractDungeon.player.hasRelic(FacePaint.ID)) {
                AbstractDungeon.player.getRelic(FacePaint.ID).flash();
            }
        }
    }

    @SpirePatch(
            clz=AbstractDungeon.class,
            method="getRewardCards"
    )
    public static class AddCardReward
    {
        public static ArrayList<AbstractCard> Postfix(ArrayList<AbstractCard> __result)
        {
            if (AbstractDungeon.player.hasRelic(FacePaint.ID)) {
                FacePaint relic = (FacePaint) AbstractDungeon.player.getRelic(FacePaint.ID);

                AbstractCard.CardRarity rarity = AbstractDungeon.rollRarity();
                AbstractCard card = relic.getRewardCard(rarity);

                if (card != null) {
                    __result.add(card.makeCopy());
                }
            }

            return __result;
        }
    }

    private static Field PAD_X_f = null;

    @SpirePatch(
            clz= CardRewardScreen.class,
            method="placeCards"
    )
    public static class PositionCards
    {
        public static void Prefix(CardRewardScreen __instance, float x, float y)
        {
            if (__instance.rewardGroup.size() == 5) {
                float PAD_X = 40.0f * Settings.scale;
                try {
                    if (PAD_X_f == null) {
                        PAD_X_f = CardRewardScreen.class.getDeclaredField("PAD_X");
                        PAD_X_f.setAccessible(true);
                    }
                    PAD_X = PAD_X_f.getFloat(null);
                } catch (IllegalAccessException | NoSuchFieldException ignored) {
                }

                __instance.rewardGroup.get(0).target_x = Settings.WIDTH / 2.0f - (AbstractCard.IMG_WIDTH + PAD_X) * 1.5f;
                __instance.rewardGroup.get(1).target_x = Settings.WIDTH / 2.0f - (AbstractCard.IMG_WIDTH + PAD_X) * 0.75f;
                __instance.rewardGroup.get(2).target_x = Settings.WIDTH / 2.0f;
                __instance.rewardGroup.get(3).target_x = Settings.WIDTH / 2.0f + (AbstractCard.IMG_WIDTH + PAD_X) * 0.75f;
                __instance.rewardGroup.get(4).target_x = Settings.WIDTH / 2.0f + (AbstractCard.IMG_WIDTH + PAD_X) * 1.5f;
                __instance.rewardGroup.get(0).target_y = y;
                __instance.rewardGroup.get(1).target_y = y;
                __instance.rewardGroup.get(2).target_y = y;
                __instance.rewardGroup.get(3).target_y = y;
                __instance.rewardGroup.get(4).target_y = y;
            }
        }
    }
}

