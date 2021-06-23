package io.github.plugindustry.carmodel.block;

import io.github.plugindustry.carmodel.ConstItem;
import io.github.plugindustry.wheelcore.interfaces.block.BlockData;
import io.github.plugindustry.wheelcore.interfaces.block.DummyBlock;
import io.github.plugindustry.wheelcore.inventory.InventoryWindow;
import io.github.plugindustry.wheelcore.inventory.Position;
import io.github.plugindustry.wheelcore.inventory.SlotSize;
import io.github.plugindustry.wheelcore.inventory.WindowInteractor;
import io.github.plugindustry.wheelcore.inventory.widget.WidgetButton;
import io.github.plugindustry.wheelcore.inventory.widget.WidgetFixedItem;
import io.github.plugindustry.wheelcore.manager.MainManager;
import io.github.plugindustry.wheelcore.manager.MultiBlockManager;
import io.github.plugindustry.wheelcore.utils.ItemStackUtil;
import io.github.plugindustry.wheelcore.utils.PlayerUtil;
import io.github.plugindustry.wheelcore.world.multiblock.Definers;
import io.github.plugindustry.wheelcore.world.multiblock.Matchers;
import io.github.plugindustry.wheelcore.world.multiblock.Relocators;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.Objects;

public class TestBlock extends DummyBlock {
    public final static TestBlock INSTANCE = new TestBlock();
    private final WindowInteractor interactor;

    private TestBlock() {
        InventoryWindow window = new InventoryWindow(new SlotSize(9, 1), "Test");
        window.addWidget(new WidgetFixedItem("fixed_1",
                                             ItemStackUtil.create(Material.IRON_INGOT)
                                                     .setDisplayName("I'm fixed")
                                                     .getItemStack()), new Position(1, 1));
        window.addWidget(new WidgetButton("button_1",
                                          ItemStackUtil.create(Material.OAK_SIGN)
                                                  .setDisplayName("I'm button")
                                                  .getItemStack(),
                                          (pos, event) -> {
                                              event.getWhoClicked().sendMessage("Hello world!");
                                              PlayerUtil.sendActionBar((Player) event.getWhoClicked(), "Test");
                                              BlockData data = MainManager.getBlockData(Objects.requireNonNull(event.getWhoClicked()
                                                                                                                       .getTargetBlockExact(
                                                                                                                               4))
                                                                                                .getLocation());
                                              if (data instanceof TestBlockData)
                                                  event.getWhoClicked().sendMessage("Data: " +
                                                                                    ((TestBlockData) data).test);

                                              MultiBlockManager.getAvailableStructures(this)
                                                      .stream()
                                                      .map(MultiBlockManager::getStructureData)
                                                      .map(env -> env.<Integer>getEnvironmentArg("height"))
                                                      .forEach(i -> event.getWhoClicked()
                                                              .sendMessage(String.valueOf(i)));
                                          }), new Position(2, 1));

        interactor = new WindowInteractor(window);

        MultiBlockManager.register(this, MultiBlockManager.Conditions.create()
                .then(Relocators.move(0, 1, 0))
                .then(Definers.scan("height", new Vector(0, 1, 0), Material.COBBLESTONE, 16))
                .check(env -> env.<Integer>getEnvironmentArg("height") >= 5)
                .then(env -> env.setEnvironmentArg("height", env.<Integer>getEnvironmentArg("height") - 1))
                .check(Matchers.cube(3, "height", 3, Material.COBBLESTONE)));
    }

    @Override
    public boolean onBlockPlace(ItemStack item, Block block) {
        if (super.onBlockPlace(item, block)) {
            MainManager.setBlockData(block.getLocation(), new TestBlockData("test"));
            return true;
        }
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getItemStack() {
        return ConstItem.TEST_BLOCK_ITEM;
    }

    @Nonnull
    @Override
    public Material getMaterial() {
        return Material.OAK_WOOD;
    }

    @Override
    public boolean onInteract(Player player, Action action, ItemStack tool, Block block) {
        if (super.onInteract(player, action, tool, block)) {
            if (action == Action.RIGHT_CLICK_BLOCK) {
                player.openInventory(interactor.getInventory());
            }
            return true;
        }
        return false;
    }

    public static class TestBlockData extends BlockData {
        public String test;

        public TestBlockData(String test) {
            this.test = test;
        }
    }
}
