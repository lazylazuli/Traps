package com.github.lazylazuli.traps.common.block;

import com.github.lazylazuli.lib.common.block.BlockBase;
import com.github.lazylazuli.lib.common.block.state.BlockState;
import com.github.lazylazuli.lib.common.block.state.BlockStateTile;
import com.github.lazylazuli.traps.common.tile.TileSpikeTrap;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class BlockSpikeTrap extends BlockDyedSlab implements ITileEntityProvider
{
	private final Item.ToolMaterial toolMaterial;
	
	private final Supplier<IBlockState> activeState;
	private final Supplier<IBlockState> passiveState;
	
	public BlockSpikeTrap(Material material, String name, Item.ToolMaterial toolMaterial, Supplier<IBlockState>
			activeState, Supplier<IBlockState> passiveState)
	{
		super(material, name, BlockStone.EnumType.GRANITE_SMOOTH);
		
		this.toolMaterial = toolMaterial;
		this.activeState = activeState;
		this.passiveState = passiveState;
	}
	
	private boolean isActive()
	{
		return activeState.get()
						  .getBlock() == this;
	}
	
	private IBlockState getActiveState()
	{
		return activeState.get();
	}
	
	private IBlockState getPassiveState()
	{
		return passiveState.get();
	}
	
	@Override
	public boolean isAssociatedBlock(Block other)
	{
		return other == getActiveState().getBlock() || other == getPassiveState().getBlock();
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
			return blockResistance / (5.0F - ((TileSpikeTrap) te).getBlastResistance());
		}
		
		return blockResistance / 5.0F;
	}
	
	// SPIKE METHODS
	
	public Item.ToolMaterial getToolMaterial()
	{
		return toolMaterial;
	}
	
	@Override
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
	{
		if (!isActive())
		{
			return;
		}
		
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te != null && te instanceof TileSpikeTrap)
		{
			((TileSpikeTrap) te).onFallenUpon(entityIn, fallDistance);
		}
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	{
		if (!isActive())
		{
			return;
		}
		
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te != null && te instanceof TileSpikeTrap)
		{
			((TileSpikeTrap) te).onEntityWalk(entityIn);
		}
	}
	
	// TILE ENTITY
	
	@Override
	public TileEntity createNewTileEntity(@Nullable World worldIn, int meta)
	{
		return new TileSpikeTrap();
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		// overridden to keep from breaking tile entity
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
		
		worldIn.addBlockEvent(pos, this, 1, 0);
	}
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, float
			chance, int fortune)
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
	
	private class State extends BlockDyedSlab.State implements BlockStateTile
	{
		public State(BlockBase blockIn, ImmutableMap<IProperty<?>, Comparable<?>> propertiesIn)
		{
			super(blockIn, propertiesIn);
		}
		
		@Override
		public void neighborChanged(World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
		{
			worldIn.addBlockEvent(pos, getBlock(), 1, 0);
		}
		
		public void updateState(World worldIn, List<BlockPos> network, EnumDyeColor color)
		{
			network = new ArrayList<>(network);
			boolean flag = hasPowerSource(worldIn, network, color);
			
			network.removeIf(e -> worldIn.getBlockState(e)
										 .getValue(COLOR) != color);
			network.removeIf(e -> !isActive(worldIn.getBlockState(e)) == flag);
			network.forEach(e -> switchState(worldIn, e));
		}
		
		@Override
		public boolean onBlockEventReceived(World worldIn, BlockPos pos, int id, int param)
		{
			if (id == 1)
			{
				List<BlockPos> network = getNetwork(worldIn, pos);
				List<EnumDyeColor> colors = new ArrayList<>();
				
				network.forEach(e ->
				{
					EnumDyeColor c = worldIn.getBlockState(e)
											.getValue(COLOR);
					
					if (!colors.contains(c))
					{
						colors.add(c);
					}
				});
				
				colors.forEach(e -> updateState(worldIn, network, e));
			}
			
			return BlockStateTile.super.onBlockEventReceived(worldIn, pos, id, param);
		}
	}
	
	private static boolean isActive(IBlockState state)
	{
		return ((BlockSpikeTrap) state.getBlock()).isActive();
	}
	
	private static void switchState(World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);
		
		BlockSpikeTrap block = (BlockSpikeTrap) state.getBlock();
		SoundEvent sound;
		
		if (isActive(state))
		{
			state = block.getPassiveState()
						 .withProperty(COLOR, state.getValue(COLOR));
			
			sound = SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE;
		} else
		{
			state = block.getActiveState()
						 .withProperty(COLOR, state.getValue(COLOR));
			sound = SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN;
		}
		
		worldIn.setBlockState(pos, state);
		worldIn.playSound(null, pos, sound, SoundCategory.BLOCKS, 0.3F, 0.6F);
		
	}
	
	private static List<BlockPos> getNetwork(World worldIn, BlockPos pos)
	{
		LinkedList<BlockPos> result = new LinkedList<>();
		LinkedList<BlockPos> queue = new LinkedList<>();
		
		queue.add(pos);
		while (!queue.isEmpty())
		{
			pos = queue.poll();
			result.add(pos);
			
			for (EnumFacing side : EnumFacing.HORIZONTALS)
			{
				BlockPos sidePos = pos.offset(side);
				if (worldIn.getBlockState(sidePos)
						   .getBlock() instanceof BlockSpikeTrap && !result.contains(sidePos) && !queue.contains
						(sidePos))
				{
					queue.add(sidePos);
				}
			}
		}
		
		return result;
	}
	
	private static boolean hasPowerSource(World worldIn, List<BlockPos> network, EnumDyeColor color)
	{
		for (BlockPos pos : network)
		{
			if (worldIn.getBlockState(pos)
					   .getValue(COLOR) == color && hasPowerSource(worldIn, pos))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean hasPowerSource(World worldIn, BlockPos pos)
	{
		for (EnumFacing side : EnumFacing.values())
		{
			BlockPos offset = pos.offset(side);
			
			if (worldIn.getBlockState(offset)
					   .getWeakPower(worldIn, offset, side.getOpposite()) > 0)
			{
				return true;
			}
		}
		
		return false;
	}
}
