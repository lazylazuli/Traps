package com.github.lazylazuli.traps.common.block;

import com.github.lazylazuli.lib.common.block.BlockBase;
import com.github.lazylazuli.lib.common.block.state.BlockState;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import static net.minecraft.block.BlockGrass.SNOWY;

public class BlockGrassTrap extends BlockBase
{
	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.875D, 0.0D, 1.0D, 1.0D, 1.0D);
	
	public BlockGrassTrap(Material material, String name)
	{
		super(material, name);
		
		setDefaultState(blockState.getBaseState()
								  .withProperty(SNOWY, false));
	}
	
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
	{
		breakTrap(worldIn, pos);
		breakSurroundingTraps(worldIn, pos);
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	{
		if (!entityIn.isSneaking())
		{
			breakTrap(worldIn, pos);
			breakSurroundingTraps(worldIn, pos);
		}
	}
	
	public void breakTrap(World worldIn, BlockPos pos)
	{
		worldIn.setBlockToAir(pos);
		worldIn.getEntitiesWithinAABB(
				EntityPlayer.class,
				new AxisAlignedBB(
						pos.getX() - 10,
						pos.getY() - 10,
						pos.getZ() - 10,
						pos.getX() + 10,
						pos.getY() + 10,
						pos.getZ() + 10
				)
		)
			   .forEach(e -> playSound(worldIn, pos, e));
	}
	
	private void breakSurroundingTraps(World worldIn, BlockPos pos)
	{
		for (int i = -1; i < 2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				BlockPos pos1 = new BlockPos(
						pos.getX() + i,
						pos.getY(),
						pos.getZ() + j
				);
				IBlockState state = worldIn.getBlockState(pos1);
				
				if (state.getBlock() == this)
				{
					((BlockGrassTrap) state.getBlock()).breakTrap(worldIn, pos1);
				}
			}
		}
	}
	
	private void playSound(World worldIn, BlockPos pos, EntityPlayer entityPlayer)
	{
		worldIn.playSound(
				entityPlayer, pos,
				SoundEvents.BLOCK_GRASS_BREAK,
				SoundCategory.BLOCKS,
				0.3F,
				0.6F
		);
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		for (EnumFacing side : EnumFacing.HORIZONTALS)
		{
			if (canPlaceBlockOnSide(worldIn, pos, side))
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		if (side == EnumFacing.UP || side == EnumFacing.DOWN)
		{
			return false;
		}
		
		side = side.getOpposite();
		IBlockState state = worldIn.getBlockState(new BlockPos(
				pos.getX() + side.getFrontOffsetX(),
				pos.getY() + side.getFrontOffsetY(),
				pos.getZ() + side.getFrontOffsetZ()
		));
		
		return state.getBlock() == Blocks.GRASS;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
	
	@Override
	protected IProperty<?>[] getProperties()
	{
		return new IProperty[] { SNOWY };
	}
	
	@Override
	public BlockState createBlockState(ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn)
	{
		return new BlockGrassTrapState(this, propertiesIn);
	}
	
	public class BlockGrassTrapState extends BlockState
	{
		BlockGrassTrapState(BlockBase blockIn, ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn)
		{
			super(blockIn, propertiesIn);
		}
		
		@Override
		public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
		{
			return side == EnumFacing.UP;
		}
		
		@Override
		public AxisAlignedBB getBoundingBox(IBlockAccess blockAccess, BlockPos pos)
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
		public IBlockState getActualState(IBlockAccess worldIn, BlockPos pos)
		{
			Block block = worldIn.getBlockState(pos.up())
								 .getBlock();
			return withProperty(SNOWY, block == Blocks.SNOW || block == Blocks.SNOW_LAYER);
		}
	}
}
