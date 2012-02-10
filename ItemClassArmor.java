// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.HashMap;


// Referenced classes of package net.minecraft.src:
//            Item

public class ItemClassArmor extends ItemArmor
{
	/**
	 * @param itemId A unique ID for this item
	 * @param armorGroup The Armor Group into which this item fits ("cloth", "iron", "diamond", "rogue", etc.)
	 * @param armorType The type of armor; where it goes on one's body (Helmet, Legs, etc.)
	 * @param defenseScore How effective the armor is (where leather == 0 and diamond == 3)
	 */
	public ItemClassArmor(int itemId, String armorGroup, ArmorType armorType, int defenseScore)
    {
        this(itemId, armorGroup, armorType, defenseScore, 1);
    }
    
	/**
	 * @param itemId A unique ID for this item
	 * @param armorGroup The Armor Group into which this item fits ("cloth", "iron", "diamond", "rogue", etc.)
	 * @param armorType The type of armor; where it goes on one's body (Helmet, Legs, etc.)
	 * @param defenseScore How effective the armor is (where leather == 0 and diamond == 3)
	 * @param maxStackSize The max number of items that can fit in one stack
	 */
    public ItemClassArmor(int itemId, String armorGroup, ArmorType armorType, int defenseScore, int maxStackSize)
    {
        super(itemId, defenseScore, mod_Classes.getArmorGroupId(armorGroup), _getArmorType(armorType));
        setIconCoord(mod_Classes.getArmorGroupId(armorGroup), _getArmorType(armorType));
        this.maxStackSize = maxStackSize;
    }
    
    /**
     * Gets the armor slot this piece of armor should fit into
     */
    public int getIntendedSlot()
    {
    	return Math.abs(armorType - 3);
    }
    
    /**
     * Gets the numeric ID of the Armor Type that is accepted by core Minecraft code
     */
    protected static int _getArmorType(ArmorType armorType)
    {
    	switch(armorType) {
	    	case Helmet:
	    		return 0;
	    	case Plate:
	    		return 1;
	    	case Legs:
	    		return 2;
	    	case Boots:
	    		return 3;
    	}
    	
    	return -1;
    }
}
