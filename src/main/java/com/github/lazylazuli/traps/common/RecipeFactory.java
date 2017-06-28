package com.github.lazylazuli.traps.common;

import com.github.lazylazuli.lib.common.inventory.Stack;
import com.github.lazylazuli.lib.common.util.JsonRecipeFactory;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import static com.github.lazylazuli.traps.common.TrapObjects.*;
import static net.minecraft.init.Blocks.STONE;

class RecipeFactory extends JsonRecipeFactory
{
	RecipeFactory(String path)
	{
		super(path);
	}
	
	private void createJsonFiles()
	{
		Block[] slabs = new Block[] {
				GRANITE_SLAB,
				SMOOTH_GRANITE_SLAB,
				DIORITE_SLAB,
				SMOOTH_DIORITE_SLAB,
				ANDESITE_SLAB,
				SMOOTH_ANDESITE_SLAB
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
			ItemStack result = Stack.of(slabs[i], 6);
			String name = slabs[i].getUnlocalizedName().substring(5);
			createShapedRecipe(name, "stone_slabs", result, "XXX", 'X', slabIngredients[i]);
			
			for (EnumDyeColor dye : EnumDyeColor.values())
			{
				createShapedRecipe(dye.getName() + "_" + name, name, Stack.of(slabs[i], 8, dye.getMetadata()),
						"###",
						"#X#",
						"###",
						'#', Stack.ofMeta(slabs[i], 32767),
						'X',
						Stack.ofMeta(Items.DYE, dye.getDyeDamage())
				);
			}
		}
		
		Item[] spikes = new Item[] {
				WOODEN_SPIKE,
				STONE_SPIKE,
				IRON_SPIKE,
				GOLDEN_SPIKE,
				DIAMOND_SPIKE
		};
		
		Block[] spikeTrap = new Block[] {
				WOODEN_SPIKE_TRAP,
				STONE_SPIKE_TRAP,
				IRON_SPIKE_TRAP,
				GOLDEN_SPIKE_TRAP,
				DIAMOND_SPIKE_TRAP
		};
		
		Object[] spikeIngredients = new Object[] {
				Blocks.PLANKS,
				Blocks.COBBLESTONE,
				Items.IRON_INGOT,
				Items.GOLD_INGOT,
				Items.DIAMOND
		};
		
		for (int i = 0; i < 5; i++)
		{
			String name = spikes[i].getUnlocalizedName().substring(5);
			createShapedRecipe(name, Stack.of(spikes[i], 6), " X ", "X X", 'X', spikeIngredients[i]);
			
			name = spikeTrap[i].getUnlocalizedName().substring(5);
			for (EnumDyeColor dye : EnumDyeColor.values())
			{
				createShapedRecipe(dye.getName() + "_" + name, name, Stack.of(spikeTrap[i], 3, dye.getMetadata()),
						"XXX", "#Y#", "Y#Y",
						'X', Stack.of(spikes[i]),
						'#', Stack.ofMeta(slabs[1], dye.getMetadata()), 'Y', Stack.of(Items.REDSTONE)
				);
				
				createShapedRecipe(dye.getName() + "_" + name + "_",
						name + "dyed",
						Stack.of(spikeTrap[i], 8, dye.getMetadata()),
						"###",
						"#X#",
						"###",
						'#',
						Stack.ofMeta(spikeTrap[i], OreDictionary.WILDCARD_VALUE),
						'X',
						Stack.ofMeta(Items.DYE, dye.getDyeDamage())
				);
			}
		}
		
		createShapedRecipe(
				"grass_trap", Stack.of(GRASS_TRAP, 6), "###", "XXX",
				'#',
				Stack.ofMeta(Blocks.TALLGRASS, 1),
				'X',
				Stack.of(Items.STICK)
		);
	}
}
