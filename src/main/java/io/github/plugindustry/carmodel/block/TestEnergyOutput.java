package io.github.plugindustry.carmodel.block;

import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import io.github.plugindustry.carmodel.CarModel;
import io.github.plugindustry.carmodel.ConstItem;
import io.github.plugindustry.wheelcore.interfaces.Tickable;
import io.github.plugindustry.wheelcore.interfaces.block.BlockData;
import io.github.plugindustry.wheelcore.interfaces.block.DummyBlock;
import io.github.plugindustry.wheelcore.interfaces.power.EnergyOutputable;
import io.github.plugindustry.wheelcore.interfaces.power.EnergyPacket;
import io.github.plugindustry.wheelcore.manager.MainManager;
import io.github.plugindustry.wheelcore.utils.ItemStackUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.conversations.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class TestEnergyOutput extends DummyBlock implements Tickable, EnergyOutputable {
    public final static TestEnergyOutput INSTANCE = new TestEnergyOutput();
    public final ConversationFactory conversationFactory;

    private TestEnergyOutput() {
        this.conversationFactory = new ConversationFactory(CarModel.instance).withModality(true)
                .withFirstPrompt(new NumericPrompt() {
                    @Override
                    protected @Nullable
                    Prompt acceptValidatedInput(@Nonnull ConversationContext context, @Nonnull Number input) {
                        context.setSessionData("amount", input.doubleValue());
                        return Prompt.END_OF_CONVERSATION;
                    }

                    @Override
                    protected boolean isNumberValid(@Nonnull ConversationContext context, @Nonnull Number input) {
                        return input.doubleValue() >= 0.0;
                    }

                    @Override
                    public @Nonnull
                    String getPromptText(@Nonnull ConversationContext context) {
                        return "Output amount: ";
                    }
                }).withEscapeSequence("/cancel").withTimeout(10).thatExcludesNonPlayersWithMessage("Illegal State.")
                .addConversationAbandonedListener(abandonedEvent -> {
                    if (abandonedEvent.gracefulExit()) abandonedEvent.getContext().getForWhom().sendRawMessage("Done.");
                    else abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation abandoned by" +
                            Objects.requireNonNull(abandonedEvent.getCanceller()).getClass().getName());
                });
    }

    @Override
    public void onTick() {
        MainManager.blockDataProvider.blocksOf(this).stream().map(MainManager::getBlockData).forEach(data -> {
            TestEnergyOutputData temp = (TestEnergyOutputData) data;
            Objects.requireNonNull(temp).output(-temp.tickOutput);
        });
        MainManager.blockDataProvider.blocksOf(this).forEach(block -> new EnergyPacket(block,
                ((TestEnergyOutputData) Objects.requireNonNull(MainManager.getBlockData(block))).expectOutput).spread(
                block));
    }

    @Override
    public boolean onInteract(@Nonnull Player player, @Nonnull Action action, @Nullable EquipmentSlot hand,
            @Nullable ItemStack tool, @Nullable Block block, @Nullable Entity entity) {
        if (super.onInteract(player, action, hand, tool, block, entity)) {
            if (action == Action.RIGHT_CLICK_BLOCK) ((TestEnergyOutputData) Objects.requireNonNull(
                    MainManager.getBlockData(Objects.requireNonNull(block).getLocation()))).window.show(player);
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public BlockData getInitialData(@Nullable ItemStack item, @Nonnull Block block, @Nullable Block blockAgainst,
            @Nullable Player player) {
        return new TestEnergyOutputData();
    }

    @Nonnull
    @Override
    public ItemStack getItemStack() {
        return ConstItem.TEST_ENERGY_OUTPUT_ITEM;
    }

    @Nonnull
    @Override
    public Material getMaterial() {
        return Material.REDSTONE_BLOCK;
    }

    @Override
    public boolean output(@Nonnull Location block, double amount) {
        ((TestEnergyOutputData) Objects.requireNonNull(MainManager.getBlockData(block))).output(amount);
        return true;
    }

    public static class TestEnergyOutputData extends BlockData {
        private transient final InventoryGui window;
        public double tickOutput = 0.0;
        public double expectOutput = 0.0;

        public TestEnergyOutputData() {
            window = new InventoryGui(CarModel.instance, null, "Test", new String[]{"fb   "});
            window.addElement(new StaticGuiElement('f',
                    ItemStackUtil.create(Material.REDSTONE).displayName("Output: 0.0").getItemStack()));
            window.addElement(new StaticGuiElement('b',
                    ItemStackUtil.create(Material.OAK_SIGN).displayName("Change output").getItemStack(), (click) -> {
                if (click.getWhoClicked() instanceof Conversable) {
                    Conversation conversation = INSTANCE.conversationFactory.buildConversation(
                            (Conversable) click.getWhoClicked());
                    conversation.addConversationAbandonedListener(abandonedEvent -> {
                        if (abandonedEvent.gracefulExit()) expectOutput = (Double) Objects.requireNonNull(
                                abandonedEvent.getContext().getSessionData("amount"));
                    });
                    conversation.begin();
                }
                return true;
            }));
        }

        public void output(double amount) {
            tickOutput += amount;
            ((StaticGuiElement) window.getElement('f')).setItem(
                    ItemStackUtil.create(Material.REDSTONE).displayName("Output: " + tickOutput).getItemStack());
            window.draw();
        }

        @Override
        public void unload() {
            window.close();
        }
    }
}