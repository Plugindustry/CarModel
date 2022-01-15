package io.github.plugindustry.carmodel.item;

import io.github.plugindustry.wheelcore.interfaces.item.DummyItem;
import io.github.plugindustry.wheelcore.interfaces.item.Tool;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class TestTool extends DummyItem implements Tool {
    public final static TestTool INSTANCE = new TestTool();

    private TestTool() {}

    @Override
    public float getToolBonus(@Nonnull Block block, @Nonnull ItemStack tool) {
        return 22;
    }

    @Override
    public boolean isSuitable(@Nonnull Block block, @Nonnull ItemStack tool) {
        return block.getType() == Material.OBSIDIAN;
    }
}
