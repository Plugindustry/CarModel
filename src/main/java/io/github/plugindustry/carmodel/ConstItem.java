package io.github.plugindustry.carmodel;

import io.github.plugindustry.carmodel.item.DarkTalisman;
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
    public static ItemStack TEST_FLUID_INPUT_ITEM;
    public static ItemStack TEST_FLUID_OUTPUT_ITEM;
    public static ItemStack TEST_FLUID_PIPE_ITEM;
    public static ItemStack TEST_BLOCK_ITEM;
    public static ItemStack TEST_WIRE_ITEM;
    public static ItemStack TEST_ITEM;
    public static ItemStack TEST_TOOL;
    public static ItemStack DARK_TALISMAN;

    public static void init() {
        ItemBase instance = new DummyBlockItem();
        MainManager.registerItem(new NamespacedKey(CarModel.instance, "test_block"), instance);
        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_block"), ItemStackUtil.create(Material.IRON_BLOCK)
                .displayName(I18n.getLocalePlaceholder(new NamespacedKey(CarModel.instance, "item/test_block/name")))
                .lore(Collections.singletonList(
                        I18n.getLocaleListPlaceholder(new NamespacedKey(CarModel.instance, "item/test_block/lore"))))
                .instance(instance).getItemStack());
        TEST_BLOCK_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_block"));

        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_item"), ItemStackUtil.create(Material.IRON_INGOT)
                .displayName(I18n.getLocalePlaceholder(new NamespacedKey(CarModel.instance, "item/test_item/name")))
                .lore(Collections.singletonList(
                        I18n.getLocaleListPlaceholder(new NamespacedKey(CarModel.instance, "item/test_item/lore"))))
                .instance(TestItem.INSTANCE).oreDictionary("test").getItemStack());
        TEST_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_item"));

        instance = new DummyBlockItem();
        MainManager.registerItem(new NamespacedKey(CarModel.instance, "test_wire"), instance);
        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_wire"), ItemStackUtil.create(Material.IRON_BARS)
                .displayName(I18n.getLocalePlaceholder(new NamespacedKey(CarModel.instance, "item/test_wire/name")))
                .lore(Collections.singletonList(
                        I18n.getLocaleListPlaceholder(new NamespacedKey(CarModel.instance, "item/test_wire/lore"))))
                .instance(instance).getItemStack());
        TEST_WIRE_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_wire"));

        instance = new DummyBlockItem();
        MainManager.registerItem(new NamespacedKey(CarModel.instance, "test_energy_input"), instance);
        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_energy_input"),
                ItemStackUtil.create(Material.IRON_BLOCK).displayName(
                                I18n.getLocalePlaceholder(new NamespacedKey(CarModel.instance, "item/test_energy_input/name")))
                        .lore(Collections.singletonList(I18n.getLocaleListPlaceholder(
                                new NamespacedKey(CarModel.instance, "item/test_energy_input/lore"))))
                        .instance(instance).getItemStack());
        TEST_ENERGY_INPUT_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_energy_input"));

        instance = new DummyBlockItem();
        MainManager.registerItem(new NamespacedKey(CarModel.instance, "test_energy_output"), instance);
        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_energy_output"),
                ItemStackUtil.create(Material.REDSTONE_BLOCK).displayName(
                                I18n.getLocalePlaceholder(new NamespacedKey(CarModel.instance, "item/test_energy_output/name")))
                        .lore(Collections.singletonList(I18n.getLocaleListPlaceholder(
                                new NamespacedKey(CarModel.instance, "item/test_energy_output/lore"))))
                        .instance(instance).getItemStack());
        TEST_ENERGY_OUTPUT_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_energy_output"));

        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_tool"), ItemStackUtil.create(Material.IRON_PICKAXE)
                .displayName(I18n.getLocalePlaceholder(new NamespacedKey(CarModel.instance, "item/test_tool/name")))
                .lore(Collections.singletonList(
                        I18n.getLocaleListPlaceholder(new NamespacedKey(CarModel.instance, "item/test_tool/lore"))))
                .instance(TestTool.INSTANCE).getItemStack());
        TEST_TOOL = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_tool"));

        instance = new DummyBlockItem();
        MainManager.registerItem(new NamespacedKey(CarModel.instance, "test_fluid_input"), instance);
        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_fluid_input"), ItemStackUtil.create(Material.GLASS)
                .displayName(
                        I18n.getLocalePlaceholder(new NamespacedKey(CarModel.instance, "item/test_fluid_input/name")))
                .lore(Collections.singletonList(I18n.getLocaleListPlaceholder(
                        new NamespacedKey(CarModel.instance, "item/test_fluid_input/lore")))).instance(instance)
                .getItemStack());
        TEST_FLUID_INPUT_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_fluid_input"));

        instance = new DummyBlockItem();
        MainManager.registerItem(new NamespacedKey(CarModel.instance, "test_fluid_output"), instance);
        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_fluid_output"),
                ItemStackUtil.create(Material.RED_STAINED_GLASS).displayName(
                                I18n.getLocalePlaceholder(new NamespacedKey(CarModel.instance, "item/test_fluid_output/name")))
                        .lore(Collections.singletonList(I18n.getLocaleListPlaceholder(
                                new NamespacedKey(CarModel.instance, "item/test_fluid_output/lore"))))
                        .instance(instance).getItemStack());
        TEST_FLUID_OUTPUT_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_fluid_output"));

        instance = new DummyBlockItem();
        MainManager.registerItem(new NamespacedKey(CarModel.instance, "test_fluid_pipe"), instance);
        ItemMapping.set(new NamespacedKey(CarModel.instance, "test_fluid_pipe"),
                ItemStackUtil.create(Material.BLUE_STAINED_GLASS_PANE).displayName(
                                I18n.getLocalePlaceholder(new NamespacedKey(CarModel.instance, "item/test_fluid_pipe/name")))
                        .lore(Collections.singletonList(I18n.getLocaleListPlaceholder(
                                new NamespacedKey(CarModel.instance, "item/test_fluid_pipe/lore")))).instance(instance)
                        .getItemStack());
        TEST_FLUID_PIPE_ITEM = ItemMapping.get(new NamespacedKey(CarModel.instance, "test_fluid_pipe"));

        ItemMapping.set(new NamespacedKey(CarModel.instance, "dark_talisman"), ItemStackUtil.create(Material.OBSIDIAN)
                .displayName(I18n.getLocalePlaceholder(new NamespacedKey(CarModel.instance, "item/dark_talisman/name")))
                .lore(Collections.singletonList(
                        I18n.getLocaleListPlaceholder(new NamespacedKey(CarModel.instance, "item/dark_talisman/lore"))))
                .instance(DarkTalisman.INSTANCE).getItemStack());
        DARK_TALISMAN = ItemMapping.get(new NamespacedKey(CarModel.instance, "dark_talisman"));
    }
}