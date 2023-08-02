package io.github.plugindustry.carmodel.block;

import io.github.plugindustry.carmodel.fluid.TestFluid;
import io.github.plugindustry.wheelcore.interfaces.block.DummyBlock;
import io.github.plugindustry.wheelcore.interfaces.fluid.FluidBase;
import io.github.plugindustry.wheelcore.interfaces.fluid.FluidStack;
import io.github.plugindustry.wheelcore.interfaces.transport.fluid.FluidInputable;
import org.bukkit.Location;
import org.bukkit.Material;

import javax.annotation.Nonnull;

public class TestFluidInput extends DummyBlock implements FluidInputable {
    public final static TestFluidInput INSTANCE = new TestFluidInput();

    @Override
    public int demand(@Nonnull Location location, @Nonnull FluidBase fluidBase) {
        return fluidBase == TestFluid.INSTANCE ? Integer.MAX_VALUE : 0;
    }

    @Override
    public void input(@Nonnull Location location, @Nonnull FluidStack fluidStack) {

    }

    @Override
    public boolean available(@Nonnull Location location) {
        return true;
    }

    @Nonnull
    @Override
    public Material getMaterial() {
        return Material.GLASS;
    }
}