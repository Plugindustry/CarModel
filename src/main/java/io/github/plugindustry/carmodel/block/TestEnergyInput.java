package io.github.plugindustry.carmodel.block;

import io.github.plugindustry.carmodel.CarModel;
import io.github.plugindustry.carmodel.ConstItem;
import io.github.plugindustry.wheelcore.interfaces.Tickable;
import io.github.plugindustry.wheelcore.interfaces.block.BlockData;
import io.github.plugindustry.wheelcore.interfaces.block.DummyBlock;
import io.github.plugindustry.wheelcore.interfaces.block.Wire;
import io.github.plugindustry.wheelcore.interfaces.inventory.Position;
import io.github.plugindustry.wheelcore.interfaces.inventory.SlotSize;
import io.github.plugindustry.wheelcore.interfaces.power.EnergyInputable;
import io.github.plugindustry.wheelcore.inventory.ClassicInventoryInteractor;
import io.github.plugindustry.wheelcore.inventory.Window;
import io.github.plugindustry.wheelcore.inventory.widget.WidgetButton;
import io.github.plugindustry.wheelcore.inventory.widget.WidgetFixedItem;
import io.github.plugindustry.wheelcore.manager.MainManager;
import io.github.plugindustry.wheelcore.manager.PowerManager;
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

public class TestEnergyInput extends DummyBlock implements Tickable, EnergyInputable {
    public final static TestEnergyInput INSTANCE = new TestEnergyInput();
    public final ConversationFactory conversationFactory;

    private TestEnergyInput() {
        this.conversationFactory = new ConversationFactory(CarModel.instance).withModality(true)
                                                                             .withFirstPrompt(new NumericPrompt() {
                                                                                 @Override
                                                                                 protected @Nullable
                                                                                 Prompt acceptValidatedInput(@Nonnull ConversationContext context, @Nonnull Number input) {
                                                                                     context.setSessionData("amount",
                                                                                             input.doubleValue());
                                                                                     return Prompt.END_OF_CONVERSATION;
                                                                                 }

                                                                                 @Override
                                                                                 protected boolean isNumberValid(@Nonnull ConversationContext context, @Nonnull Number input) {
                                                                                     return input.doubleValue() >= 0.0;
                                                                                 }

                                                                                 @Override
                                                                                 public @Nonnull
                                                                                 String getPromptText(@Nonnull ConversationContext context) {
                                                                                     return "Input amount: ";
                                                                                 }
                                                                             })
                                                                             .withEscapeSequence("/cancel")
                                                                             .withTimeout(10)
                                                                             .thatExcludesNonPlayersWithMessage(
                                                                                     "Illegal State.")
                                                                             .addConversationAbandonedListener(
                                                                                     abandonedEvent -> {
                                                                                         if (abandonedEvent.gracefulExit())
                                                                                             abandonedEvent.getContext()
                                                                                                           .getForWhom()
                                                                                                           .sendRawMessage(
                                                                                                                   "Done.");
                                                                                         else
                                                                                             abandonedEvent.getContext()
                                                                                                           .getForWhom()
                                                                                                           .sendRawMessage(
                                                                                                                   "Conversation abandoned by" +
                                                                                                                           abandonedEvent.getCanceller()
                                                                                                                                         .getClass()
                                                                                                                                         .getName());
                                                                                     });
    }

    @Override
    public void onTick() {
        MainManager.blockDataProvider.blocksOf(this).stream().map(MainManager::getBlockData).forEach(data -> {
            TestEnergyInputData temp = (TestEnergyInputData) data;
            Objects.requireNonNull(temp).input(-temp.tickInput);
        });
        MainManager.blockDataProvider.blocksOf(this).forEach(block -> PowerManager.inputPower(block,
                ((TestEnergyInputData) Objects.requireNonNull(
                        MainManager.getBlockData(
                                block))).expectInput));
    }

    @Override
    public boolean onInteract(@Nonnull Player player, @Nonnull Action action, @Nullable EquipmentSlot hand, @Nullable ItemStack tool, @Nullable Block block, @Nullable Entity entity) {
        if (super.onInteract(player, action, hand, tool, block, entity)) {
            if (action == Action.RIGHT_CLICK_BLOCK)
                player.openInventory(
                        ((TestEnergyInputData) Objects.requireNonNull(MainManager.getBlockData(Objects.requireNonNull(
                                block).getLocation()))).interactor.getInventory());
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public BlockData getInitialData(@Nullable ItemStack item, @Nonnull Block block, @Nullable Block blockAgainst, @Nullable Player player) {
        return new TestEnergyInputData();
    }

    @Nonnull
    @Override
    public ItemStack getItemStack() {
        return ConstItem.TEST_ENERGY_INPUT_ITEM;
    }

    @Nonnull
    @Override
    public Material getMaterial() {
        return Material.IRON_BLOCK;
    }

    @Override
    public void finishInput(@Nonnull Location block, @Nonnull Wire.PowerPacket packet) {
        ((TestEnergyInputData) Objects.requireNonNull(MainManager.getBlockData(block))).input(packet.amount);
    }

    public static class TestEnergyInputData extends BlockData {
        public transient final ClassicInventoryInteractor interactor;
        private transient final Window window;
        public double tickInput = 0.0;
        public double expectInput = Double.MAX_VALUE;

        public TestEnergyInputData() {
            window = new Window(new SlotSize(9, 1), "Test");
            window.addWidget(new WidgetFixedItem("fixed_1",
                    ItemStackUtil.create(Material.REDSTONE).displayName("Input: 0.0")
                                 .getItemStack()), new Position(1, 1));
            window.addWidget(new WidgetButton("button_1",
                    ItemStackUtil.create(Material.OAK_SIGN).displayName("Change input")
                                 .getItemStack(),
                    (pos, info) -> {
                        if (info.whoClicked instanceof Conversable) {
                            Conversation conversation = INSTANCE.conversationFactory.buildConversation(
                                    (Conversable) info.whoClicked);
                            conversation.addConversationAbandonedListener(abandonedEvent -> {
                                if (abandonedEvent.gracefulExit())
                                    expectInput = (Double) abandonedEvent.getContext()
                                                                         .getSessionData("amount");
                            });
                            conversation.begin();
                        }
                    }), new Position(2, 1));

            interactor = new ClassicInventoryInteractor(window);
        }

        public void input(double amount) {
            tickInput += amount;
            window.<WidgetFixedItem>getWidget("fixed_1").setItem(ItemStackUtil.create(Material.REDSTONE)
                                                                              .displayName("Input: " + tickInput)
                                                                              .getItemStack());
            window.sync();
        }
    }
}
