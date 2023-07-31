package io.github.plugindustry.carmodel;

import io.github.plugindustry.carmodel.item.TestItem;
import io.github.plugindustry.carmodel.item.TestTool;
import io.github.plugindustry.wheelcore.i18n.I18n;
import io.github.plugindustry.wheelcore.interfaces.item.DummyBlockItem;
import io.github.plugindustry.wheelcore.interfaces.item.ItemBase;
import io.github.plugindustry.wheelcore.manager.ItemMapping;
import io.github.plugindustry.wheelcore.manager.MainManager;
import io.github.plugindustry.wheelcore.utils.ItemStackUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public final class ConstItem {
    public static ItemStack TEST_ENERGY_INPUT_ITEM;
    public static ItemStack TEST_ENERGY_OUTPUT_ITEM;
    public static ItemStack TEST_BLOCK_ITEM;
    public static ItemStack TEST_WIRE_ITEM;
    public static ItemStack TEST_ITEM;
    public static ItemStack TEST_TOOL;

    public static void init() {
        ItemBase instance = new DummyBlockItem();
        MainManager.registerItem(new NamespacedKey(CarModel.instance, "test_block"), instance);
        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_block"),
                ItemStackUtil.create(Material.IRON_BLOCK).displayName(I18n.getLocalePlaceholder("item/test_block/name"))
                        .lore(Collections.singletonList(I18n.getLocaleListPlaceholder("item/test_block/lore")))
                        .instance(instance).getItemStack());
        TEST_BLOCK_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_block"));

        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_item"),
                ItemStackUtil.create(Material.IRON_INGOT).displayName(I18n.getLocalePlaceholder("item/test_item/name"))
                        .lore(Collections.singletonList(I18n.getLocaleListPlaceholder("item/test_item/lore")))
                        .instance(TestItem.INSTANCE).oreDictionary("test").getItemStack());
        TEST_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_item"));

        instance = new DummyBlockItem();
        MainManager.registerItem(new NamespacedKey(CarModel.instance, "test_wire"), instance);
        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_wire"),
                ItemStackUtil.create(Material.IRON_BARS).displayName(I18n.getLocalePlaceholder("item/test_wire/name"))
                        .lore(Collections.singletonList(I18n.getLocaleListPlaceholder("item/test_wire/lore")))
                        .instance(instance).getItemStack());
        TEST_WIRE_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_wire"));

        instance = new DummyBlockItem();
        MainManager.registerItem(new NamespacedKey(CarModel.instance, "test_energy_input"), instance);
        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_energy_input"),
                ItemStackUtil.create(Material.IRON_BLOCK)
                        .displayName(I18n.getLocalePlaceholder("item/test_energy_input/name"))
                        .lore(Collections.singletonList(I18n.getLocaleListPlaceholder("item/test_energy_input/lore")))
                        .instance(instance).getItemStack());
        TEST_ENERGY_INPUT_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_energy_input"));

        instance = new DummyBlockItem();
        MainManager.registerItem(new NamespacedKey(CarModel.instance, "test_energy_output"), instance);
        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_energy_output"),
                ItemStackUtil.create(Material.REDSTONE_BLOCK)
                        .lore(Collections.singletonList(I18n.getLocaleListPlaceholder("item/test_energy_output/lore")))
                        .instance(instance).getItemStack());
        TEST_ENERGY_OUTPUT_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_energy_output"));

        ItemMapping.set(new NamespacedKey(CarModel.instance, "TEST_TOOL"), ItemStackUtil.create(Material.IRON_PICKAXE)
                .displayName(I18n.getLocalePlaceholder("item/test_tool/name"))
                .lore(Collections.singletonList(I18n.getLocaleListPlaceholder("item/test_tool/lore")))
                .instance(TestTool.INSTANCE).getItemStack());
        TEST_TOOL = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_tool"));
    }
}