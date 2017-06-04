package com.github.lazylazuli.traps;

import net.minecraft.block.BlockColored;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockSpikes extends BlockColored implements ITileEntityProvider
{
	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	
	private final Item.ToolMaterial toolMaterial;
	
	BlockSpikes(String name, Material material, SoundType sound, Item.ToolMaterial toolMaterial)
	{
		super(material);
		
		setRegistryName(name);
		setUnlocalizedName(name);
		setHardness(1.25f);
		setResistance(7);
		setSoundType(sound);
		
		this.toolMaterial = toolMaterial;
	}
	
	@Nonnull
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
											float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, @Nonnull Entity exploder, Explosion explosion)
	{
		TileEntity te = world.getTileEntity(pos);
		
		if (te instanceof TileEntitySpikes)
		{
			return blockResistance / (5 - ((TileEntitySpikes) te).getBlastResistance());
		}
		
		return getExplosionResistance(exploder);
	}
	
	@Nonnull
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB;
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Nonnull
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Nonnull
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
	
	// BLOCKCONTAINER
	
	@Override
	public void dropBlockAsItemWithChance(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, float
			chance, int fortune) {}
	
	@Override
	public void harvestBlock(@Nonnull World worldIn, EntityPlayer player, @Nonnull BlockPos pos, @Nonnull IBlockState
			state, @Nullable TileEntity te, ItemStack stack)
	{
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		
		if (te instanceof TileEntitySpikes)
		{
			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TileEntitySpikes) te)
					.getItemDropped());
		}
	}
	
	@Override
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
	{
		super.eventReceived(state, worldIn, pos, id, param);
		
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		return tileentity != null && tileentity.receiveClientEvent(id, param);
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
		
		if (te != null && te instanceof TileEntitySpikes)
		{
			((TileEntitySpikes) te).onFallenUpon(entityIn, fallDistance);
		}
	}
	
	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te != null && te instanceof TileEntitySpikes)
		{
			((TileEntitySpikes) te).onEntityWalk(entityIn);
		}
	}
	
	// ENCHANTMENT
	
	@Nullable
	@Override
	public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta)
	{
		return new TileEntitySpikes();
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack
			stack)
	{
		TileEntity te = worldIn.getTileEntity(pos);
		
		if (te != null && te instanceof TileEntitySpikes)
		{
			((TileEntitySpikes) te).initializeStack(stack);
		}
	}
}
