package com.github.lazylazuli.traps.common.block;

import com.github.lazylazuli.lib.common.block.BlockBase;
import com.github.lazylazuli.lib.common.block.state.BlockState;
import com.github.lazylazuli.lib.common.block.state.BlockStateTile;
import com.github.lazylazuli.traps.common.tile.TileSpikeTrap;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockStone;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockSpikeTrap extends BlockDyedSlab implements ITileEntityProvider
{
	private final Item.ToolMaterial toolMaterial;
	
	public BlockSpikeTrap(Material material, String name, Item.ToolMaterial toolMaterial)
	{
		super(material, name, BlockStone.EnumType.GRANITE_SMOOTH);
		
		this.toolMaterial = toolMaterial;
	}
	
	@Override
	public BlockState createBlockState(ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn)
	{
		return new State(this, propertiesIn);
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, @Nonnull Entity exploder, Explosion explosion)
	{
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileSpikeTrap)
		{
			return blockResistance / (5 - ((TileSpikeTrap) te).getBlastResistance());
		}
		
		return getExplosionResistance(exploder);
	}
	
	// BLOCKCONTAINER
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state,
			float chance, int fortune)
	{}
	
	@Override
	public void harvestBlock(@Nonnull World worldIn, EntityPlayer player, @Nonnull BlockPos pos, @Nonnull IBlockState
			state, @Nullable TileEntity te, ItemStack stack)
	{
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		
		if (te instanceof TileSpikeTrap)
		{
			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TileSpikeTrap) te)
					.getItemDropped());
		}
	}
	
	// SPIKE METHODS
	
	public Item.ToolMaterial getToolMaterial()
	{
		return toolMaterial;
	}
	
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te != null && te instanceof TileSpikeTrap)
		{
			((TileSpikeTrap) te).onFallenUpon(entityIn, fallDistance);
		}
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te != null && te instanceof TileSpikeTrap)
		{
			((TileSpikeTrap) te).onEntityWalk(entityIn);
		}
	}
	
	// ENCHANTMENT
	
	@Nullable
	@Override
	public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
	{
		return new TileSpikeTrap();
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack
			stack)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te != null && te instanceof TileSpikeTrap)
		{
			((TileSpikeTrap) te).initializeStack(stack);
		}
	}
	
	private class State extends BlockDyedSlab.State implements BlockStateTile
	{
		public State(BlockBase blockIn,
				ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn)
		{
			super(blockIn, propertiesIn);
		}
		
		@Override
		public boolean onBlockEventReceived(World worldIn, BlockPos pos, int id, int param)
		{
			super.onBlockEventReceived(worldIn, pos, id, param);
			return BlockStateTile.super.onBlockEventReceived(worldIn, pos, id, param);
		}
	}
}
