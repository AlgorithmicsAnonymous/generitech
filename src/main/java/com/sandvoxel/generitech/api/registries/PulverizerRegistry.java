package com.sandvoxel.generitech.api.registries;

import com.sandvoxel.generitech.api.util.Crushable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class PulverizerRegistry {
    private static List<Crushable> registry = new ArrayList<>();

    public static void register(ItemStack input, ItemStack output, float chance) {
        registry.add(new Crushable(input, output, chance));
    }

    public static List<Crushable> getOutputs(ItemStack itemStack) {
        List<Crushable> outputList = new ArrayList<>();

        for (Crushable crushable : registry)
            if (crushable.input.isItemEqual(itemStack) && crushable.output != null)
                outputList.add(crushable);

        return outputList;
    }

    public static boolean containsInput(ItemStack itemStack) {
        for (Crushable crushable : registry)
            if (itemStack.isItemEqual(crushable.input))
                return true;

        return false;
    }

    public static void registerOreDictOre(String oreName) {
        List<ItemStack> itemOres = OreDictionary.getOres("ore" + oreName);
        List<ItemStack> itemDusts = OreDictionary.getOres("dust" + oreName);

        if (itemOres.isEmpty() || itemDusts.isEmpty())
            return;

        for (ItemStack input : itemOres) {
            ItemStack output = itemDusts.get(0);
            register(input, output, 1.0f);
        }
    }

    public static void registerOreDictIngot(String ingotName) {
        List<ItemStack> itemOres = OreDictionary.getOres("ingot" + ingotName);
        List<ItemStack> itemDusts = OreDictionary.getOres("dust" + ingotName);

        if (itemOres.isEmpty() || itemDusts.isEmpty())
            return;

        for (ItemStack input : itemOres) {
            ItemStack output = itemDusts.get(0);
            register(input, output, 0f);
        }
    }
}
