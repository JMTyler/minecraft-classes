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
    
    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1)
    {
    	EntityPlayer entityplayer = (EntityPlayer)entityliving1;
    	
    	for (ItemClassArmor armor : _dependencies) {
    		if (!mod_Classes.checkDependency(entityplayer, armor)) {
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
    	for (ItemClassArmor armor : _dependencies) {
    		if (!mod_Classes.checkDependency(entityplayer, armor)) {
        		onFailedRightClick(itemstack, world, entityplayer);
        		return itemstack;
        	}
        }
    	
        onSuccessfulRightClick(itemstack, world, entityplayer);
        return itemstack;
    }
}
