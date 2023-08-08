package io.github.plugindustry.carmodel.item;

import io.github.plugindustry.wheelcore.interfaces.item.DummyItem;
import io.github.plugindustry.wheelcore.manager.EntityDamageHandler;
import io.github.plugindustry.wheelcore.utils.InventoryUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DarkTalisman extends DummyItem {
    public final static DarkTalisman INSTANCE = new DarkTalisman();

    private DarkTalisman() {
        EntityDamageHandler.infoModifiers.add(new EntityDamageHandler.DamageInfoModifier() {
            @Override
            public boolean modify(@Nonnull EntityDamageEvent.DamageCause cause,
                    @Nonnull EntityDamageHandler.DamageInfo info, boolean canBlock, @Nullable Entity damagerEntity,
                    @Nullable Block damagerBlock, @Nonnull Entity damagee) {
                if (damagee instanceof Player player &&
                    InventoryUtil.contains(player.getInventory(), DarkTalisman.INSTANCE) &&
                    (cause == EntityDamageEvent.DamageCause.MAGIC || cause == EntityDamageEvent.DamageCause.POISON ||
                     cause == EntityDamageEvent.DamageCause.WITHER || cause == EntityDamageEvent.DamageCause.THORNS ||
                     (cause == EntityDamageEvent.DamageCause.ENTITY_ATTACK && damagerEntity != null &&
                      damagerEntity.getType() == EntityType.AREA_EFFECT_CLOUD))) info.applyArmor = true;
                info.damage *= 1.2;
                return true;
            }
        });
    }
}