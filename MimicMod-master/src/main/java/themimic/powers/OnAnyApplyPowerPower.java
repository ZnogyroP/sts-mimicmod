package themimic.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import themimic.util.GeneralUtils;
import themimic.util.TextureLoader;

public abstract class OnAnyApplyPowerPower extends BasePower {
    public OnAnyApplyPowerPower(String id, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount) {
        super(id, powerType, isTurnBased, owner, source, amount);
    }

    public void onAnyPowerApply(AbstractPower powerToApply, AbstractCreature target, AbstractCreature source) {
    }
}