package dev.lasm.justenoughaccents;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.*;

@ZenClass("mods.jea.JEA")
@ZenRegister
@ModOnly(Tags.MOD_ID)
public class JEA {
    public static Map<IIngredient, Set<String>> ALIASES = new HashMap<>();

    @ZenMethod
    public static void alias(IIngredient ingredient, String alias) {
        ALIASES.computeIfAbsent(ingredient, k -> new HashSet<>()).add(alias);
    }

    @ZenMethod
    public static void aliases(IIngredient ingredient, String... aliases) {
        ALIASES.computeIfAbsent(ingredient, k -> new HashSet<>()).addAll(Arrays.asList(aliases));
    }
}
