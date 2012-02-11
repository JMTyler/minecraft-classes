package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.lwjgl.opengl.GL11;

public class mod_Classes extends BaseMod
{
	protected static int _nextBlockId = 110;
	protected static int _nextItemId = 113;
	
	protected static HashMap<String, Integer> _armorGroups = new HashMap<String, Integer>();

	protected static HashMap<String, ItemClassArmor> _armors = new HashMap<String, ItemClassArmor>();
	protected static HashMap<String, ItemClassWeapon> _weapons = new HashMap<String, ItemClassWeapon>();
	
	protected static Logger _logger;
	
	/**
	 * Adds a new Armor Group to the game.  Leather, Steel, Diamond, etc.
	 * @param armorGroup Name of the Armor Group (used to pick the texture file)
	 * @return whether or not the specified Armor Group name already exists
	 */
	public static boolean addArmorGroup(String armorGroup)
	{
		int i = ModLoader.AddArmor(armorGroup);
		if (i != -1) {
			_armorGroups.put(armorGroup, i);
		}
		return i != -1;
	}
	
	/**
	 * Gets the numeric ID used to reference the Armor Group throughout core Minecraft code
	 * @param armorGroup Name of the Armor Group specified in addArmorGroup method
	 * @return
	 */
	public static int getArmorGroupId(String armorGroup)
	{
		Integer i = _armorGroups.get(armorGroup);
		return (i == null) ? -1 : i;
	}
	
	/**
	 * Adds a new piece of armor to the game.
	 * @param armorName Name of the new piece of armor (e.g. "helmetDiamond")
	 * @param armor The armor object itself
	 * @return The armor object on success, or null if a piece of armor already exists by this name
	 */
	public static ItemClassArmor addArmor(String armorName, ItemClassArmor armor)
	{
		if (_armors.containsKey(armorName)) {
			return null;
		}
		
		armor.setItemName(armorName);
		_armors.put(armorName, armor);
		return armor;
	}
	
	/**
	 * Adds a new weapon to the game.
	 * @param weaponName Name of the new weapon (e.g. "swordDiamond")
	 * @param weapon The weapon object itself
	 * @return The weapon object on success, or null if a weapon already exists by this name
	 */
	public static ItemClassWeapon addWeapon(String weaponName, ItemClassWeapon weapon)
	{
		if (_weapons.containsKey(weaponName)) {
			return null;
		}
		
		weapon.setItemName(weaponName);
		_weapons.put(weaponName, weapon);
		return weapon;
	}
	
	/**
	 * Performs a check against the specified player's inventory to see if they are wearing the specified armor.
	 * TODO: checking against a different class' armor should go here, somehow
	 * @param entityplayer The player whose inventory to check
	 * @param armorSought The armor which you want to search for on the player's person
	 * @return Whether or not the player is wearing the armor
	 */
	public static boolean checkDependency(EntityPlayer entityplayer, ItemClassArmor armorSought)
	{
		ItemStack armorFound = entityplayer.inventory.armorItemInSlot(armorSought.getIntendedSlot());
    	return armorFound != null && armorFound.getItemName() == armorSought.getItemName();
	}
	
	/**
	 * Gets a (roughly) unique block ID.  Assumes no other mods will try to hard-code their own block IDs AFTER this method
	 * is called, which is unlikely in the wild, as it depends on the alphabetic ordering of the mod names.
	 * @return a unique block ID
	 */
	public static int getUniqueBlockId()
	{
		while (Block.blocksList[_nextBlockId] != null) {
			_nextBlockId++;
			if (_nextBlockId >= 256) {
				_logger.severe("You have exceeded the allowed limit of block types! (256 allowed)");
			}
		}
		
		return _nextBlockId++;
	}
	
	/**
	 * Gets a (roughly) unique item ID.  Assumes no other mods will try to hard-code their own item IDs AFTER this method
	 * is called, which is unlikely in the wild, as it depends on the alphabetic ordering of the mod names.
	 * @return a unique item ID
	 */
	public static int getUniqueItemId()
	{
		while (Item.itemsList[256 + _nextItemId] != null) {
			_nextItemId++;
			if ((256 + _nextItemId) >= 32000) {
				_logger.severe("You have exceeded the allowed limit of item types! (32000 allowed)");
			}
		}
		
		return _nextItemId++;
	}
	
	/**
	 * Gets a (roughly) unique entity ID.  Assumes all other mods are using ModLoader to retrieve their unique entity IDs.
	 * Given such a scenario, it will work every time.
	 * @return a unique entity ID
	 */
	public static int getUniqueEntityId()
	{
		return ModLoader.getUniqueEntityId();
	}
	
	static
	{
		_logger = Logger.getLogger("ModLoader");
	}
	
	@Override
	public String Version()
	{
		return null;
	}
}
