package io.github.plugindustry.carmodel.block;

import io.github.plugindustry.carmodel.ConstItem;
import io.github.plugindustry.carmodel.utils.ExtendedInteractor;
import io.github.plugindustry.wheelcore.interfaces.Tickable;
import io.github.plugindustry.wheelcore.interfaces.block.BlockData;
import io.github.plugindustry.wheelcore.interfaces.block.DummyBlock;
import io.github.plugindustry.wheelcore.inventory.Position;
import io.github.plugindustry.wheelcore.inventory.SlotSize;
import io.github.plugindustry.wheelcore.inventory.Window;
import io.github.plugindustry.wheelcore.inventory.widget.WidgetButton;
import io.github.plugindustry.wheelcore.inventory.widget.WidgetFixedItem;
import io.github.plugindustry.wheelcore.manager.MainManager;
import io.github.plugindustry.wheelcore.manager.MultiBlockManager;
import io.github.plugindustry.wheelcore.manager.PowerManager;
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

public class TestBlock extends DummyBlock implements Tickable {
    public final static TestBlock INSTANCE = new TestBlock();
    private final Window window;

    @SuppressWarnings("unchecked")
    private TestBlock() {
        window = new Window(new SlotSize(9, 1), "Test");
        window.addWidget(new WidgetFixedItem("fixed_1",
                                             ItemStackUtil.create(Material.IRON_INGOT)
                                                     .setDisplayName("I'm fixed")
                                                     .getItemStack()), new Position(1, 1));
        window.addWidget(new WidgetButton("button_1",
                                          ItemStackUtil.create(Material.OAK_SIGN)
                                                  .setDisplayName("I'm button")
                                                  .getItemStack(),
                                          (pos, info) -> {
                                              info.whoClicked.sendMessage("Hello world!");
                                              PlayerUtil.sendActionBar((Player) info.whoClicked, "Test");
                                              TestBlockData data = ((ExtendedInteractor<TestBlockData>) Objects.requireNonNull(
                                                      info.inventory.getHolder())).extend;
                                              info.whoClicked.sendMessage("Data: " + data.test);

                                              MultiBlockManager.getAvailableStructures(this)
                                                      .stream()
                                                      .map(MultiBlockManager::getStructureData)
                                                      .map(env -> env.<Integer>getEnvironmentArg("height"))
                                                      .forEach(i -> info.whoClicked.sendMessage(String.valueOf(i)));

                                              data.attr = !data.attr;
                                              info.whoClicked.sendMessage("Attr: " + data.attr);
                                          }), new Position(2, 1));

        MultiBlockManager.register(this, MultiBlockManager.Conditions.create()
                .then(Relocators.move(0, 1, 0))
                .then(Definers.scan("height", new Vector(0, 1, 0), Material.COBBLESTONE, 16))
                .check(env -> env.<Integer>getEnvironmentArg("height") >= 5)
                .then(env -> env.setEnvironmentArg("height", env.<Integer>getEnvironmentArg("height") - 1))
                .check(Matchers.cube(3, "height", 3, Material.COBBLESTONE)));
    }

    @Override
    public boolean onBlockPlace(@Nonnull ItemStack item, @Nonnull Block block) {
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
    public boolean onInteract(@Nonnull Player player, @Nonnull Action action, ItemStack tool, Block block) {
        if (super.onInteract(player, action, tool, block)) {
            if (action == Action.RIGHT_CLICK_BLOCK)
                player.openInventory(((TestBlockData) MainManager.getBlockData(block.getLocation())).interactor.getInventory());
            return true;
        }
        return false;
    }

    @Override
    public void onTick() {
        MainManager.dataProvider.blocksOf(this).forEach(block -> {
            TestBlockData data = (TestBlockData) MainManager.getBlockData(block);
            if (data.attr)
                PowerManager.outputPower(block, 30);
            else
                PowerManager.inputPower(block, 30);
        });
    }

    public static class TestBlockData extends BlockData {
        private transient final ExtendedInteractor<TestBlockData> interactor = new ExtendedInteractor<>(INSTANCE.window,
                                                                                                        this);
        public String test = "test";
        public boolean attr = false;

        public TestBlockData() {}

        public TestBlockData(String test) {
            this.test = test;
        }
    }
}
