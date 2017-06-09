package com.github.lazylazuli.traps.common;

import com.github.lazylazuli.lazylazulilib.inventory.Stack;
import com.github.lazylazuli.lazylazulilib.item.ItemBase;
import com.github.lazylazuli.lazylazulilib.registry.BlockRegistry;
import com.github.lazylazuli.lazylazulilib.registry.CreativeTabRegistry;
import com.github.lazylazuli.lazylazulilib.registry.ItemRegistry;
import com.github.lazylazuli.traps.block.BlockDyedSlab;
import com.github.lazylazuli.traps.block.BlockSpikeTrap;
import com.github.lazylazuli.traps.item.ItemSpikeTrap;
import com.github.lazylazuli.traps.item.crafting.RecipeSpikeTrap;
import com.github.lazylazuli.traps.tile.TileEntitySpikeTrap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.github.lazylazuli.traps.TrapObjects.*;
import static net.minecraft.block.BlockStone.EnumType.ANDESITE;
import static net.minecraft.block.BlockStone.EnumType.ANDESITE_SMOOTH;
import static net.minecraft.block.BlockStone.EnumType.DIORITE;
import static net.minecraft.block.BlockStone.EnumType.DIORITE_SMOOTH;
import static net.minecraft.block.BlockStone.EnumType.GRANITE;
import static net.minecraft.block.BlockStone.EnumType.GRANITE_SMOOTH;

public class CommonProxy extends com.github.lazylazuli.lazylazulilib.common.CommonProxy implements BlockRegistry,
		ItemRegistry, CreativeTabRegistry
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		GameRegistry.registerTileEntity(TileEntitySpikeTrap.class, "tileentityspiketrap");
		RecipeSpikeTrap.addRecipes(CraftingManager.getInstance());
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
	}
	
	@Override
	public Block[] getBlocksForRegistry()
	{
		return new Block[] {
				new BlockDyedSlab(Material.ROCK, "andesite_slab", ANDESITE),
				new BlockDyedSlab(Material.ROCK, "smooth_andesite_slab", ANDESITE_SMOOTH),
				new BlockDyedSlab(Material.ROCK, "diorite_slab", DIORITE),
				new BlockDyedSlab(Material.ROCK, "smooth_diorite_slab", DIORITE_SMOOTH),
				new BlockDyedSlab(Material.ROCK, "granite_slab", GRANITE),
				new BlockDyedSlab(Material.ROCK, "smooth_granite_slab", GRANITE_SMOOTH),
				new BlockSpikeTrap(Material.ROCK, "wooden_spike_trap", Item.ToolMaterial.WOOD),
				new BlockSpikeTrap(Material.ROCK, "stone_spike_trap", Item.ToolMaterial.STONE),
				new BlockSpikeTrap(Material.ROCK, "iron_spike_trap", Item.ToolMaterial.IRON),
				new BlockSpikeTrap(Material.ROCK, "golden_spike_trap", Item.ToolMaterial.GOLD),
				new BlockSpikeTrap(Material.ROCK, "diamond_spike_trap", Item.ToolMaterial.DIAMOND),
//				new BlockGrassTrap(Material.ROCK, "grass_trap")
		};
	}
	
	@Override
	public Item[] getItemsForRegistry()
	{
		return new Item[] {
				new ItemBase("wooden_spike"),
				new ItemBase("stone_spike"),
				new ItemBase("iron_spike"),
				new ItemBase("golden_spike"),
				new ItemBase("diamond_spike"),
				new ItemSpikeTrap(WOODEN_SPIKE_TRAP),
				new ItemSpikeTrap(STONE_SPIKE_TRAP),
				new ItemSpikeTrap(IRON_SPIKE_TRAP),
				new ItemSpikeTrap(GOLDEN_SPIKE_TRAP),
				new ItemSpikeTrap(DIAMOND_SPIKE_TRAP)
		};
	}
	
	@Override
	public CreativeTabs[] getCreativeTabsForRegistry()
	{
		return new CreativeTabs[] {
				CreativeTabRegistry.create("traps", Stack.of(IRON_SPIKE))
		};
	}
	
	@Override
	public Block[] getBlocksForTab(CreativeTabs tab)
	{
		return new Block[] {
				ANDESITE_SLAB,
				SMOOTH_ANDESITE_SLAB,
				DIORITE_SLAB,
				SMOOTH_DIORITE_SLAB,
				GRANITE_SLAB,
				SMOOTH_GRANITE_SLAB,
				WOODEN_SPIKE_TRAP,
				STONE_SPIKE_TRAP,
				IRON_SPIKE_TRAP,
				GOLDEN_SPIKE_TRAP,
				DIAMOND_SPIKE_TRAP,
//				GRASS_TRAP
		};
	}
	
	@Override
	public Item[] getItemsForTab(CreativeTabs tab)
	{
		return new Item[] {
				WOODEN_SPIKE,
				STONE_SPIKE,
				IRON_SPIKE,
				GOLDEN_SPIKE,
				DIAMOND_SPIKE
		};
	}
}
