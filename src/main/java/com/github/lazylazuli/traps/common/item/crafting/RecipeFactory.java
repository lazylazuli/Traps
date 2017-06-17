package com.github.lazylazuli.traps.common.item.crafting;

import com.github.lazylazuli.lib.common.inventory.Stack;
import com.github.lazylazuli.lib.common.item.crafting.JsonRecipeFactory;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import static com.github.lazylazuli.traps.common.TrapObjects.*;
import static net.minecraft.init.Blocks.STONE;

public class RecipeFactory extends JsonRecipeFactory
{
	public RecipeFactory(String path)
	{
		super(path);
		
		ItemStack[] slabs = new ItemStack[] {
				Stack.of(GRANITE_SLAB, 6),
				Stack.of(SMOOTH_GRANITE_SLAB, 6),
				Stack.of(DIORITE_SLAB, 6),
				Stack.of(SMOOTH_DIORITE_SLAB, 6),
				Stack.of(ANDESITE_SLAB, 6),
				Stack.of(SMOOTH_ANDESITE_SLAB, 6)
		};
		
		ItemStack[] slabIngredients = new ItemStack[] {
				Stack.ofMeta(STONE, 1),
				Stack.ofMeta(STONE, 2),
				Stack.ofMeta(STONE, 3),
				Stack.ofMeta(STONE, 4),
				Stack.ofMeta(STONE, 5),
				Stack.ofMeta(STONE, 6)
		};
		
		for (int i = 0; i < 6; i++)
		{
			createShapedRecipe(slabs[i].getUnlocalizedName()
									   .substring(5), slabs[i], "XXX", 'X', slabIngredients[i]);
			
			for (EnumDyeColor dye : EnumDyeColor.values())
			{
				createShapedRecipe(dye.getName() + "_" + slabs[i].getItem()
																 .getUnlocalizedName()
																 .substring(5), Stack.of(slabs[i].getItem(), 8, dye
						.getMetadata()), "###", "#X#", "###", '#', Stack.ofMeta(slabs[i].getItem(), 32767), 'X', Stack
						.ofMeta(Items.DYE, dye.getDyeDamage()));
			}
		}
		
		ItemStack[] spikes = new ItemStack[] {
				Stack.of(WOODEN_SPIKE, 6),
				Stack.of(STONE_SPIKE, 6),
				Stack.of(IRON_SPIKE, 6),
				Stack.of(GOLDEN_SPIKE, 6),
				Stack.of(DIAMOND_SPIKE, 6)
		};
		
		ItemStack[] spikeTraps = new ItemStack[] {
				Stack.of(WOODEN_SPIKE_TRAP, 6),
				Stack.of(STONE_SPIKE_TRAP, 6),
				Stack.of(IRON_SPIKE_TRAP, 6),
				Stack.of(GOLDEN_SPIKE_TRAP, 6),
				Stack.of(DIAMOND_SPIKE_TRAP, 6)
		};
		
		ItemStack[] spikeIngredients = new ItemStack[] {
				Stack.ofMeta(Blocks.PLANKS, 32767),
				Stack.of(Blocks.COBBLESTONE),
				Stack.of(Items.IRON_INGOT),
				Stack.of(Items.GOLD_INGOT),
				Stack.of(Items.DIAMOND)
		};
		
		for (int i = 0; i < 5; i++)
		{
			createShapedRecipe(spikes[i].getUnlocalizedName()
										.substring(5), spikes[i], " X ", "X X", 'X', spikeIngredients[i]);
			
			for (EnumDyeColor dye : EnumDyeColor.values())
			{
				createShapedRecipe(dye.getName() + "_" + spikeTraps[i].getItem()
																	  .getUnlocalizedName()
																	  .substring(5), Stack.of(spikeTraps[i].getItem(),
						3, dye.getMetadata()), "XXX", "###", 'X', Stack.of(spikes[i].getItem()), '#', Stack.ofMeta
						(slabs[1].getItem(), dye.getMetadata())
				);
				
				createShapedRecipe(dye.getName() + "_" + spikeTraps[i].getItem()
																	  .getUnlocalizedName()
																	  .substring(5) + "_", Stack.of(spikeTraps[i]
						.getItem(), 8, dye.getMetadata()), "###", "#X#", "###", '#', Stack.ofMeta(spikeTraps[i]
						.getItem(), 32767), 'X', Stack.ofMeta(Items.DYE, dye.getDyeDamage()));
			}
		}
	}
}
