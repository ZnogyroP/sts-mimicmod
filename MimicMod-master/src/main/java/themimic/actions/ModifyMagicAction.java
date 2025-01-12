package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import java.util.UUID;

public class ModifyMagicAction extends AbstractGameAction {
    private UUID uuid;

    public ModifyMagicAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        for(AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.baseMagicNumber += this.amount;
            if (c.baseMagicNumber < 0) {
                c.baseMagicNumber = 0;
            }
        }
        this.isDone = true;
    }
}
