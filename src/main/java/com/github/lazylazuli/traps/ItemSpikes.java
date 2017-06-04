package com.github.lazylazuli.traps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class ItemSpikes extends ItemColored
{
	public ItemSpikes(BlockSpikes blockSpikes)
	{
		super(blockSpikes, true);
		setSubtypeNames(Arrays.stream(EnumDyeColor.values())
							  .map(EnumDyeColor::getUnlocalizedName)
							  .toArray(String[]::new));
		setCreativeTab(CreativeTabs.COMBAT);
		maxStackSize = 16;
	}
	
	@Override
	public String getHighlightTip(ItemStack item, String displayName)
	{
		if (item.hasTagCompound() && item.getTagCompound()
										 .hasKey("ToolDamage"))
		{
			int max = getToolMaterial().getMaxUses();
			int dmg = max - item.getTagCompound()
								.getInteger("ToolDamage");
			return displayName + " (" + dmg + "/" + max + ")";
		}
		return displayName;
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		if (stack.hasTagCompound() && stack.getTagCompound()
										   .hasKey("ToolDamage"))
		{
			int max = getToolMaterial().getMaxUses();
			int dmg = max - stack.getTagCompound()
								 .getInteger("ToolDamage");
			tooltip.add("Damage: " + dmg + "/" + max + "");
		}
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
	
	public ToolMaterial getToolMaterial()
	{
		return ((BlockSpikes) block).getToolMaterial();
	}
	
	public boolean isEnchantable(ItemStack stack)
	{
		return stack.getItem() instanceof ItemSpikes;
	}
	
	public String getUnlocalizedName(ItemStack stack)
	{
		return block.getUnlocalizedName() + "." + EnumDyeColor.byMetadata(stack.getMetadata());
	}
	
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
	{
		return enchantment == Enchantments.BANE_OF_ARTHROPODS || enchantment == Enchantments.BLAST_PROTECTION ||
				enchantment == Enchantments.FIRE_ASPECT || enchantment == Enchantments.SHARPNESS || enchantment ==
				Enchantments.SMITE || enchantment == Enchantments.UNBREAKING;
	}
	
	public boolean canHarvestBlock(IBlockState blockIn)
	{
		return false;
	}
	
	public int getItemEnchantability()
	{
		return ((BlockSpikes) block).getToolMaterial()
									.getEnchantability();
	}
}