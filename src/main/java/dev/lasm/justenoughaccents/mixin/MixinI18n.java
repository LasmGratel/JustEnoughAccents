package dev.lasm.justenoughaccents.mixin;

import net.minecraft.client.resources.Locale;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Locale.class)
public class MixinI18n {
    @Inject(at = @At("RETURN"), method = "translateKeyPrivate", cancellable = true)
    private void format(String translateKey, CallbackInfoReturnable<String> ci) {
        ci.setReturnValue(I18n.translateToLocal(translateKey) + " " + I18n.translateToFallback(translateKey));
    }
}
