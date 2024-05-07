package dev.lasm.justenoughaccents;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.runtime.ScriptLoader;
import gregtech.api.items.metaitem.MetaOreDictItem;
import gregtech.api.unification.OreDictUnifier;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import mezz.jei.config.Config;
import mezz.jei.gui.ingredients.IIngredientListElement;
import mezz.jei.search.LimitedStringStorage;
import mezz.jei.search.PrefixInfo;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION, clientSideOnly = true, dependencies = "required-after: jei; after: gregtech")
public class JustEnoughAccents {
    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    private ScriptLoader loader;


    private static final int SMALL_DOWN_NUMBER_BASE = '\u2080';

    private static String convert(String string, int base) {
        boolean hasPrecedingDash = false;
        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            boolean isDash = c == '-';
            if (isDash) hasPrecedingDash = true;
            int relativeIndex = c - SMALL_DOWN_NUMBER_BASE;
            if (relativeIndex >= 0 && relativeIndex <= 9) {
                if (!hasPrecedingDash) {
                    // no preceding dash, so convert the char
                    charArray[i] = (char) (base + relativeIndex);
                }
            } else if (!isDash && hasPrecedingDash) {
                // was a non-number, so invalidate the previously seen dash
                hasPrecedingDash = false;
            }
        }
        return new String(charArray);
    }
    public static Collection<String> getAliasForIngredient(IIngredientListElement<?> element) {
        if (element.getIngredient() instanceof ItemStack stack) {
            return JEA.ALIASES.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().contains(CraftTweakerMC.getIItemStackForMatching(stack)))
                    .flatMap(entry -> entry.getValue().stream())
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    public static Collection<String> getFormula(IIngredientListElement<?> element) {
        try {
            if (element.getIngredient() instanceof ItemStack stack) {
                if (stack.getItem() instanceof MetaOreDictItem oreDictItem) {
                    Optional<String> oreDictName = OreDictUnifier.getOreDictionaryNames(stack).stream().findFirst();
                    if (oreDictName.isPresent() && oreDictItem.OREDICT_TO_FORMULA.containsKey(oreDictName.get()) &&
                            !oreDictItem.OREDICT_TO_FORMULA.get(oreDictName.get()).isEmpty()) {
                        var formula = oreDictItem.OREDICT_TO_FORMULA.get(oreDictName.get()).toLowerCase().trim();
                        var formula1 = convert(formula, '0');
                        var formula2 = formula1.replaceAll("\\(", "").replaceAll("\\)", "").trim();
                        return new HashSet<>(Arrays.asList(formula, formula1, formula2));
                    }
                } else {
                    var entry = OreDictUnifier.getUnificationEntry(stack);
                    if (entry != null && entry.material != null && entry.material.getChemicalFormula() != null) {
                        var formula = entry.material.getChemicalFormula().toLowerCase().trim();
                        var formula1 = convert(formula, '0');
                        var formula2 = formula1.replaceAll("\\(", "").replaceAll("\\)", "").trim();
                        return new HashSet<>(Arrays.asList(formula, formula1, formula2));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Unable to process chemical formula", e);
        }

        return Collections.emptyList();
    }

    public JustEnoughAccents() {
        loader = CraftTweakerAPI.tweaker.getOrCreateLoader("jea");
    }

    @Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        System.out.println("666666666");
        try {
            var instanceField = PrefixInfo.class.getDeclaredField("instances");
            instanceField.setAccessible(true);
            var instances = (Char2ObjectMap<PrefixInfo>) instanceField.get(null);

            System.out.println(instances);
            instances.put('>', new PrefixInfo('>', 6, true, "aliases", () -> Config.SearchMode.ENABLED,
                    JustEnoughAccents::getAliasForIngredient, LimitedStringStorage::new));

            instances.put('/', new PrefixInfo('/', 7, true, "formula", () -> Config.SearchMode.REQUIRE_PREFIX,
                    JustEnoughAccents::getFormula, LimitedStringStorage::new));
        } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
            throw new RuntimeException(e);
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Hello From {}!", Tags.MOD_NAME);

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CraftTweakerAPI.tweaker.loadScript(false, loader);

    }
}
