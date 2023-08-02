package io.github.plugindustry.carmodel.block;

import io.github.plugindustry.carmodel.fluid.TestFluid;
import io.github.plugindustry.wheelcore.interfaces.Tickable;
import io.github.plugindustry.wheelcore.interfaces.block.DummyBlock;
import io.github.plugindustry.wheelcore.interfaces.fluid.FluidStack;
import io.github.plugindustry.wheelcore.interfaces.transport.fluid.FluidOutputable;
import io.github.plugindustry.wheelcore.interfaces.transport.fluid.FluidPacket;
import io.github.plugindustry.wheelcore.manager.MainManager;
import org.bukkit.Location;
import org.bukkit.Material;

import javax.annotation.Nonnull;

public class TestFluidOutput extends DummyBlock implements FluidOutputable, Tickable {
    public final static TestFluidOutput INSTANCE = new TestFluidOutput();

    @Override
    public boolean output(@Nonnull Location location, @Nonnull FluidStack fluidStack) {
        return fluidStack.getType() == TestFluid.INSTANCE;
    }

    @Nonnull
    @Override
    public Material getMaterial() {
        return Material.RED_STAINED_GLASS;
    }

    @Override
    public void onTick() {
        MainManager.blockDataProvider.blocksOf(this)
                .forEach(block -> new FluidPacket(block, new FluidStack(TestFluid.INSTANCE, 100)).spread(block));
    }
}