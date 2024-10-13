package net.glasslauncher.mods.starlightbeta.mixin;

import net.minecraft.world.chunk.ChunkNibbleArray;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkNibbleArray.class)
public class NibbleArrayMixin {

    @Shadow @Final public byte[] bytes;

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void a(int x, int y, int z, CallbackInfoReturnable<Integer> cir) {
        int index = x << 11 | z << 7 | y;
        final byte value = this.bytes[index >>> 1];

        // if we are an even index, we want lower 4 bits
        // if we are an odd index, we want upper 4 bits
        cir.setReturnValue((value >>> ((index & 1) << 2)) & 0xF);
    }

    @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    private void a(int x, int y, int z, int value, CallbackInfo ci) {
        final int index = x << 11 | z << 7 | y;
        final int shift = (index & 1) << 2;
        final int i = index >>> 1;
        this.bytes[i] = (byte)((this.bytes[i] & (0xF0 >>> shift)) | (value << shift));
        ci.cancel();
    }
}
