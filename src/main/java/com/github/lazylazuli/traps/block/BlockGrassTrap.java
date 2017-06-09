package com.github.lazylazuli.traps.block;

import com.github.lazylazuli.lazylazulilib.block.BlockBase;
import com.github.lazylazuli.lazylazulilib.block.state.BlockState;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import static net.minecraft.block.BlockGrass.SNOWY;

public class BlockGrassTrap extends BlockBase implements IGrowable
{
	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.875D, 0.0D, 1.0D, 1.0D, 1.0D);
	
	public BlockGrassTrap(Material material, String name)
	{
		super(material, name);
		
		setDefaultState(blockState.getBaseState()
								  .withProperty(SNOWY, Boolean.FALSE));
		setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
		{
			if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up())
																	  .getLightOpacity(worldIn, pos.up()) > 2)
			{
				worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
			} else
			{
				if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
				{
					for (int i = 0; i < 4; ++i)
					{
						BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
						
						if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos))
						{
							return;
						}
						
						IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
						IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
						
						if (iblockstate1.getBlock() == Blocks.DIRT && iblockstate1.getValue(BlockDirt.VARIANT) ==
								BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(
								blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2)
						{
							worldIn.setBlockState(blockpos, Blocks.GRASS.getDefaultState());
						}
					}
				}
			}
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Blocks.DIRT.getItemDropped(
				Blocks.DIRT.getDefaultState()
						   .withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
	}
	
	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
	}
	
	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		BlockPos blockpos = pos.up();
		
		for (int i = 0; i < 128; ++i)
		{
			BlockPos blockpos1 = blockpos;
			int j = 0;
			
			while (true)
			{
				if (j >= i / 16)
				{
					if (worldIn.isAirBlock(blockpos1))
					{
						if (rand.nextInt(8) == 0)
						{
							worldIn.getBiome(blockpos1)
								   .plantFlower(worldIn, rand, blockpos1);
						} else
						{
							IBlockState iblockstate1 = Blocks.TALLGRASS.getDefaultState()
																	   .withProperty(
																			   BlockTallGrass.TYPE,
																			   BlockTallGrass.EnumType.GRASS
																	   );
							
							if (Blocks.TALLGRASS.canBlockStay(worldIn, blockpos1, iblockstate1))
							{
								worldIn.setBlockState(blockpos1, iblockstate1, 3);
							}
						}
					}
					
					break;
				}
				
				blockpos1 = blockpos1.add(
						rand.nextInt(3) - 1,
						(rand.nextInt(3) - 1) * rand.nextInt(3) / 2,
						rand.nextInt(3) - 1
				);
				
				if (worldIn.getBlockState(blockpos1.down())
						   .getBlock() != Blocks.GRASS || worldIn.getBlockState(blockpos1)
																 .isNormalCube())
				{
					break;
				}
				
				++j;
			}
		}
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
