package net.glasslauncher.mods.starlightbeta.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.glasslauncher.mods.starlightbeta.StarlightEngine;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkNibbleArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientChunkManager.class)
public class ClientChunkFuckery {

    @Unique
    boolean wasChunkGenerated = false;

    @WrapOperation(method = "loadChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/ClientChunkManager;method_1051(II)Lnet/minecraft/world/chunk/Chunk;"))
    private Chunk e(ClientChunkManager instance, int j, int i, Operation<Chunk> original, @Local Chunk var4) {
        wasChunkGenerated = var4 == null;
        return original.call(instance, j, i);
    }

    @Inject(method = "loadChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;method_881()V"))
    private void ee(int z, int par2, CallbackInfoReturnable<Chunk> cir, @Local Chunk var4) {
        if (wasChunkGenerated) {
            StarlightEngine.initLightingForChunk(var4);
        }
    }
}
