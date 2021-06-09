package io.github.plugindustry.carmodel;

import io.github.plugindustry.wheelcore.i18n.I18n;
import io.github.plugindustry.wheelcore.manager.ItemMapping;
import io.github.plugindustry.wheelcore.utils.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.logging.Level;

public final class ConstItem {
    public static ItemStack TEST_BLOCK_ITEM;

    public static void init() {
        ItemMapping.set("TEST_BLOCK", ItemStackUtil.create(Material.IRON_BLOCK)
                .setDisplayName(I18n.getLocaleString("item.test_block.name"))
                .setLore(I18n.getLocaleStringList("item.test_block.lore"))
                .setId("TEST_BLOCK")
                .setOreDictionary("test")
                .getItemStack());
        TEST_BLOCK_ITEM = ItemMapping.get("TEST_BLOCK");

        CarModel.instance.getLogger().log(Level.INFO, "{0} {1} ", new Object[]{
                Objects.requireNonNull(TEST_BLOCK_ITEM.getItemMeta()).getDisplayName(),
                Objects.requireNonNull(TEST_BLOCK_ITEM.getItemMeta().getLore())
        });
    }
}
