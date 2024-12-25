package themimic.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Sozu;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.ObtainPotionEffect;
import themimic.character.MimicCharacter;

import static themimic.TheMimicMod.makeID;

public class SpiritOfAnything extends OnUsePotionPotion {
    public static final String ID = makeID(SpiritOfAnything.class.getSimpleName());

    public SpiritOfAnything() {
        super(ID, 1, PotionRarity.RARE, PotionSize.EYE, PotionEffect.RAINBOW, Color.WHITE, Color.CLEAR, null);
        playerClass = MimicCharacter.Meta.MIMIC_CHAR;
    }

    @Override
    public String getDescription() {
        if (potency > 1) {
            return DESCRIPTIONS[0] + potency + DESCRIPTIONS[2];
        } else {
            return DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        }
    }

    @Override
    public boolean canUse() {
        return false;
    }

    @Override
    public void use(AbstractCreature target) {
    }

    @Override
    public void onUsePotionPotion (AbstractPotion potion) {
        if (!potion.ID.equals(SpiritOfAnything.ID)) {
            AbstractDungeon.player.removePotion(this);
            if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
                for (int i = 0; i < potency; ++i) {
                    addToBot(new ObtainPotionAction(potion.makeCopy()));
                }
            } else if (AbstractDungeon.player.hasRelic(Sozu.ID)) {
                AbstractDungeon.player.getRelic(Sozu.ID).flash();
            } else {
                for (int i = 0; i < potency; ++i) {
                    AbstractDungeon.effectsQueue.add(new ObtainPotionEffect(potion.makeCopy()));
                }
            }
        }
    }
}
