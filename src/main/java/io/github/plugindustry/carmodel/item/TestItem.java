package io.github.plugindustry.carmodel.item;

import io.github.plugindustry.carmodel.ConstItem;
import io.github.plugindustry.wheelcore.interfaces.Tickable;
import io.github.plugindustry.wheelcore.interfaces.item.DummyItem;
import io.github.plugindustry.wheelcore.utils.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class TestItem extends DummyItem implements Tickable {
    public final static TestItem INSTANCE = new TestItem();

    private TestItem() {
    }

    @Override
    public void onTick() {
        Bukkit.getOnlinePlayers().stream()
              .filter(p -> ItemStackUtil.isSimilar(p.getInventory().getItemInMainHand(), ConstItem.TEST_ITEM))
              .forEach(p -> p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 20, 0)));
    }
}
