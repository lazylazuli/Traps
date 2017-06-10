package com.github.lazylazuli.traps.item;

import com.github.lazylazuli.lib.common.item.ItemBlockDyed;
import com.github.lazylazuli.traps.block.BlockSpikeTrap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemSpikeTrap extends ItemBlockDyed
{
	public ItemSpikeTrap(BlockSpikeTrap blockSpikeTrap)
	{
		super(blockSpikeTrap);
		maxStackSize = 16;
	}
	
	@Nonnull
	@Override
	public String getHighlightTip(ItemStack item, @Nonnull String displayName)
	{
		NBTTagCompound compound = item.getTagCompound();
		
		String unloc = getUnlocalizedColor(item);
		
		if (compound != null && compound.hasKey("ToolDamage"))
		{
			int max = getToolMaterial().getMaxUses();
			int dmg = max - item.getTagCompound()
								.getInteger("ToolDamage");
			
			return I18n.translateToLocal(unloc) + " " + displayName + " (" + dmg + "/" + max + ")";
		} else
		{
			return I18n.translateToLocal(unloc) + " " + displayName;
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
			
			String unloc = getUnlocalizedColor(stack);
			tooltip.add("Color: " + I18n.translateToLocal(unloc));
			
			if (compound != null && compound.hasKey("ToolDamage"))
			{
				int max = getToolMaterial().getMaxUses();
				int dmg = max - stack.getTagCompound()
									 .getInteger("ToolDamage");
				tooltip.add("Damage: " + dmg + "/" + max + "");
			}
		}
	}
	
	private ToolMaterial getToolMaterial()
	{
		return ((BlockSpikeTrap) block).getToolMaterial();
	}
	
	@Override
	public boolean isEnchantable(@Nonnull ItemStack stack)
	{
		return stack.getItem() instanceof ItemSpikeTrap;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
	{
		return enchantment == Enchantments.BANE_OF_ARTHROPODS || enchantment == Enchantments.BLAST_PROTECTION ||
				enchantment == Enchantments.FIRE_ASPECT || enchantment == Enchantments.SHARPNESS || enchantment ==
				Enchantments.SMITE || enchantment == Enchantments.UNBREAKING;
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
}