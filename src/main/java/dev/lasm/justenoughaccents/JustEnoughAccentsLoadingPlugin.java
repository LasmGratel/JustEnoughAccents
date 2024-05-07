package dev.lasm.justenoughaccents;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

public class JustEnoughAccentsLoadingPlugin implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins." + Tags.MOD_ID + ".json");
    }
}
