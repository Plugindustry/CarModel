package io.github.plugindustry.carmodel;

import io.github.plugindustry.carmodel.block.TestBlock;
import io.github.plugindustry.carmodel.block.TestWire;
import io.github.plugindustry.carmodel.item.TestItem;
import io.github.plugindustry.wheelcore.i18n.I18n;
import io.github.plugindustry.wheelcore.manager.MainManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class CarModel extends JavaPlugin {
    public static CarModel instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        I18n.load(new InputStreamReader(Objects.requireNonNull(getResource("zh_cn.lang")), StandardCharsets.UTF_8));
        ConstItem.init();
        MainManager.registerBlock("TEST_BLOCK", TestBlock.INSTANCE);
        MainManager.registerBlock("TEST_WIRE", TestWire.INSTANCE);
        MainManager.registerItem("TEST_ITEM", TestItem.INSTANCE);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
