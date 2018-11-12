package com.gmail.borlandlp.minigamesdtools.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InventoryUtil {
    public static void str2ItemStack(String strItem) {

    }

    public static ItemStack getArmourItemStack(String info) {
        String[] itemInfo = info.split(",");
        String[] idDamageValueAndAmount = itemInfo[0].split("!");
        String[] idAndDamageValue = idDamageValueAndAmount[0].split(":");
        ItemStack is = new ItemStack(Integer.valueOf(idAndDamageValue[0]), Integer.valueOf(idDamageValueAndAmount[1]));
        if(itemInfo.length > 1) {
            String[] enchantments = itemInfo[1].split("!");
            String[] arr$ = enchantments;
            int len$ = enchantments.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String ench = arr$[i$];
                if(ench != null && ench.length() > 2) {
                    String[] enchantment = ench.split(":");
                    is.addUnsafeEnchantment(Enchantment.getByName(enchantment[0]), Integer.valueOf(enchantment[1]));
                }
            }
        }

        return is;
    }

    // id, amount, durability, slot, enchantments
    public static Object[] getItemStackOufOfString(String info) {
        HashMap<String, String> itemInfo = new HashMap<>();
        String enchantments = new String();
        for(String row : info.split(",")) {
            String[] splitted = row.split("=");
            if(splitted.length == 2) {
                if(splitted[0].equalsIgnoreCase("enchantments")) {
                    enchantments = splitted[1];
                } else {
                    itemInfo.put(splitted[0], splitted[1]);
                }
            }
        }

        Material material = Material.getMaterial(itemInfo.get("id").toString());
        ItemStack is = new ItemStack(material, Integer.parseInt(itemInfo.get("amount")));
        if(itemInfo.containsKey("durability")) {
            is.setDurability(Short.parseShort(itemInfo.get("durability")));
        }

        if(enchantments.length() > 0) {
            for(String row : enchantments.split("#")) {
                String[] splitted = row.split(":");
                int enchPower = (enchantments.length() > 1) ? Integer.valueOf(splitted[1]) : 1;

                is.addEnchantment(Enchantment.getByName(splitted[0]), enchPower);
            }
        }

        return new Object[]{is, itemInfo.containsKey("slot") ? Integer.parseInt(itemInfo.get("slot")) : 0};
    }
}
