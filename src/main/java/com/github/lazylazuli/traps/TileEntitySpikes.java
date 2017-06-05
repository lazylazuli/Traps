package com.github.lazylazuli.traps;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;

import javax.annotation.Nonnull;
import java.util.Random;

public class TileEntitySpikes extends TileEntity implements ITickable
{
	private ItemStack inventory;
	
	private int sharpness;
	
	private int fire;
	
	private int blast;
	
	private int smite;
	
	private int bane;
	
	private int damageCooldown;
	
	private int damage;
	
	int getBlastResistance()
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
	
	void initializeStack(ItemStack stack)
	{
		this.inventory = stack.copy();
		
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
	
	ItemStack getItemDropped()
	{
		ItemStack stack = inventory
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
	
	void onFallenUpon(Entity entityIn, float fallDistance)
	{
		float dmg = getDamageMultiplier(entityIn) + 1;
		
		float fall = Math.max(4, fallDistance);
		
		entityIn.fall(fall, dmg);
		damageBlock((int) fall);
	}
	
	void onEntityWalk(Entity entityIn)
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
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}
	
	private boolean attemptDamageItem(int amount, Random rand)
	{
		ItemStack stack = inventory;
		
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
		
		if (compound.hasKey("Stack"))
		{
			NBTTagCompound nbt = compound.getCompoundTag("Stack");
			inventory = ItemStack.func_77949_a(nbt);
		}
		
		initializeStack(inventory);
		
		damageCooldown = compound.getInteger("DamageCooldown");
		damage = compound.getInteger("Damage");
	}
	
	@Nonnull
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		NBTTagCompound nbt = new NBTTagCompound();
		inventory.writeToNBT(nbt);
		compound.setTag("Stack", nbt);
		
		compound.setInteger("DamageCooldown", damageCooldown);
		compound.setInteger("Damage", damage);
		
		return compound;
	}
}
