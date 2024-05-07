package dev.lasm.justenoughaccents.mixin;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import mezz.jei.search.PrefixInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = mezz.jei.search.PrefixInfo.class, remap = false)
public class MixinPrefixInfo {
    private static Char2ObjectMap<PrefixInfo> instances;

    @Inject(at = @At("RETURN"), method = "<clinit>")
    private static void onInit(CallbackInfo ci) {

    }
}
