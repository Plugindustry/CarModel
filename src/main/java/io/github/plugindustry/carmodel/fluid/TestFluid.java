package io.github.plugindustry.carmodel.fluid;

import io.github.plugindustry.carmodel.CarModel;
import io.github.plugindustry.wheelcore.i18n.I18n;
import io.github.plugindustry.wheelcore.interfaces.fluid.FluidBase;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

public class TestFluid implements FluidBase {
    public final static TestFluid INSTANCE = new TestFluid();

    @Nonnull
    @Override
    public String getDisplayName() {
        return I18n.getLocalePlaceholder(new NamespacedKey(CarModel.instance, "fluid/test_fluid/name"));
    }
}