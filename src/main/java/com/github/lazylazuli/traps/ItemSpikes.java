package com.github.lazylazuli.traps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class ItemSpikes extends ItemColored
{
	ItemSpikes(BlockSpikeTrap blockSpikeTrap)
	{
		super(blockSpikeTrap, true);
		
		setCreativeTab(CreativeTabs.COMBAT);
		setSubtypeNames(Arrays.stream(EnumDyeColor.values())
							  .map(EnumDyeColor::getUnlocalizedName)
							  .toArray(String[]::new));
		
		maxStackSize = 16;
	}
	
	@Nonnull
	@Override
	public String getHighlightTip(ItemStack item, @Nonnull String displayName)
	{
		NBTTagCompound compound = item.getTagCompound();
		
		if (compound != null && compound.hasKey("ToolDamage"))
		{
			int max = getToolMaterial().getMaxUses();
			int dmg = max - item.getTagCompound()
								.getInteger("ToolDamage");
			
			return displayName + " (" + dmg + "/" + max + ")";
		} else
		{
			return displayName;
		}
	}
	
	@Override
	public void addInformation(@Nullable ItemStack stack, @Nullable EntityPlayer playerIn, @Nullable List<String>
			tooltip, boolean advanced)
	{
		if (tooltip == null)
		{
			return;
		}
		
		if (stack != null)
		{
			NBTTagCompound compound = stack.getTagCompound();
			
			if (compound != null && compound.hasKey("ToolDamage"))
			{
				int max = getToolMaterial().getMaxUses();
				int dmg = max - stack.getTagCompound()
									 .getInteger("ToolDamage");
				tooltip.add("Damage: " + dmg + "/" + max + "");
			}
		}
		
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
	
	private ToolMaterial getToolMaterial()
	{
		return ((BlockSpikeTrap) block).getToolMaterial();
	}
	
	@Override
	public boolean isEnchantable(@Nonnull ItemStack stack)
	{
		return stack.getItem() instanceof ItemSpikes;
	}
	
	@Nonnull
	@Override
	public String getUnlocalizedName(@Nullable ItemStack stack)
	{
		String s = block.getUnlocalizedName();
		
		if (stack != null)
		{
			s += "." + EnumDyeColor.byMetadata(stack.getMetadata());
		}
		
		return s;
	}
	
	// TODO: 2017-06-05
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book)
	{
		NBTTagList list = book.getEnchantmentTagList();
		
		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound ench = (NBTTagCompound) list.get(i);
			
			int id = ench.getShort("id");
			Enchantment enchantment = Enchantment.REGISTRY.getObjectById(id);
			if (enchantment == Enchantments.BANE_OF_ARTHROPODS || enchantment == Enchantments.BLAST_PROTECTION ||
					enchantment == Enchantments.FIRE_ASPECT || enchantment == Enchantments.SHARPNESS || enchantment ==
					Enchantments.SMITE || enchantment == Enchantments.UNBREAKING)
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean canHarvestBlock(IBlockState blockIn)
	{
		return false;
	}
	
	@Override
	public int getItemEnchantability()
	{
		return getToolMaterial().getEnchantability();
	}
	
	public boolean isDamageable()
	{
		return true;
	}
}