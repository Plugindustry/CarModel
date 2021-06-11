package io.github.plugindustry.carmodel;

import io.github.plugindustry.wheelcore.i18n.I18n;
import io.github.plugindustry.wheelcore.manager.ItemMapping;
import io.github.plugindustry.wheelcore.utils.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class ConstItem {
    public static ItemStack TEST_BLOCK_ITEM;
    public static ItemStack TEST_ITEM;

    public static void init() {
        ItemMapping.set("TEST_BLOCK", ItemStackUtil.create(Material.IRON_BLOCK)
                .setDisplayName(I18n.getLocaleString("item.test_block.name"))
                .setLore(I18n.getLocaleStringList("item.test_block.lore"))
                .setId("TEST_BLOCK")
                .getItemStack());
        TEST_BLOCK_ITEM = ItemMapping.get("TEST_BLOCK");

        ItemMapping.set("TEST_ITEM", ItemStackUtil.create(Material.IRON_INGOT)
                .setDisplayName(I18n.getLocaleString("item.test_item.name"))
                .setLore(I18n.getLocaleStringList("item.test_item.lore"))
                .setId("TEST_ITEM")
                .setOreDictionary("test")
                .getItemStack());
        TEST_ITEM = ItemMapping.get("TEST_ITEM");
    }
}
