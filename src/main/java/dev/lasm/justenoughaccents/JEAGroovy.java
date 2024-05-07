package dev.lasm.justenoughaccents;

import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;

public class JEAGroovy implements GroovyPlugin {
    @Override
    public String getModId() {
        return Tags.MOD_ID;
    }

    @Override
    public String getContainerName() {
        return Tags.MOD_NAME;
    }

    @Override
    public void onCompatLoaded(GroovyContainer<?> container) {

    }
}
