package net.the_blue_shark;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;


public class MagicOriginsClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {

//        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
//                "key.magic_origins.magic_origins_first_keybind", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_ALT,
//                "category.magic_origins.magic_origins_keybinds"
//        ));
//        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
//                "key.magic_origins.magic_origins_second_keybind", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT,
//                "category.magic_origins.magic_origins_keybinds"
//        ));
    }
}
