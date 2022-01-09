/*******************************************************************************
 * Copyright 2022, the Glitchfiend Team.
 * Creative Commons Attribution-NonCommercial-NoDerivatives 4.0.
 ******************************************************************************/
package terrablender.mixin.client;

import com.mojang.datafixers.util.Function4;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import terrablender.hooks.MinecraftHooks;

import java.util.function.Function;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft
{
    @Shadow
    private void doLoadLevel(String string, RegistryAccess.RegistryHolder registryHolder, Function<LevelStorageSource.LevelStorageAccess, DataPackConfig> function, Function4<LevelStorageSource.LevelStorageAccess, RegistryAccess.RegistryHolder, ResourceManager, DataPackConfig, WorldData> function4, boolean bl, Minecraft.ExperimentalDialogType experimentalDialogType) {}

    @Overwrite
    public void createLevel(String levelName, LevelSettings levelSettings, RegistryAccess.RegistryHolder registryAccess, WorldGenSettings currentSettings)
    {
        this.doLoadLevel(levelName, registryAccess, (levelStorageAccess) -> {
            return levelSettings.getDataPackConfig();
        }, (levelStorageAccess, p_167887_, resourceManager, dataPackConfig) -> {
            return MinecraftHooks.createPrimaryLevelData(levelSettings, registryAccess, currentSettings, resourceManager);
        }, false, Minecraft.ExperimentalDialogType.NONE);
    }
}