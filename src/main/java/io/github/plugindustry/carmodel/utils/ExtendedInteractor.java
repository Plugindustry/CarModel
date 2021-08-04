package io.github.plugindustry.carmodel.utils;

import io.github.plugindustry.wheelcore.inventory.ClassicInventoryInteractor;
import io.github.plugindustry.wheelcore.inventory.Window;

public class ExtendedInteractor<E> extends ClassicInventoryInteractor {
    public final E extend;

    public ExtendedInteractor(Window window, E extend) {
        super(window);
        this.extend = extend;
    }
}
