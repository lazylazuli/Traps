package com.github.lazylazuli.traps.tile;

import com.github.lazylazuli.traps.block.BlockSpikeTrap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.Random;

import static com.github.lazylazuli.traps.TrapObjects.SMOOTH_GRANITE_SLAB;

public class TileEntitySpikeTrap extends TileEntity implements ITickable
{
	private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
	
	private int sharpness;
	
	private int fire;
	
	private int blast;
	
	private int smite;
	
	private int bane;
	
	private int damageCooldown;
	
	private int damage;
	
	public int getBlastResistance()
	{
		return blast;
	}
	
	@Override
	public void update()
	{
		if (damageCooldown > 0)
		{
			damageCooldown--;
		}
	}
	
	public void initializeStack(ItemStack stack)
	{
		inventory.set(0, stack.copy());
		
		NBTTagCompound compound = stack.getTagCompound();
		
		if (compound == null)
		{
			return;
		}
		
		if (compound.hasKey("ToolDamage"))
		{
			damage = stack.getTagCompound()
						  .getInteger("ToolDamage");
		}
		
		NBTTagList list = stack.getEnchantmentTagList();
		
		if (list == null)
		{
			return;
		}
		
		for (int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound ench = (NBTTagCompound) list.get(i);
			
			int id = ench.getShort("id");
			int lvl = ench.getShort("lvl");
			
			if (Enchantment.REGISTRY.getIDForObject(Enchantments.SHARPNESS) == id)
			{
				sharpness = lvl;
			} else if (Enchantment.REGISTRY.getIDForObject(Enchantments.FIRE_ASPECT) == id)
			{
				fire = lvl;
			} else if (Enchantment.REGISTRY.getIDForObject(Enchantments.BLAST_PROTECTION) == id)
			{
				blast = lvl;
			} else if (Enchantment.REGISTRY.getIDForObject(Enchantments.BANE_OF_ARTHROPODS) == id)
			{
				bane = lvl;
			} else if (Enchantment.REGISTRY.getIDForObject(Enchantments.SMITE) == id)
			{
				smite = lvl;
			}
		}
	}
	
	public ItemStack getItemDropped()
	{
		ItemStack stack = inventory.get(0)
								   .copy();
		
		if (damage > 0)
		{
			NBTTagCompound compound = stack.getTagCompound();
			
			if (compound == null)
			{
				compound = new NBTTagCompound();
				stack.setTagCompound(compound);
			}
			
			compound.setInteger("ToolDamage", damage);
		}
		
		return stack;
	}
	
	private Item.ToolMaterial getToolMaterial()
	{
		return ((BlockSpikeTrap) getBlockType()).getToolMaterial();
	}
	
	private float getDamageMultiplier(Entity entityIn)
	{
		float dmg = getToolMaterial().getDamageVsEntity() + 1;
		
		dmg += sharpness;
		
		if (entityIn instanceof EntityMob)
		{
			switch (((EntityMob) entityIn).getCreatureAttribute())
			{
			case UNDEAD:
				dmg += smite;
				break;
			case ARTHROPOD:
				dmg += bane;
				break;
			}
		}
		
		return dmg;
	}
	
	public void onFallenUpon(Entity entityIn, float fallDistance)
	{
		float dmg = getDamageMultiplier(entityIn) + 1;
		
		float fall = Math.max(4, fallDistance);
		
		entityIn.fall(fall, dmg);
		damageBlock((int) fall);
	}
	
	public void onEntityWalk(Entity entityIn)
	{
		if (!entityIn.isSneaking())
		{
			entityIn.attackEntityFrom(DamageSource.CACTUS, getDamageMultiplier(entityIn));
			
			if (!world.isRaining())
			{
				entityIn.setFire(((int) getToolMaterial().getDamageVsEntity() + 1) * fire);
			}
			
			damageBlock(1);
		}
	}
	
	private void damageBlock(int dmg)
	{
		if (damageCooldown > 0)
		{
			return;
		}
		
		boolean isBroken = attemptDamageItem(dmg, world.rand);
		
		damageCooldown = 8;
		markDirty();
		
		if (isBroken)
		{
			world.setBlockState(
					pos,
					SMOOTH_GRANITE_SLAB.getDefaultState()
									   .withProperty(
											   BlockSpikeTrap.COLOR,
											   EnumDyeColor.byMetadata(getBlockMetadata())
									   )
			);
		}
	}
	
	private boolean attemptDamageItem(int amount, Random rand)
	{
		ItemStack stack = inventory.get(0);
		
		int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
		int j = 0;
		
		for (int k = 0; i > 0 && k < amount; ++k)
		{
			if (EnchantmentDurability.negateDamage(stack, i, rand))
			{
				++j;
			}
		}
		
		amount -= j;
		
		if (amount <= 0)
		{
			return false;
		}
		
		damage += amount;
		return damage > getToolMaterial().getMaxUses();
	}
	
	// NBT
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		
		ItemStackHelper.loadAllItems(compound, inventory);
		
		initializeStack(inventory.get(0));
		
		damageCooldown = compound.getInteger("DamageCooldown");
		damage = compound.getInteger("Damage");
	}
	
	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		ItemStackHelper.saveAllItems(compound, inventory);
		
		compound.setInteger("DamageCooldown", damageCooldown);
		compound.setInteger("Damage", damage);
		
		return compound;
	}
}
