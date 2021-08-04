package io.github.plugindustry.carmodel.block;

import io.github.plugindustry.carmodel.CarModel;
import io.github.plugindustry.carmodel.ConstItem;
import io.github.plugindustry.wheelcore.interfaces.Tickable;
import io.github.plugindustry.wheelcore.interfaces.block.BlockData;
import io.github.plugindustry.wheelcore.interfaces.block.DummyBlock;
import io.github.plugindustry.wheelcore.interfaces.block.Wire;
import io.github.plugindustry.wheelcore.interfaces.power.EnergyInputable;
import io.github.plugindustry.wheelcore.inventory.ClassicInventoryInteractor;
import io.github.plugindustry.wheelcore.inventory.Position;
import io.github.plugindustry.wheelcore.inventory.SlotSize;
import io.github.plugindustry.wheelcore.inventory.Window;
import io.github.plugindustry.wheelcore.inventory.widget.WidgetButton;
import io.github.plugindustry.wheelcore.inventory.widget.WidgetFixedItem;
import io.github.plugindustry.wheelcore.manager.MainManager;
import io.github.plugindustry.wheelcore.manager.PowerManager;
import io.github.plugindustry.wheelcore.utils.ItemStackUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestEnergyInput extends DummyBlock implements Tickable, EnergyInputable {
    public final static TestEnergyInput INSTANCE = new TestEnergyInput();
    public final ConversationFactory conversationFactory;

    private TestEnergyInput() {
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
                        return "Input amount: ";
                    }
                })
                .withEscapeSequence("/cancel")
                .withTimeout(10)
                .thatExcludesNonPlayersWithMessage("Illegal State.")
                .addConversationAbandonedListener(abandonedEvent -> {
                    if (abandonedEvent.gracefulExit())
                        abandonedEvent.getContext().getForWhom().sendRawMessage("Done.");
                    else
                        abandonedEvent.getContext().getForWhom().sendRawMessage("Conversation abandoned by" +
                                                                                abandonedEvent.getCanceller()
                                                                                        .getClass()
                                                                                        .getName());
                });
    }

    @Override
    public void onTick() {
        MainManager.dataProvider.blocksOf(this)
                .stream()
                .map(MainManager::getBlockData)
                .forEach(data -> ((TestEnergyInputData) data).tickInput = 0.0);
        MainManager.dataProvider.blocksOf(this).forEach(block -> PowerManager.inputPower(block,
                                                                                         ((TestEnergyInputData) MainManager
                                                                                                 .getBlockData(block)).expectInput));
    }

    @Override
    public boolean onBlockPlace(@Nonnull ItemStack item, @Nonnull Block block) {
        if (super.onBlockPlace(item, block)) {
            MainManager.setBlockData(block.getLocation(), new TestEnergyInputData());
            return true;
        }
        return false;
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
        ((TestEnergyInputData) MainManager.getBlockData(block)).input(packet.amount);
    }

    public static class TestEnergyInputData extends BlockData {
        public transient final ClassicInventoryInteractor interactor;
        private transient final Window window;
        public double tickInput = 0.0;
        public double expectInput = Double.MAX_VALUE;

        public TestEnergyInputData() {
            window = new Window(new SlotSize(9, 1), "Test");
            window.addWidget(new WidgetFixedItem("fixed_1",
                                                 ItemStackUtil.create(Material.REDSTONE)
                                                         .setDisplayName("Input: 0.0")
                                                         .getItemStack()), new Position(1, 1));
            window.addWidget(new WidgetButton("button_1",
                                              ItemStackUtil.create(Material.OAK_SIGN)
                                                      .setDisplayName("Change input")
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
                                                                         .setDisplayName("Input: " + tickInput)
                                                                         .getItemStack());
            window.sync();
        }
    }
}
