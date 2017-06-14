package com.github.lazylazuli.traps.common.item;

import com.github.lazylazuli.lib.common.item.ItemBlockDyed;
import com.github.lazylazuli.traps.common.block.BlockSpikeTrap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemSpikeTrap extends ItemBlockDyed
{
	public ItemSpikeTrap(BlockSpikeTrap blockSpikeTrap)
	{
		super(blockSpikeTrap);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World playerIn, List<String> tooltip, ITooltipFlag advanced)
	{
		int dmg = getToolDamage(stack);
		
		if (dmg > 0)
		{
			int max = getToolMaterial().getMaxUses();
			int remaining = max - dmg;
			tooltip.add(String.format("Damage: (%s/%s)", remaining, max));
		}
	}
	
	public int getToolDamage(ItemStack stack)
	{
		NBTTagCompound compound = stack.getTagCompound();
		
		if (compound != null && compound.hasKey("ToolDamage"))
		{
			return stack.getTagCompound()
						.getInteger("ToolDamage");
		} else
		{
			return 0;
		}
	}
	
	private ToolMaterial getToolMaterial()
	{
		return ((BlockSpikeTrap) block).getToolMaterial();
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack)
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