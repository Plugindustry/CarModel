package io.github.plugindustry.carmodel.block;

import io.github.plugindustry.carmodel.ConstItem;
import io.github.plugindustry.carmodel.utils.ExtendedInteractor;
import io.github.plugindustry.wheelcore.interfaces.Tickable;
import io.github.plugindustry.wheelcore.interfaces.block.*;
import io.github.plugindustry.wheelcore.interfaces.inventory.Position;
import io.github.plugindustry.wheelcore.interfaces.inventory.SlotSize;
import io.github.plugindustry.wheelcore.interfaces.power.EnergyInputable;
import io.github.plugindustry.wheelcore.interfaces.power.EnergyOutputable;
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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class TestBlock extends DummyBlock implements Tickable, EnergyInputable, EnergyOutputable, TexturedBlock, PistonPushable, PistonPullable {
    public final static TestBlock INSTANCE = new TestBlock();
    private final Window window;

    @SuppressWarnings("unchecked")
    private TestBlock() {
        window = new Window(new SlotSize(9, 1), "Test");
        window.addWidget(new WidgetFixedItem("fixed_1",
                ItemStackUtil.create(Material.IRON_INGOT)
                             .displayName("I'm fixed")
                             .getItemStack()), new Position(1, 1));
        window.addWidget(new WidgetButton("button_1", ItemStackUtil.create(Material.OAK_SIGN).displayName("I'm button")
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
                                                                     .then(Definers.scan("height", new Vector(0, 1, 0),
                                                                             Material.COBBLESTONE, 16))
                                                                     .check(env -> env.<Integer>getEnvironmentArg(
                                                                             "height") >= 5)
                                                                     .then(env -> env.setEnvironmentArg("height",
                                                                             env.<Integer>getEnvironmentArg(
                                                                                     "height") - 1))
                                                                     .check(Matchers.cube(3, "height", 3,
                                                                             Material.COBBLESTONE)));
    }

    @Nullable
    @Override
    public BlockData getInitialData(@Nullable ItemStack item, @Nonnull Block block, @Nullable Block blockAgainst, @Nullable Player player) {
        return new TestBlockData("test");
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
    public boolean onInteract(@Nonnull Player player, @Nonnull Action action, @Nullable EquipmentSlot hand, @Nullable ItemStack tool, @Nullable Block block, @Nullable Entity entity) {
        if (super.onInteract(player, action, hand, tool, block, entity)) {
            if (action == Action.RIGHT_CLICK_BLOCK)
                player.openInventory(
                        ((TestBlockData) Objects.requireNonNull(MainManager.getBlockData(Objects.requireNonNull(
                                block).getLocation()))).interactor.getInventory());
            return true;
        }
        return false;
    }

    @Override
    public void onTick() {
        MainManager.blockDataProvider.blocksOf(this).forEach(block -> {
            TestBlockData data = (TestBlockData) MainManager.getBlockData(block);
            if (Objects.requireNonNull(data).attr)
                PowerManager.outputPower(block, 30);
            else
                PowerManager.inputPower(block, 30);
        });
    }

    @Override
    public void finishInput(@Nonnull Location block, @Nonnull Wire.PowerPacket packet) {
    }

    @Override
    public boolean finishOutput(@Nonnull Location block, @Nonnull Wire.PowerPacket packet) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getTextureItem(@Nonnull Location location, @Nonnull Player player) {
        return new ItemStack(Material.DIAMOND_BLOCK);
    }

    @Override
    public boolean onPistonPull(@Nonnull Block block, @Nonnull Block piston, @Nonnull BlockFace direction, @Nonnull List<Block> pulledBlocks) {
        return true;
    }

    @Override
    public boolean onPistonPush(@Nonnull Block block, @Nonnull Block piston, @Nonnull BlockFace direction, @Nonnull List<Block> pushedBlocks) {
        return true;
    }

    public static class TestBlockData extends BlockData {
        private transient final ExtendedInteractor<TestBlockData> interactor = new ExtendedInteractor<>(INSTANCE.window,
                this);
        public String test = "test";
        public boolean attr = false;

        public TestBlockData() {
        }

        public TestBlockData(String test) {
            this.test = test;
        }
    }
}
