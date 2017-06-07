package com.github.lazylazuli.traps;

import com.github.lazylazuli.lazylazulilib.Registry;
import com.github.lazylazuli.lazylazulilib.Stack;
import com.github.lazylazuli.traps.block.BlockDyedSlab;
import com.github.lazylazuli.traps.block.BlockGrassTrap;
import com.github.lazylazuli.traps.block.BlockSpikeTrap;
import com.github.lazylazuli.traps.item.ItemSpikeTrap;
import com.github.lazylazuli.traps.tile.TileEntitySpikeTrap;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import static net.minecraft.block.BlockStone.EnumType.ANDESITE;
import static net.minecraft.block.BlockStone.EnumType.ANDESITE_SMOOTH;
import static net.minecraft.block.BlockStone.EnumType.DIORITE;
import static net.minecraft.block.BlockStone.EnumType.DIORITE_SMOOTH;
import static net.minecraft.block.BlockStone.EnumType.GRANITE;
import static net.minecraft.block.BlockStone.EnumType.GRANITE_SMOOTH;
import static net.minecraft.item.Item.ToolMaterial;
import static net.minecraft.item.Item.ToolMaterial.DIAMOND;
import static net.minecraft.item.Item.ToolMaterial.GOLD;
import static net.minecraft.item.Item.ToolMaterial.IRON;
import static net.minecraft.item.Item.ToolMaterial.STONE;
import static net.minecraft.item.Item.ToolMaterial.WOOD;

@Mod(modid = Traps.MODID, version = Traps.VERSION, acceptedMinecraftVersions = Traps.MCVERSION, dependencies =
		"required-after:lazylazulilib", updateJSON = Traps.UPDATE)
public final class Traps
{
	public static final String MODID = "traps";
	public static final String VERSION = "@version@";
	public static final String MCVERSION = "@mcversion@";
	public static final String UPDATE = "@update@";
	
	@Mod.Instance
	public static Traps instance;
	
	public static final BlockDyedSlab ANDESITE_SLAB = new BlockDyedSlab(ANDESITE);
	public static final BlockDyedSlab DIORITE_SLAB = new BlockDyedSlab(DIORITE);
	public static final BlockDyedSlab GRANITE_SLAB = new BlockDyedSlab(GRANITE);
	public static final BlockDyedSlab ANDESITE_SMOOTH_SLAB = new BlockDyedSlab(ANDESITE_SMOOTH);
	public static final BlockDyedSlab DIORITE_SMOOTH_SLAB = new BlockDyedSlab(DIORITE_SMOOTH);
	public static final BlockDyedSlab GRANITE_SMOOTH_SLAB = new BlockDyedSlab(GRANITE_SMOOTH);
	
	public static final BlockSpikeTrap WOODEN_SPIKES = createSpikes("wooden_spike_trap", WOOD);
	public static final BlockSpikeTrap STONE_SPIKES = createSpikes("stone_spike_trap", STONE);
	public static final BlockSpikeTrap IRON_SPIKES = createSpikes("iron_spike_trap", IRON);
	public static final BlockSpikeTrap DIAMOND_SPIKES = createSpikes("diamond_spike_trap", DIAMOND);
	public static final BlockSpikeTrap GOLDEN_SPIKES = createSpikes("golden_spike_trap", GOLD);
	public static final BlockGrassTrap GRASS_TRAP = new BlockGrassTrap();
	
	public static final Item WOODEN_SPIKE = createSpike("wooden_spike");
	public static final Item STONE_SPIKE = createSpike("stone_spike");
	public static final Item IRON_SPIKE = createSpike("iron_spike");
	public static final Item DIAMOND_SPIKE = createSpike("diamond_spike");
	public static final Item GOLDEN_SPIKE = createSpike("golden_spike");
	
	private static final Registry REGISTRY = new Registry(MODID);
	
	public static final CreativeTabs TAB_TRAPS = REGISTRY.newCreativeTab("traps", Stack.of(IRON_SPIKE));
	
