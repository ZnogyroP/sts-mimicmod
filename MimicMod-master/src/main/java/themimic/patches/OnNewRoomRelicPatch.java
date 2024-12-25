package themimic.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import themimic.powers.BasePower;
import themimic.relics.BaseRelic;
import themimic.relics.MutatingGarden;

public class OnNewRoomRelicPatch {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "nextRoomTransitionStart"
    )
    public static class onNextRoomTransition {
        public static boolean cardRemoved;
        @SpirePrefixPatch
        public static SpireReturn<Void> Insert () {
            if (AbstractDungeon.player.hasRelic(MutatingGarden.ID) && !cardRemoved && !(AbstractDungeon.nextRoom.room instanceof VictoryRoom) && !(AbstractDungeon.nextRoom.room instanceof TrueVictoryRoom) && !(AbstractDungeon.nextRoom.room instanceof TreasureRoomBoss)){
                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    if (r instanceof BaseRelic) {
                        ((BaseRelic)r).nextFloorTransitionStart();
                    }
                }
                return SpireReturn.Return();
            } else {
                cardRemoved = false;
                return SpireReturn.Continue();
            }
        }
    }
}
