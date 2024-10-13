package net.glasslauncher.mods.starlightbeta.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Cancellable;
import com.llamalad7.mixinextras.sugar.Local;
import net.glasslauncher.mods.starlightbeta.StarlightWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Chunk.class)
public class ChunkFuckeryMixin {

    @Shadow @Final public int x;

    @Shadow @Final public int z;

    @Shadow public World world;

    @Unique
    public void updateLight(int localX, int worldY, int localZ) {
        int worldX = localX | (x << 4);
        int worldZ = localZ | (z << 4);

        ((StarlightWorld) world).starlight_Beta$getBlockLight().checkBlockEmittance(worldX, worldY, worldZ);
        if (!world.dimension.hasCeiling) {
            ((StarlightWorld) world).starlight_Beta$getSkyLight().checkSkyEmittance(worldX, worldY, worldZ);
        }
    }

    @Redirect(method = "method_873", at = @At(value = "FIELD", target = "Lnet/minecraft/world/dimension/Dimension;hasCeiling:Z"))
    private boolean yeetBadLight(Dimension instance) {
        return true;
    }

    @ModifyConstant(method = "method_873", constant = @Constant(intValue = 16, ordinal = 2))
    private int cancelLightUpdates(int constant) {
        return 0;
    }

    @ModifyVariable(method = "method_889", at = @At("STORE"), index = 8)
    private int yeetBadLightMore(int value, @Cancellable CallbackInfo ci) {
        ci.cancel();
        return value;
    }

    @WrapOperation(method = "setBlock(IIIII)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;queueLightUpdate(Lnet/minecraft/world/LightType;IIIIII)V", ordinal = 0))
    private void e(World instance, LightType minX, int minY, int minZ, int maxX, int maxY, int maxZ, int i, Operation<Void> original) {}

    @WrapOperation(method = "setBlock(IIIII)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;method_887(II)V", ordinal = 0))
    private void ee(Chunk instance, int z, int i, Operation<Void> original) {}

    @WrapOperation(method = "setBlock(IIIII)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;queueLightUpdate(Lnet/minecraft/world/LightType;IIIIII)V", ordinal = 1))
    private void useStarlight(World instance, LightType lightType, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Operation<Void> original, @Local(argsOnly = true, ordinal = 1) int x, @Local(argsOnly = true, ordinal = 2) int y, @Local(argsOnly = true, ordinal = 3) int z) {
        updateLight(x, y, z);
    }

    @WrapOperation(method = "setBlock(IIII)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;queueLightUpdate(Lnet/minecraft/world/LightType;IIIIII)V", ordinal = 0))
    private void a(World instance, LightType minX, int minY, int minZ, int maxX, int maxY, int maxZ, int i, Operation<Void> original) {}

    @WrapOperation(method = "setBlock(IIII)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;method_887(II)V", ordinal = 0))
    private void aa(Chunk instance, int z, int i, Operation<Void> original) {}

    @WrapOperation(method = "setBlock(IIII)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;queueLightUpdate(Lnet/minecraft/world/LightType;IIIIII)V", ordinal = 1))
    private void useStarlighta(World instance, LightType lightType, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Operation<Void> original, @Local(argsOnly = true, ordinal = 1) int x, @Local(argsOnly = true, ordinal = 2) int y, @Local(argsOnly = true, ordinal = 3) int z) {
        updateLight(x, y, z);
    }
}
