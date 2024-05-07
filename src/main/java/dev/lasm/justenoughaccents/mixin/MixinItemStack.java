package dev.lasm.justenoughaccents.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
    @Inject(at = @At("RETURN"), method = "getDisplayName", cancellable = true)
    private void getDisplayName(CallbackInfoReturnable<String> ci) {
        /*
        var mgr = Minecraft.getMinecraft().getLanguageManager();
        if (mgr == null || mgr.getCurrentLanguage() == null) return;
        var lang = mgr.getCurrentLanguage();
        if (lang.getLanguageCode().equals("en_us")) {
        } else {
            String enDisplayName = null;
            NBTTagCompound nbttagcompound = getSubCompound("display");
            if (nbttagcompound != null)
            {
                if (nbttagcompound.hasKey("Name", 8))
                {
//                    enDisplayName = nbttagcompound.getString("Name");
                    return;
                }

                if (nbttagcompound.hasKey("LocName", 8))
                {
                    enDisplayName = I18n.translateToFallback(nbttagcompound.getString("LocName"));
                }
            }
            if (enDisplayName == null)
                enDisplayName = I18n.translateToFallback(getItem().getUnlocalizedNameInefficiently((ItemStack) (Object) this) + ".name");
            ci.setReturnValue(ci.getReturnValue() + " " + enDisplayName);
        }*/
    }

    @Shadow
    public abstract NBTTagCompound getSubCompound(String display);

    @Shadow
    public abstract Item getItem();
}
