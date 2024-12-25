package themimic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class IncreaseCostAction extends AbstractGameAction {

    UUID uuid;

    public IncreaseCostAction(UUID targetUUID, int amount) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.uuid = targetUUID;
        this.amount = amount;
    }

    public void update() {
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid))
            c.modifyCostForCombat(amount);
        this.isDone = true;
    }
}
