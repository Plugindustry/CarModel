package io.github.plugindustry.carmodel;

import io.github.plugindustry.carmodel.block.TestBlock;
import io.github.plugindustry.wheelcore.i18n.I18n;
import io.github.plugindustry.wheelcore.manager.MainManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public final class CarModel extends JavaPlugin {
    public static CarModel instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        I18n.load(new InputStreamReader(Objects.requireNonNull(getResource("zh_cn.lang")), StandardCharsets.UTF_8));
        ConstItem.init();
        MainManager.registerBlock("TEST_BLOCK", new TestBlock());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
