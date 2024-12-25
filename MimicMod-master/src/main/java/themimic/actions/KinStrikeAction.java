package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Objects;

public class KinStrikeAction extends AbstractGameAction {
    private int damage;
    private AbstractCard card = null;
    private final AbstractPlayer player;
    private final AbstractMonster targetMonster;
    private final String[] lice = {"FuzzyLouseNormal", "FuzzyLouseDefensive"};
    private final String[] slimes = {"SpikeSlime_S", "SpikeSlime_M", "SpikeSlime_L", "AcidSlime_S", "AcidSlime_M", "AcidSlime_L", "SlimeBoss"};
    private final String[] jaws = {"JawWorm", "Maw"};
    private final String[] slavers = {"SlaverRed", "SlaverBlue", "SlaverBoss"};
    private final String[] gremlins = {"GremlinTsundere", "GremlinWizard", "GremlinThief", "GremlinFat", "GremlinWarrior", "GremlinLeader", "GremlinNob"};
    private final String[] shapes = {"Spiker", "Exploder", "Repulsor", "SphericGuardian", "Deca", "Donu"};
    private final String[] bronzes = {"BronzeAutomaton", "BronzeOrb", "Sentry"};
    private final String[] birds = {"Byrd", "Cultist", "Chosen", "AwakenedOne"};
    private final String[] thieves = {"Looter", "Mugger", "BanditChild", "BanditLeader", "BanditBear"};
    private final String[] spires = {"Serpent", "SpireSpear", "SpireShield"};
    private final String[][] relatedMonsters = {lice, slimes, jaws, slavers, gremlins, shapes, bronzes, birds, thieves, spires};

    public KinStrikeAction(AbstractCard card, int damage, AbstractPlayer p, AbstractMonster m) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.damage = damage;
        this.player = p;
        this.targetMonster = m;
        this.card = card;
    }

    public void update() {
        String targetMonsterID = targetMonster.id;
        int arrayToCheck = -1;
        for(int i = 0; i < relatedMonsters.length; ++i){
            for (int j = 0; j < relatedMonsters[i].length; ++j){
                if (Objects.equals(relatedMonsters[i][j], targetMonsterID)){
                    arrayToCheck = i;
                    break;
                }
            }
        }

        if (arrayToCheck >= 0) {
            for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                for (int i = 0; i < relatedMonsters[arrayToCheck].length; ++i) {
                    if (Objects.equals(mo.id, relatedMonsters[arrayToCheck][i])){
                        this.card.calculateCardDamage(mo);
                        addToBot(new DamageAction(mo, new DamageInfo(player, this.card.damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL));
                    }
                }
            }
        } else {
            for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (Objects.equals(mo.id, targetMonsterID)){
                    this.card.calculateCardDamage(mo);
                    addToBot(new DamageAction(mo, new DamageInfo(player, this.card.damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL));
                }
            }
        }
        this.isDone = true;
    }
}
