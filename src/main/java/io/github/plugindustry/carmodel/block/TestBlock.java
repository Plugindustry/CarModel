package io.github.plugindustry.carmodel.block;

import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import io.github.plugindustry.carmodel.CarModel;
import io.github.plugindustry.carmodel.ConstItem;
import io.github.plugindustry.wheelcore.interfaces.Tickable;
import io.github.plugindustry.wheelcore.interfaces.block.*;
import io.github.plugindustry.wheelcore.interfaces.transport.power.EnergyInputable;
import io.github.plugindustry.wheelcore.interfaces.transport.power.EnergyOutputable;
import io.github.plugindustry.wheelcore.interfaces.transport.power.EnergyPacket;
import io.github.plugindustry.wheelcore.manager.MainManager;
import io.github.plugindustry.wheelcore.manager.MultiBlockManager;
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
import java.util.Optional;

public class TestBlock extends DummyBlock implements Tickable, EnergyInputable, EnergyOutputable, TexturedBlock,
        PistonPushable, PistonPullable {
    public final static TestBlock INSTANCE = new TestBlock();

    private TestBlock() {
        MultiBlockManager.register(this, MultiBlockManager.Conditions.create().then(Relocators.move(0, 1, 0))
                .then(Definers.scan("height", new Vector(0, 1, 0), Material.COBBLESTONE, 16))
                .check(env -> env.<Integer>getEnvironmentArg("height") >= 5)
                .then(env -> env.setEnvironmentArg("height", env.<Integer>getEnvironmentArg("height") - 1))
                .check(Matchers.cube(3, "height", 3, Material.COBBLESTONE)));
    }

    @Nullable
    @Override
    public BlockData getInitialData(@Nullable ItemStack item, @Nonnull Block block, @Nullable Block blockAgainst,
            @Nullable Player player) {
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
    public boolean onInteract(@Nonnull Player player, @Nonnull Action action, @Nullable EquipmentSlot hand,
            @Nullable ItemStack tool, @Nullable Block block, @Nullable Entity entity) {
        if (super.onInteract(player, action, hand, tool, block, entity)) {
            if (action == Action.RIGHT_CLICK_BLOCK) ((TestBlockData) Objects.requireNonNull(
                    MainManager.getBlockData(Objects.requireNonNull(block).getLocation()))).window.show(player);
            return true;
        }
        return false;
    }

    @Override
    public void onTick() {
        MainManager.blockDataProvider.blocksOf(this).forEach(block -> {
            TestBlockData data = (TestBlockData) MainManager.getBlockData(block);
            if (Objects.requireNonNull(data).attr) new EnergyPacket(block, 30).spread(block);
        });
    }

    @Override
    public void input(@Nonnull Location block, double amount) {
    }

    @Override
    public boolean output(@Nonnull Location block, double amount) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getTextureItem(@Nonnull Location location, @Nonnull Player player) {
        return new ItemStack(Material.DIAMOND_BLOCK);
    }

    @Override
    public boolean onPistonPull(@Nonnull Block block, @Nonnull Block piston, @Nonnull BlockFace direction,
            @Nonnull List<Block> pulledBlocks) {
        return true;
    }

    @Override
    public boolean onPistonPush(@Nonnull Block block, @Nonnull Block piston, @Nonnull BlockFace direction,
            @Nonnull List<Block> pushedBlocks) {
        return true;
    }

    @Override
    public double demand(@Nonnull Location block) {
        return 30;
    }

    @Override
    public boolean available(@Nonnull Location loc) {
        return !((TestBlockData) Objects.requireNonNull(MainManager.getBlockData(loc))).attr;
    }

    @Override
    public Optional<Float> getBlastResistance(@Nonnull Block block) {
        return Optional.of(1200.0f);
    }

    public static class TestBlockData extends BlockData {
        private transient final InventoryGui window;
        public String test = "test";
        public boolean attr = false;

        public TestBlockData() {
            window = new InventoryGui(CarModel.instance, null, "Test", new String[]{"fb   "});
            window.addElement(new StaticGuiElement('f',
                    ItemStackUtil.create(Material.IRON_INGOT).displayName("I'm fixed").getItemStack()));
            window.addElement(new StaticGuiElement('b',
                    ItemStackUtil.create(Material.OAK_SIGN).displayName("I'm button").getItemStack(), (click) -> {
                click.getWhoClicked().sendMessage("Hello world!");
                PlayerUtil.sendActionBar((Player) click.getWhoClicked(), "Test");
                click.getWhoClicked().sendMessage("Data: " + test);

                MultiBlockManager.getAvailableStructures(TestBlock.INSTANCE).stream()
                        .map(MultiBlockManager::getStructureData).map(env -> env.<Integer>getEnvironmentArg("height"))
                        .forEach(i -> click.getWhoClicked().sendMessage(String.valueOf(i)));

                attr = !attr;
                click.getWhoClicked().sendMessage("Attr: " + attr);
                return true;
            }));
        }

        public TestBlockData(String test) {
            this();
            this.test = test;
        }

        @Override
        public void unload() {
            window.close();
        }
    }
}