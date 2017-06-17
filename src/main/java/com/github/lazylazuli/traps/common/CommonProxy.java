package com.github.lazylazuli.traps.common;

import com.github.lazylazuli.lib.common.inventory.Stack;
import com.github.lazylazuli.lib.common.item.ItemBase;
import com.github.lazylazuli.lib.common.item.ItemBlockBase;
import com.github.lazylazuli.lib.common.item.ItemBlockDyed;
import com.github.lazylazuli.lib.common.registry.BlockRegistry;
import com.github.lazylazuli.lib.common.registry.CreativeTabRegistry;
import com.github.lazylazuli.lib.common.registry.ItemRegistry;
import com.github.lazylazuli.traps.common.block.BlockDyedSlab;
import com.github.lazylazuli.traps.common.block.BlockGrassTrap;
import com.github.lazylazuli.traps.common.block.BlockSpikeTrap;
import com.github.lazylazuli.traps.common.item.ItemSpikeTrap;
import com.github.lazylazuli.traps.common.tile.TileSpikeTrap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.github.lazylazuli.traps.common.TrapObjects.*;
import static net.minecraft.block.BlockStone.EnumType.ANDESITE;
import static net.minecraft.block.BlockStone.EnumType.ANDESITE_SMOOTH;
import static net.minecraft.block.BlockStone.EnumType.DIORITE;
import static net.minecraft.block.BlockStone.EnumType.DIORITE_SMOOTH;
import static net.minecraft.block.BlockStone.EnumType.GRANITE;
import static net.minecraft.block.BlockStone.EnumType.GRANITE_SMOOTH;

public class CommonProxy extends com.github.lazylazuli.lib.common.CommonProxy implements BlockRegistry, ItemRegistry,
		CreativeTabRegistry
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		GameRegistry.registerTileEntity(TileSpikeTrap.class, "tile_spike_trap");
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		RecipeFactory.registerRecipes();
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
				new BlockGrassTrap(Material.GRASS, "grass_trap"),
				new BlockSpikeTrap(
						Material.ROCK,
						"wooden_spike_trap",
						Item.ToolMaterial.WOOD,
						() -> WOODEN_SPIKE_TRAP.getDefaultState(),
						() -> WOODEN_SPIKE_TRAP_OFF.getDefaultState()
				),
				new BlockSpikeTrap(
						Material.ROCK,
						"stone_spike_trap",
						Item.ToolMaterial.STONE,
						() -> STONE_SPIKE_TRAP.getDefaultState(),
						() -> STONE_SPIKE_TRAP_OFF.getDefaultState()
				),
				new BlockSpikeTrap(
						Material.ROCK,
						"iron_spike_trap",
						Item.ToolMaterial.IRON,
						() -> IRON_SPIKE_TRAP.getDefaultState(),
						() -> IRON_SPIKE_TRAP_OFF.getDefaultState()
				),
				new BlockSpikeTrap(
						Material.ROCK,
						"golden_spike_trap",
						Item.ToolMaterial.GOLD,
						() -> GOLDEN_SPIKE_TRAP.getDefaultState(),
						() -> GOLDEN_SPIKE_TRAP_OFF.getDefaultState()
				),
				new BlockSpikeTrap(
						Material.ROCK,
						"diamond_spike_trap",
						Item.ToolMaterial.DIAMOND,
						() -> DIAMOND_SPIKE_TRAP.getDefaultState(),
						() -> DIAMOND_SPIKE_TRAP_OFF.getDefaultState()
				),
				new BlockSpikeTrap(
						Material.ROCK,
						"wooden_spike_trap_off",
						Item.ToolMaterial.WOOD,
						() -> WOODEN_SPIKE_TRAP.getDefaultState(),
						() -> WOODEN_SPIKE_TRAP_OFF.getDefaultState()
				),
				new BlockSpikeTrap(
						Material.ROCK,
						"stone_spike_trap_off",
						Item.ToolMaterial.STONE,
						() -> STONE_SPIKE_TRAP.getDefaultState(),
						() -> STONE_SPIKE_TRAP_OFF.getDefaultState()
				),
				new BlockSpikeTrap(
						Material.ROCK,
						"iron_spike_trap_off",
						Item.ToolMaterial.IRON,
						() -> IRON_SPIKE_TRAP.getDefaultState(),
						() -> IRON_SPIKE_TRAP_OFF.getDefaultState()
				),
				new BlockSpikeTrap(
						Material.ROCK,
						"golden_spike_trap_off",
						Item.ToolMaterial.GOLD,
						() -> GOLDEN_SPIKE_TRAP.getDefaultState(),
						() -> GOLDEN_SPIKE_TRAP_OFF.getDefaultState()
				),
				new BlockSpikeTrap(
						Material.ROCK,
						"diamond_spike_trap_off",
						Item.ToolMaterial.DIAMOND,
						() -> DIAMOND_SPIKE_TRAP.getDefaultState(),
						() -> DIAMOND_SPIKE_TRAP_OFF.getDefaultState()
				)
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
				new ItemBlockDyed(ANDESITE_SLAB),
				new ItemBlockDyed(SMOOTH_ANDESITE_SLAB),
				new ItemBlockDyed(DIORITE_SLAB),
				new ItemBlockDyed(SMOOTH_DIORITE_SLAB),
				new ItemBlockDyed(GRANITE_SLAB),
				new ItemBlockDyed(SMOOTH_GRANITE_SLAB),
				new ItemSpikeTrap(WOODEN_SPIKE_TRAP),
				new ItemSpikeTrap(STONE_SPIKE_TRAP),
				new ItemSpikeTrap(IRON_SPIKE_TRAP),
				new ItemSpikeTrap(GOLDEN_SPIKE_TRAP),
				new ItemSpikeTrap(DIAMOND_SPIKE_TRAP),
				new ItemBlockBase(GRASS_TRAP)
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
				WOODEN_SPIKE_TRAP,
				STONE_SPIKE_TRAP,
				IRON_SPIKE_TRAP,
				GOLDEN_SPIKE_TRAP,
				DIAMOND_SPIKE_TRAP,
				GRASS_TRAP
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
