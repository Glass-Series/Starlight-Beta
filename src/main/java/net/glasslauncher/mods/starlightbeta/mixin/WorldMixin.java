package net.glasslauncher.mods.starlightbeta.mixin;

import net.glasslauncher.mods.starlightbeta.StarlightEngine;
import net.glasslauncher.mods.starlightbeta.StarlightWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("DataFlowIssue")
@Mixin(World.class)
public class WorldMixin implements StarlightWorld {

    @Unique
    private final StarlightEngine blockLight = new StarlightEngine(false, (World) (Object) this);
    @Unique
    private final StarlightEngine skyLight = new StarlightEngine(false, (World) (Object) this);

    @Override
    public StarlightEngine starlight_Beta$getBlockLight() {
        return blockLight;
    }

    @Override
    public StarlightEngine starlight_Beta$getSkyLight() {
        return skyLight;
    }
}
