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
		
		setModelResource(ANDESITE_SLAB);
		setModelResource(SMOOTH_ANDESITE_SLAB);
		setModelResource(DIORITE_SLAB);
		setModelResource(SMOOTH_DIORITE_SLAB);
		setModelResource(GRANITE_SLAB);
		setModelResource(SMOOTH_GRANITE_SLAB);
		
		setModelResource(WOODEN_SPIKE_TRAP);
		setModelResource(STONE_SPIKE_TRAP);
		setModelResource(IRON_SPIKE_TRAP);
		setModelResource(GOLDEN_SPIKE_TRAP);
		setModelResource(DIAMOND_SPIKE_TRAP);
		
		setModelResource(WOODEN_SPIKE);
		setModelResource(STONE_SPIKE);
		setModelResource(IRON_SPIKE);
		setModelResource(GOLDEN_SPIKE);
		setModelResource(DIAMOND_SPIKE);
	}
	
	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		
		registerDyedColorHandler(ANDESITE_SLAB);
		registerDyedColorHandler(SMOOTH_ANDESITE_SLAB);
		registerDyedColorHandler(DIORITE_SLAB);
		registerDyedColorHandler(SMOOTH_DIORITE_SLAB);
		registerDyedColorHandler(GRANITE_SLAB);
		registerDyedColorHandler(SMOOTH_GRANITE_SLAB);
		
		registerDyedColorHandler(WOODEN_SPIKE_TRAP);
		registerDyedColorHandler(STONE_SPIKE_TRAP);
		registerDyedColorHandler(IRON_SPIKE_TRAP);
		registerDyedColorHandler(GOLDEN_SPIKE_TRAP);
		registerDyedColorHandler(DIAMOND_SPIKE_TRAP);

//		Minecraft.getMinecraft()
//				 .getBlockColors()
//				 .registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
//				 {
//					 if (worldIn != null && pos != null)
//					 {
//						 return BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
//					 } else
//					 {
//						 return ColorizerGrass.getGrassColor(0.5D, 1.0D);
//					 }
//				 }, GRASS_TRAP);
//
//		Minecraft.getMinecraft()
//				 .getItemColors()
//				 .registerItemColorHandler((stack, tintIndex) ->
//				 {
//					 IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock()
//																			.getStateFromMeta(stack.getMetadata());
//					 return Minecraft.getMinecraft()
//									 .getBlockColors()
//									 .colorMultiplier(iblockstate, null, null, tintIndex);
//				 }, GRASS_TRAP);
	}
}
