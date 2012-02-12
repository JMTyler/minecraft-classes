// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Vector;


// Referenced classes of package net.minecraft.src:
//            Item, EntityPig, ItemStack, EntityLiving

public abstract class ItemClassWeapon extends Item
{
	protected Vector<ItemClassArmor> _dependencies = new Vector<ItemClassArmor>();
	
    public ItemClassWeapon(int itemId)
    {
        this(itemId, 1);
    }
    
    public ItemClassWeapon(int itemId, int maxStackSize)
    {
    	super(itemId);
    	this.maxStackSize = maxStackSize;
    }
    
    /**
     * Sets a dependency for this weapon on the specified piece of armor.
     * @param armor The piece of armor that must be worn for this weapon to be used
     * @return this
     */
    public ItemClassWeapon dependsOn(ItemClassArmor armor)
    {
    	_dependencies.add(armor);
    	return this;
    }
    
    /**
     * This callback will fire when the weapon is used to attack an entity and the player is wearing all dependent armor.
     * TODO: absolutely must be tweaked, as one should be able to smack something with a weapon (sometimes) even if one
     * may not utilize the weapon's special abilities
     * TODO: maybe it doesn't need to be tweaked, maybe we just need to stuff the same code in both on*Attack methods
     * @param itemstack This item
     * @param entityliving The entity being attacked
     * @param entityplayer The player doing the attacking
     */
    public void onSuccessfulAttack(ItemStack itemstack, EntityLiving entityliving, EntityPlayer entityplayer)
    {
    	return;
    }
    
    /**
     * This callback will fire when the player right-clicks with this weapon in-hand, and they are wearing all dependent
     * armor.
     * @param itemstack This item
     * @param world THE WORLD
     * @param entityplayer The player attempting to use the weapon
     */
    public void onSuccessfulRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	return;
    }
    
    /**
     * This callback will fire when the player attempts to use this weapon (right-click?), and they are wearing all dependent
     * armor.
     * @param itemstack This item
     * @param entityplayer The player attempting to use the weapon
     * @param world THE WORLD
     * @param i X coordinate of the block they've used it on
     * @param j Y coordinate of the block they've used it on
     * @param k Z coordinate of the block they've used it on
     * @param l I'm actually not sure, some kind of metadata?  Scott?
     */
    public boolean onSuccessfulUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
    	return true;
    }
    
    /**
     * This callback will fire when the weapon is used to attack an entity and the player is NOT wearing all dependent armor.
     * Note: the weapon WILL AUTOMATICALLY FAIL to harm the entity.
     * @param itemstack This item
     * @param entityliving The entity being attacked
     * @param entityplayer The player doing the attacking
     */
    public void onFailedAttack(ItemStack itemstack, EntityLiving entityliving, EntityPlayer entityplayer)
    {
    	return;
    }
    
    /**
     * This callback will fire when the player right-clicks with this weapon in-hand, and they are NOT wearing all dependent
     * armor.  Perhaps you want to harm the player here?
     * @param itemstack This item
     * @param world THE WORLD
     * @param entityplayer The player attempting to use the weapon
     */
    public void onFailedRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	return;
    }
    
    /**
     * This callback will fire when the player attempts to use this weapon (right-click?), and they are NOT wearing all dependent
     * armor.  Perhaps you want to harm the player here?
     * @param itemstack This item
     * @param entityplayer The player attempting to use the weapon
     * @param world THE WORLD
     * @param i X coordinate of the block they've used it on
     * @param j Y coordinate of the block they've used it on
     * @param k Z coordinate of the block they've used it on
     * @param l I'm actually not sure, some kind of metadata?  Scott?
     */
    public boolean onFailedUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
    	return false;
    }
    
    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1)
    {
    	EntityPlayer entityplayer = (EntityPlayer)entityliving1;
    	
    	int renderIndex = -1;
    	for (ItemClassArmor armor : _dependencies) {
    		renderIndex = armor.renderIndex;
    		if (!mod_Classes.checkDependency(entityplayer, armor)) {
        		onFailedAttack(itemstack, entityliving, entityplayer);
        		return false;
        	}
        }
		
		for (int i = 0; i < 4; i++) {
			ItemStack armorSlot = entityplayer.inventory.armorItemInSlot(i);
			// player is wearing another class' armor
			if (armorSlot != null && armorSlot.getItem() instanceof ItemClassArmor && ((ItemArmor)armorSlot.getItem()).renderIndex != renderIndex) {
				onFailedAttack(itemstack, entityliving, entityplayer);
				return false;
			}
		}
    	
        onSuccessfulAttack(itemstack, entityliving, entityplayer);
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	int renderIndex = -1;
    	for (ItemClassArmor armor : _dependencies) {
    		renderIndex = armor.renderIndex;
    		if (!mod_Classes.checkDependency(entityplayer, armor)) {
        		onFailedRightClick(itemstack, world, entityplayer);
        		return itemstack;
        	}
        }
		
		for (int i = 0; i < 4; i++) {
			ItemStack armorSlot = entityplayer.inventory.armorItemInSlot(i);
			// player is wearing another class' armor
			if (armorSlot != null && armorSlot.getItem() instanceof ItemClassArmor && ((ItemArmor)armorSlot.getItem()).renderIndex != renderIndex) {
				onFailedRightClick(itemstack, world, entityplayer);
				return itemstack;
			}
		}
    	
        onSuccessfulRightClick(itemstack, world, entityplayer);
        return itemstack;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l)
    {
    	int renderIndex = -1;
    	for (ItemClassArmor armor : _dependencies) {
    		renderIndex = armor.renderIndex;
    		if (!mod_Classes.checkDependency(entityplayer, armor)) {
        		return onFailedUse(itemstack, entityplayer, world, i, j, k, l);
        	}
        }
		
		for (int m = 0; m < 4; m++) {
			ItemStack armorSlot = entityplayer.inventory.armorItemInSlot(m);
			// player is wearing another class' armor
			if (armorSlot != null && armorSlot.getItem() instanceof ItemClassArmor && ((ItemArmor)armorSlot.getItem()).renderIndex != renderIndex) {
				return onFailedUse(itemstack, entityplayer, world, i, j, k, l);
			}
		}
    	
        return onSuccessfulUse(itemstack, entityplayer, world, i, j, k, l);
    }
}
