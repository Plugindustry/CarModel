package io.github.plugindustry.carmodel;

import io.github.plugindustry.carmodel.block.TestBlock;
import io.github.plugindustry.carmodel.block.TestEnergyInput;
import io.github.plugindustry.carmodel.block.TestEnergyOutput;
import io.github.plugindustry.carmodel.block.TestWire;
import io.github.plugindustry.carmodel.item.TestItem;
import io.github.plugindustry.carmodel.item.TestTool;
import io.github.plugindustry.wheelcore.i18n.I18n;
import io.github.plugindustry.wheelcore.manager.MainManager;
import io.github.plugindustry.wheelcore.manager.RecipeRegistry;
import io.github.plugindustry.wheelcore.manager.recipe.ShapedRecipeFactory;
import io.github.plugindustry.wheelcore.manager.recipe.ShapelessRecipe;
import org.bukkit.Material;
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

        I18n.load(Locale.SIMPLIFIED_CHINESE,
                new InputStreamReader(Objects.requireNonNull(getResource("zh_cn.lang")), StandardCharsets.UTF_8));
        I18n.load(Locale.US,
                new InputStreamReader(Objects.requireNonNull(getResource("en_us.lang")), StandardCharsets.UTF_8));
        MainManager.registerBlock("TEST_BLOCK", TestBlock.INSTANCE);
        MainManager.registerBlock("TEST_WIRE", TestWire.INSTANCE);
        MainManager.registerBlock("TEST_ENERGY_INPUT", TestEnergyInput.INSTANCE);
        MainManager.registerBlock("TEST_ENERGY_OUTPUT", TestEnergyOutput.INSTANCE);
        MainManager.registerItem("TEST_ITEM", TestItem.INSTANCE);
        MainManager.registerItem("TEST_TOOL", TestTool.INSTANCE);
        ConstItem.init();

        RecipeRegistry.register(ShapedRecipeFactory.create().pattern("aaa", "nsn", "nsn")
                                                   .map('a', ConstItem.TEST_ITEM)
                                                   .map('s', new ItemStack(Material.STICK))
                                                   .build(ConstItem.TEST_TOOL), "cm/test_tool_recipe", false);
        RecipeRegistry.register(new ShapelessRecipe(ConstItem.TEST_ITEM,
                new ItemStack(Material.IRON_INGOT),
                new ItemStack(Material.DIAMOND)), "cm/test_item_recipe");
        RecipeRegistry.updatePlaceholders();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
