package io.github.plugindustry.carmodel.block;

import io.github.plugindustry.wheelcore.interfaces.transport.fluid.FluidPipe;

public class TestFluidPipe extends FluidPipe {
    public static final TestFluidPipe INSTANCE = new TestFluidPipe();

    @Override
    public int getThreshold() {
        return 100;
    }

    @Override
    public int getSpeed() {
        return 2;
    }
}