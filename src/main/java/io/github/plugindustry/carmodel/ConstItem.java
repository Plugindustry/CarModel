package io.github.plugindustry.carmodel;

import io.github.plugindustry.carmodel.item.TestItem;
import io.github.plugindustry.wheelcore.i18n.I18n;
import io.github.plugindustry.wheelcore.interfaces.item.DummyBlockItem;
import io.github.plugindustry.wheelcore.interfaces.item.ItemBase;
import io.github.plugindustry.wheelcore.manager.ItemMapping;
import io.github.plugindustry.wheelcore.manager.MainManager;
import io.github.plugindustry.wheelcore.utils.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class ConstItem {
    public static ItemStack TEST_ENERGY_INPUT_ITEM;
    public static ItemStack TEST_ENERGY_OUTPUT_ITEM;
    public static ItemStack TEST_BLOCK_ITEM;
    public static ItemStack TEST_WIRE_ITEM;
    public static ItemStack TEST_ITEM;

    public static void init() {
        ItemBase instance = new DummyBlockItem();
        MainManager.registerItem("TEST_BLOCK", instance);
        ItemMapping.set("TEST_BLOCK", ItemStackUtil.create(Material.IRON_BLOCK)
                .setDisplayName(I18n.getLocaleString("item.test_block.name"))
                .setLore(I18n.getLocaleStringList("item.test_block.lore"))
                .setInstance(instance)
                .getItemStack());
        TEST_BLOCK_ITEM = ItemMapping.get("TEST_BLOCK");

        ItemMapping.set("TEST_ITEM", ItemStackUtil.create(Material.IRON_INGOT)
                .setDisplayName(I18n.getLocaleString("item.test_item.name"))
                .setLore(I18n.getLocaleStringList("item.test_item.lore"))
                .setInstance(TestItem.INSTANCE)
                .setOreDictionary("test")
                .getItemStack());
        TEST_ITEM = ItemMapping.get("TEST_ITEM");

        instance = new DummyBlockItem();
        MainManager.registerItem("TEST_WIRE", instance);
        ItemMapping.set("TEST_WIRE", ItemStackUtil.create(Material.IRON_BARS)
                .setDisplayName(I18n.getLocaleString("item.test_wire.name"))
                .setLore(I18n.getLocaleStringList("item.test_wire.lore"))
                .setInstance(instance)
                .getItemStack());
        TEST_WIRE_ITEM = ItemMapping.get("TEST_WIRE");

        instance = new DummyBlockItem();
        MainManager.registerItem("TEST_ENERGY_INPUT", instance);
        ItemMapping.set("TEST_ENERGY_INPUT",
                        ItemStackUtil.create(Material.IRON_BLOCK).setDisplayName(I18n.getLocaleString(
                                "item.test_energy_input.name")).setLore(I18n.getLocaleStringList(
                                "item.test_energy_input.lore")).setInstance(instance).getItemStack());
        TEST_ENERGY_INPUT_ITEM = ItemMapping.get("TEST_ENERGY_INPUT");

        instance = new DummyBlockItem();
        MainManager.registerItem("TEST_ENERGY_OUTPUT", instance);
        ItemMapping.set("TEST_ENERGY_OUTPUT",
                        ItemStackUtil.create(Material.REDSTONE_BLOCK).setDisplayName(I18n.getLocaleString(
                                "item.test_energy_output.name")).setLore(I18n.getLocaleStringList(
                                "item.test_energy_output.lore")).setInstance(instance).getItemStack());
        TEST_ENERGY_OUTPUT_ITEM = ItemMapping.get("TEST_ENERGY_OUTPUT");
    }
}
