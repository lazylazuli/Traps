package com.github.lazylazuli.traps.client;

import com.github.lazylazuli.traps.common.CommonProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static com.github.lazylazuli.traps.common.TrapObjects.*;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		
		setModelResourceFor(GRASS_TRAP);
		
		setModelResourceFor(
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
				DIAMOND_SPIKE_TRAP
		);
		
		setModelResourceFor(WOODEN_SPIKE, STONE_SPIKE, IRON_SPIKE, GOLDEN_SPIKE, DIAMOND_SPIKE);
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		
		registerGrassColorHandlerFor(GRASS_TRAP);
		
		registerDyedColorHandlerFor(
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
				WOODEN_SPIKE_TRAP_OFF,
				STONE_SPIKE_TRAP_OFF,
				IRON_SPIKE_TRAP_OFF,
				GOLDEN_SPIKE_TRAP_OFF,
				DIAMOND_SPIKE_TRAP_OFF
		);
	}
}