	private static Item createSpike(String name)
	{
		Item item = new Item();
		item.setRegistryName(name);
		item.setUnlocalizedName(name);
		item.setCreativeTab(CreativeTabs.MATERIALS);
		return item;
	}
	
	private static BlockSpikeTrap createSpikes(String name, ToolMaterial material)
	{
		return new BlockSpikeTrap(name, Material.ROCK, SoundType.STONE, material);
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
//		REGISTRY.registerBlocks(
//				event,
//				GRASS_TRAP
//		);
		
		REGISTRY.registerDyedBlocks(
				event,
				ANDESITE_SLAB,
				ANDESITE_SMOOTH_SLAB,
				DIORITE_SLAB,
				DIORITE_SMOOTH_SLAB,
				GRANITE_SLAB,
				GRANITE_SMOOTH_SLAB
		);
		
		REGISTRY.registerItems(
				event,
				WOODEN_SPIKE,
				STONE_SPIKE,
				IRON_SPIKE,
				DIAMOND_SPIKE,
				GOLDEN_SPIKE
		);
		
		REGISTRY.registerDyedItems(
				event,
				new ItemSpikeTrap(WOODEN_SPIKES),
				new ItemSpikeTrap(STONE_SPIKES),
				new ItemSpikeTrap(IRON_SPIKES),
				new ItemSpikeTrap(DIAMOND_SPIKES),
				new ItemSpikeTrap(GOLDEN_SPIKES)
		);
		
		WOODEN_SPIKES.setCreativeTab(TAB_TRAPS);
		STONE_SPIKES.setCreativeTab(TAB_TRAPS);
		IRON_SPIKES.setCreativeTab(TAB_TRAPS);
		DIAMOND_SPIKES.setCreativeTab(TAB_TRAPS);
		GOLDEN_SPIKES.setCreativeTab(TAB_TRAPS);
		
		GameRegistry.registerTileEntity(TileEntitySpikeTrap.class, "tileentityspiketrap");
		
		RecipeSpikeTrap.addRecipes(CraftingManager.getInstance());
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (event.getSide() == Side.CLIENT)
		{
			REGISTRY.registerDyedBlockColorHandler(
					WOODEN_SPIKES,
					STONE_SPIKES,
					IRON_SPIKES,
					GOLDEN_SPIKES,
					DIAMOND_SPIKES,
					ANDESITE_SLAB,
					ANDESITE_SMOOTH_SLAB,
					DIORITE_SLAB,
					DIORITE_SMOOTH_SLAB,
					GRANITE_SLAB,
					GRANITE_SMOOTH_SLAB
			);
			
			REGISTRY.registerDyedItemColorHandler(
					WOODEN_SPIKES,
					STONE_SPIKES,
					IRON_SPIKES,
					GOLDEN_SPIKES,
					DIAMOND_SPIKES,
					ANDESITE_SLAB,
					ANDESITE_SMOOTH_SLAB,
					DIORITE_SLAB,
					DIORITE_SMOOTH_SLAB,
					GRANITE_SLAB,
					GRANITE_SMOOTH_SLAB
			);
			
			Minecraft.getMinecraft()
					 .getBlockColors()
					 .registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
					 {
						 if (worldIn != null && pos != null)
						 {
							 return BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
						 } else
						 {
							 return ColorizerGrass.getGrassColor(0.5D, 1.0D);
						 }
					 }, Traps.GRASS_TRAP);
			Minecraft.getMinecraft()
					 .getItemColors()
					 .registerItemColorHandler((stack, tintIndex) ->
					 {
						 IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock()
																				.getStateFromMeta(stack.getMetadata());
						 return Minecraft.getMinecraft()
										 .getBlockColors()
										 .colorMultiplier(iblockstate, null, null, tintIndex);
					 }, Traps.GRASS_TRAP);
		}
	}
}
