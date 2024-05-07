package dev.lasm.justenoughaccents.mixin;

import mezz.jei.plugins.vanilla.ingredients.item.ItemStackHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemStackHelper.class, remap = false)
public class MixinItemStackHelper {
    @Inject(at = @At("RETURN"), remap = false, method = "getDisplayName", cancellable = true)
    private void getDisplayName(ItemStack ingredient, CallbackInfoReturnable<String> ci) {
        var lang = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage();
        if (lang.getLanguageCode().equals("en_us")) {
        } else {
            String enDisplayName;
            NBTTagCompound nbttagcompound = ingredient.getSubCompound("display");

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

            enDisplayName = I18n.translateToFallback(ingredient.getItem().getUnlocalizedNameInefficiently(ingredient) + ".name");
            ci.setReturnValue(ingredient.getDisplayName() + " " + enDisplayName);
        }
    }
}
