package io.github.plugindustry.carmodel;

import io.github.plugindustry.carmodel.block.*;
import io.github.plugindustry.carmodel.fluid.TestFluid;
import io.github.plugindustry.carmodel.item.DarkTalisman;
import io.github.plugindustry.carmodel.item.TestItem;
import io.github.plugindustry.carmodel.item.TestTool;
import io.github.plugindustry.wheelcore.i18n.I18n;
import io.github.plugindustry.wheelcore.manager.MainManager;
import io.github.plugindustry.wheelcore.manager.RecipeRegistry;
import io.github.plugindustry.wheelcore.manager.recipe.ShapedRecipeFactory;
import io.github.plugindustry.wheelcore.manager.recipe.ShapelessRecipe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

public final class CarModel extends JavaPlugin {
    public static CarModel instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        I18n.load(Locale.SIMPLIFIED_CHINESE, this,
                new InputStreamReader(Objects.requireNonNull(getResource("zh_cn.lang")), StandardCharsets.UTF_8));
        I18n.load(Locale.US, this,
                new InputStreamReader(Objects.requireNonNull(getResource("en_us.lang")), StandardCharsets.UTF_8));
        MainManager.registerBlock(new NamespacedKey(this, "test_block"), TestBlock.INSTANCE);
        MainManager.registerBlock(new NamespacedKey(this, "test_wire"), TestWire.INSTANCE);
        MainManager.registerBlock(new NamespacedKey(this, "test_energy_input"), TestEnergyInput.INSTANCE);
        MainManager.registerBlock(new NamespacedKey(this, "test_energy_output"), TestEnergyOutput.INSTANCE);
        MainManager.registerBlock(new NamespacedKey(this, "test_fluid_input"), TestFluidInput.INSTANCE);
        MainManager.registerBlock(new NamespacedKey(this, "test_fluid_output"), TestFluidOutput.INSTANCE);
        MainManager.registerBlock(new NamespacedKey(this, "test_fluid_pipe"), TestFluidPipe.INSTANCE);
        MainManager.registerItem(new NamespacedKey(this, "test_item"), TestItem.INSTANCE);
        MainManager.registerItem(new NamespacedKey(this, "test_tool"), TestTool.INSTANCE);
        MainManager.registerItem(new NamespacedKey(this, "dark_talisman"), DarkTalisman.INSTANCE);
        MainManager.registerFluid(new NamespacedKey(this, "test_fluid"), TestFluid.INSTANCE);
        ConstItem.init();

        RecipeRegistry.register(ShapedRecipeFactory.create().pattern("aaa", "nsn", "nsn").map('a', ConstItem.TEST_ITEM)
                        .map('s', new ItemStack(Material.STICK)).build(ConstItem.TEST_TOOL),
                new NamespacedKey(this, "test_tool_recipe"), false);
        RecipeRegistry.register(new ShapelessRecipe(ConstItem.TEST_ITEM, new ItemStack(Material.IRON_INGOT),
                new ItemStack(Material.DIAMOND)), new NamespacedKey(this, "test_item_recipe"));
        RecipeRegistry.updatePlaceholders();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}