package com.github.lazylazuli.traps;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy
{
	void preInit(FMLPreInitializationEvent event)
	{
		Registry.registerBlocks(event, Traps.WOODEN_SPIKES, Traps.STONE_SPIKES, Traps.IRON_SPIKES, Traps
				.DIAMOND_SPIKES, Traps.GOLDEN_SPIKES);
		Registry.registerItems(event, Traps.WOODEN_SPIKE, Traps.STONE_SPIKE, Traps.IRON_SPIKE, Traps.DIAMOND_SPIKE,
				Traps.GOLDEN_SPIKE);
		GameRegistry.registerTileEntity(TileEntitySpikes.class, "tileentityspikes");
		new RecipeSpikes().addRecipes(CraftingManager.getInstance());
	}
}