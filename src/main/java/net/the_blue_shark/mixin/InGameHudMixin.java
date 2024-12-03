package net.the_blue_shark.mixin;

import com.ibm.icu.impl.coll.CollationSettings;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.the_blue_shark.MagicOrigins;
import net.the_blue_shark.item.ModItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Unique
    private static final Identifier BAMBOO_SNIPER_OVERLAY = Identifier.of(MagicOrigins.MOD_ID, "textures/misc/bamboo_sniper_scope.png");
    @Unique
    private float BambooSniperScale;
    @Inject(method = "renderMiscOverlays", at = @At("TAIL"))
    private void renderMiscOverlaysMixin(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.player != null) {
            PlayerEntity player = client.player;
            Perspective perspective = MinecraftClient.getInstance().options.getPerspective();
            ItemStack itemStack = player.getActiveItem();
            float g = tickCounter.getLastFrameDuration() / 20.0f;
            g = g > 1.0f ? 1.0f : g * g;
            float f = 1.0f - g * 1.5f;
            this.BambooSniperScale = MathHelper.lerp(0.5F * f, this.BambooSniperScale, 1.125F);
            if (player.isUsingItem() && itemStack.isOf(ModItems.BAMBOO_SNIPER) && perspective == Perspective.FIRST_PERSON) {
                this.renderBambooSniperOverlay(context, this.BambooSniperScale);
            }
        }
    }
    @Unique
    private void renderBambooSniperOverlay(DrawContext context, float scale) {
        float f = (float)Math.min(context.getScaledWindowWidth(), context.getScaledWindowHeight());
        float h = Math.min((float)context.getScaledWindowWidth() / f, (float)context.getScaledWindowHeight() / f) * scale;
        int i = MathHelper.floor(f * h);
        int j = MathHelper.floor(f * h);
        int k = (context.getScaledWindowWidth() - i) / 2;
        int l = (context.getScaledWindowHeight() - j) / 2;
        int m = k + i;
        int n = l + j;
        RenderSystem.enableBlend();
        context.drawTexture(BAMBOO_SNIPER_OVERLAY, k, l, -90, 0.0F, 0.0F, i, j, i, j);
        RenderSystem.disableBlend();
        context.fill(RenderLayer.getGuiOverlay(), 0, n, context.getScaledWindowWidth(), context.getScaledWindowHeight(), -90, Colors.BLACK);
        context.fill(RenderLayer.getGuiOverlay(), 0, 0, context.getScaledWindowWidth(), l, -90, Colors.BLACK);
        context.fill(RenderLayer.getGuiOverlay(), 0, l, k, n, -90, Colors.BLACK);
        context.fill(RenderLayer.getGuiOverlay(), m, l, context.getScaledWindowWidth(), n, -90, Colors.BLACK);
    }
}
