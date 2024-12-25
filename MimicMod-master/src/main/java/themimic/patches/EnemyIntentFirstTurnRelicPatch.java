package themimic.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import themimic.powers.BasePower;
import themimic.relics.BaseRelic;

public class EnemyIntentFirstTurnRelicPatch {
    @SpirePatch(
            clz = AbstractMonster.class,
            method = "createIntent"
    )
    public static class onCreateIntent {
        @SpirePostfixPatch
        public static void Postfix (AbstractMonster ___instance) {
            for (AbstractRelic r : AbstractDungeon.player.relics) { //Loops through relics
                if (r instanceof BaseRelic) {//checks if r is one of yours
                    ((BaseRelic)r).onCreateIntent(___instance);//Casts the relic as a BaseRelic since you know it is one
                }
            }
            for (AbstractPower p : ___instance.powers) { //Loops through powers on the monster
                if (p instanceof BasePower) {//checks if p is one of yours
                    ((BasePower)p).onCreateIntent(___instance);//Casts the power as a BasePower since you know it is one
                }
            }
        }
    }
}
