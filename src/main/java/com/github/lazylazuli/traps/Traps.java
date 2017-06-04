package com.github.lazylazuli.traps;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Traps.MODID, version = Traps.VERSION, acceptedMinecraftVersions = Traps.MCVERSION)
public final class Traps
{
	public static final String MODID = "traps";
	public static final String VERSION = "@version@";
	public static final String MCVERSION = "@mcversion@";
	
	@Mod.Instance
	public static Traps instance;
	
	public static final BlockSpikes WOODEN_SPIKES = createSpikes("wooden_spikes", Item.ToolMaterial.WOOD);
	public static final BlockSpikes STONE_SPIKES = createSpikes("stone_spikes", Item.ToolMaterial.STONE);
	public static final BlockSpikes IRON_SPIKES = createSpikes("iron_spikes", Item.ToolMaterial.IRON);
	public static final BlockSpikes DIAMOND_SPIKES = createSpikes("diamond_spikes", Item.ToolMaterial.DIAMOND);
	public static final BlockSpikes GOLDEN_SPIKES = createSpikes("golden_spikes", Item.ToolMaterial.GOLD);
	
	public static final Item WOODEN_SPIKE = createSpike("wooden_spike");
	public static final Item STONE_SPIKE = createSpike("stone_spike");
	public static final Item IRON_SPIKE = createSpike("iron_spike");
	public static final Item DIAMOND_SPIKE = createSpike("diamond_spike");
	public static final Item GOLDEN_SPIKE = createSpike("golden_spike");
	
	private static Item createSpike(String name)
	{
		Item item = new Item();
		
		item.setRegistryName(name);
		item.setUnlocalizedName(name);
		item.setCreativeTab(CreativeTabs.MATERIALS);
		
		return item;
	}
	
	private static BlockSpikes createSpikes(String name, Item.ToolMaterial material)
	{
		return new BlockSpikes(name, Material.ROCK, SoundType.STONE, material);
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Registry.registerBlocks(event, Traps.WOODEN_SPIKES, Traps.STONE_SPIKES, Traps.IRON_SPIKES, Traps
				.DIAMOND_SPIKES, Traps.GOLDEN_SPIKES);
		
		Registry.registerItems(event, Traps.WOODEN_SPIKE, Traps.STONE_SPIKE, Traps.IRON_SPIKE, Traps.DIAMOND_SPIKE,
				Traps.GOLDEN_SPIKE);
		
		GameRegistry.registerTileEntity(TileEntitySpikes.class, "tileentityspikes");
		
		RecipeSpikes.addRecipes(CraftingManager.getInstance());
	}
}
