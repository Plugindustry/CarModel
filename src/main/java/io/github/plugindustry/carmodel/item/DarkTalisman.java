package io.github.plugindustry.carmodel.item;

import io.github.plugindustry.carmodel.ConstItem;
import io.github.plugindustry.wheelcore.interfaces.item.DummyItem;
import io.github.plugindustry.wheelcore.manager.EntityDamageHandler;
import org.bukkit.entity.Entity;
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
                    @Nonnull EntityDamageHandler.DamageInfo info, boolean canBlock, @Nullable Entity damager,
                    @Nonnull Entity damagee) {
                if (damagee instanceof Player player && player.getInventory().contains(ConstItem.DARK_TALISMAN) &&
                    (cause == EntityDamageEvent.DamageCause.MAGIC || cause == EntityDamageEvent.DamageCause.POISON ||
                     cause == EntityDamageEvent.DamageCause.WITHER || cause == EntityDamageEvent.DamageCause.THORNS))
                    info.applyArmor = true;
                info.damage *= 1.2;
                return true;
            }
        });
    }
}