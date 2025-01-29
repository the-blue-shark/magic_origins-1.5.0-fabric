package net.the_blue_shark;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import net.the_blue_shark.block.entity.layer.ModModelLayers;
import net.the_blue_shark.entity.ModEntities;
import net.the_blue_shark.entity.client.BambooDartModel;
import net.the_blue_shark.entity.client.BambooDartRenderer;
import org.lwjgl.glfw.GLFW;

public class MagicOriginsClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BAMBOO_DART, BambooDartModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.BAMBOO_DART, BambooDartRenderer::new);

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.magic_origins.magic_origins_first_keybind", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R,
                "category.magic_origins.magic_origins_keybinds"
        ));
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.magic_origins.magic_origins_second_keybind", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G,
                "category.magic_origins.magic_origins_keybinds"
        ));
    }
}
