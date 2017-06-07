package com.github.lazylazuli.traps.block;

import com.github.lazylazuli.lazylazulilib.block.BlockBase;
import com.github.lazylazuli.lazylazulilib.block.BlockDyed;
import com.github.lazylazuli.lazylazulilib.block.state.BlockState;
import com.github.lazylazuli.lazylazulilib.block.state.BlockStateTile;
import com.github.lazylazuli.traps.tile.TileEntitySpikeTrap;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
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
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockSpikeTrap extends BlockDyed implements ITileEntityProvider
{
	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	
	private final Item.ToolMaterial toolMaterial;
	
	public BlockSpikeTrap(String name, Material material, SoundType sound, Item.ToolMaterial toolMaterial)
	{
		super(material);
		
		setRegistryName(name);
		setUnlocalizedName(name);
		setHardness(1.25f);
		setResistance(7);
		setSoundType(sound);
		
		this.toolMaterial = toolMaterial;
	}
	
	@Override
	public BlockState createBlockState(ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn)
	{
		return new BlockSpikeTrapState(this, propertiesIn);
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, @Nonnull Entity exploder, Explosion explosion)
	{
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntitySpikeTrap)
		{
			return blockResistance / (5 - ((TileEntitySpikeTrap) te).getBlastResistance());
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
		
		if (te instanceof TileEntitySpikeTrap)
		{
			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TileEntitySpikeTrap) te)
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
		
		if (te != null && te instanceof TileEntitySpikeTrap)
		{
			((TileEntitySpikeTrap) te).onFallenUpon(entityIn, fallDistance);
		}
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te != null && te instanceof TileEntitySpikeTrap)
		{
			((TileEntitySpikeTrap) te).onEntityWalk(entityIn);
		}
	}
	
	// ENCHANTMENT
	
	@Nullable
	@Override
	public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
	{
		return new TileEntitySpikeTrap();
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack
			stack)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te != null && te instanceof TileEntitySpikeTrap)
		{
			((TileEntitySpikeTrap) te).initializeStack(stack);
		}
	}
	
	private class BlockSpikeTrapState extends BlockStateTile
	{
		public BlockSpikeTrapState(BlockBase blockIn,
				ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn)
		{
			super(blockIn, propertiesIn);
		}
		
		@Override
		public MapColor getMapColor()
		{
			return getValue(COLOR).getMapColor();
		}
		
		@Override
		public AxisAlignedBB getBoundingBox(IBlockAccess source, BlockPos pos)
		{
			return AABB;
		}
		
		@Override
		public boolean isFullCube()
		{
			return false;
		}
		
		@Override
		public boolean isOpaqueCube()
		{
			return false;
		}
		
		@Override
		public EnumBlockRenderType getRenderType()
		{
			return EnumBlockRenderType.MODEL;
		}
		
		@Override
		public boolean onBlockEventReceived(World worldIn, BlockPos pos, int id, int param)
		{
			super.onBlockEventReceived(worldIn, pos, id, param);
			
			TileEntity tileentity = worldIn.getTileEntity(pos);
			
			return tileentity != null && tileentity.receiveClientEvent(id, param);
		}
	}
}
