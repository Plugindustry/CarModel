package io.github.plugindustry.carmodel.block;

import io.github.plugindustry.carmodel.ConstItem;
import io.github.plugindustry.wheelcore.interfaces.block.Wire;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class TestWire extends Wire {
    public final static TestWire INSTANCE = new TestWire();

    private TestWire() {
    }

    @Nonnull
    @Override
    public ItemStack getItemStack() {
        return ConstItem.TEST_WIRE_ITEM;
    }

    @Nonnull
    @Override
    public Material getMaterial() {
        return Material.IRON_BARS;
    }

    @Override
    public double getMaxTransmissionEnergy() {
        return 90;
    }

    @Override
    public double getEnergyLoss() {
        return 0.05;
    }
}
