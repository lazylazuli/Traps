package com.github.lazylazuli.traps;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Traps.MODID, version = Traps.VERSION, acceptedMinecraftVersions = Traps.MCVERSION)
public final class Traps
{
	public static final String MODID = "traps";
	public static final String VERSION = "@version@";
	public static final String MCVERSION = "@mcversion@";
	
	public static final String CLIENT_PROXY = "com.github.lazylazuli.traps.ClientProxy";
	public static final String COMMON_PROXY = "com.github.lazylazuli.traps.CommonProxy";
	
	@Mod.Instance
	public static Traps instance;
	
	@SidedProxy(clientSide = Traps.CLIENT_PROXY, serverSide = Traps.COMMON_PROXY)
	public static CommonProxy proxy;
	
	public static final BlockSpikes WOODEN_SPIKES = new BlockSpikes("wooden_spikes", Material.ROCK, SoundType.STONE,
			Item.ToolMaterial.WOOD);
	public static final BlockSpikes STONE_SPIKES = new BlockSpikes("stone_spikes", Material.ROCK, SoundType.STONE,
			Item.ToolMaterial.STONE);
	public static final BlockSpikes IRON_SPIKES = new BlockSpikes("iron_spikes", Material.ROCK, SoundType.STONE, Item
			.ToolMaterial.IRON);
	public static final BlockSpikes DIAMOND_SPIKES = new BlockSpikes("diamond_spikes", Material.ROCK, SoundType.STONE,
			Item.ToolMaterial.DIAMOND);
	public static final BlockSpikes GOLDEN_SPIKES = new BlockSpikes("golden_spikes", Material.ROCK, SoundType.STONE,
			Item.ToolMaterial.GOLD);
	
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
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
	}
}
