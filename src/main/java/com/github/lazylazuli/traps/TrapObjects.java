package com.github.lazylazuli.traps;

import com.github.lazylazuli.lazylazulilib.item.ItemBase;
import com.github.lazylazuli.traps.block.BlockDyedSlab;
import com.github.lazylazuli.traps.block.BlockGrassTrap;
import com.github.lazylazuli.traps.block.BlockSpikeTrap;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = Traps.MODID)
@GameRegistry.ObjectHolder(Traps.MODID)
public class TrapObjects
{
	@GameRegistry.ObjectHolder("andesite_slab")
	public static final BlockDyedSlab ANDESITE_SLAB = null;
	
	@GameRegistry.ObjectHolder("smooth_andesite_slab")
	public static final BlockDyedSlab SMOOTH_ANDESITE_SLAB = null;
	
	@GameRegistry.ObjectHolder("diorite_slab")
	public static final BlockDyedSlab DIORITE_SLAB = null;
	
	@GameRegistry.ObjectHolder("smooth_diorite_slab")
	public static final BlockDyedSlab SMOOTH_DIORITE_SLAB = null;
	
	@GameRegistry.ObjectHolder("granite_slab")
	public static final BlockDyedSlab GRANITE_SLAB = null;
	
	@GameRegistry.ObjectHolder("smooth_granite_slab")
	public static final BlockDyedSlab SMOOTH_GRANITE_SLAB = null;
	
	@GameRegistry.ObjectHolder("wooden_spike_trap")
	public static final BlockSpikeTrap WOODEN_SPIKE_TRAP = null;
	
	@GameRegistry.ObjectHolder("stone_spike_trap")
	public static final BlockSpikeTrap STONE_SPIKE_TRAP = null;
	
	@GameRegistry.ObjectHolder("iron_spike_trap")
	public static final BlockSpikeTrap IRON_SPIKE_TRAP = null;
	
	@GameRegistry.ObjectHolder("golden_spike_trap")
	public static final BlockSpikeTrap GOLDEN_SPIKE_TRAP = null;
	
	@GameRegistry.ObjectHolder("diamond_spike_trap")
	public static final BlockSpikeTrap DIAMOND_SPIKE_TRAP = null;
	
	@GameRegistry.ObjectHolder("grass_trap")
	public static final BlockGrassTrap GRASS_TRAP = null;
	
	@GameRegistry.ObjectHolder("wooden_spike")
	public static final ItemBase WOODEN_SPIKE = null;
	
	@GameRegistry.ObjectHolder("stone_spike")
	public static final ItemBase STONE_SPIKE = null;
	
	@GameRegistry.ObjectHolder("iron_spike")
	public static final ItemBase IRON_SPIKE = null;
	
	@GameRegistry.ObjectHolder("golden_spike")
	public static final ItemBase GOLDEN_SPIKE = null;
	
	@GameRegistry.ObjectHolder("diamond_spike")
	public static final ItemBase DIAMOND_SPIKE = null;
}
