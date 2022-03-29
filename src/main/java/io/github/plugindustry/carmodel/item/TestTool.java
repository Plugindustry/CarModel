package io.github.plugindustry.carmodel.item;

import io.github.plugindustry.wheelcore.interfaces.item.DummyTool;
import io.github.plugindustry.wheelcore.manager.MainManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TestTool extends DummyTool {
    public final static TestTool INSTANCE = new TestTool();

    private TestTool() {
    }

    @Override
    public float getToolBonus(@Nonnull Block block, @Nonnull ItemStack tool) {
        return 22;
    }

    @Override
    public boolean isSuitable(@Nonnull Block block, @Nonnull ItemStack tool) {
        return block.getType() == Material.OBSIDIAN && MainManager.getBlockInstance(block.getLocation()) == null;
    }

    @Nonnull
    @Override
    public Optional<List<ItemStack>> getOverrideItemDrop(@Nonnull Block block, @Nonnull ItemStack tool) {
        if (block.getType() == Material.OBSIDIAN && MainManager.getBlockInstance(block.getLocation()) == null)
            return Optional.of(Collections.singletonList(new ItemStack(Material.OBSIDIAN)));
        else
            return super.getOverrideItemDrop(block, tool);
    }
}
