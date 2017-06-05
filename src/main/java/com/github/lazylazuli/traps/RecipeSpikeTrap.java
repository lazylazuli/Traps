package com.github.lazylazuli.traps;

import com.github.lazylazuli.lazylazulilib.Stack;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

final class RecipeSpikeTrap
{
	static void addRecipes(CraftingManager manager)
	{
		String[][] recipePattern = new String[][] {
				new String[] {
						" # ",
						"# #"
				},
				new String[] {
						"X#X",
						"#X#",
						"X#X"
				}
		};
		
		Object[][] recipeItems = new Object[][] {
				{
						Blocks.PLANKS,
						Blocks.COBBLESTONE,
						Items.IRON_INGOT,
						Items.DIAMOND,
						Items.GOLD_INGOT
				},
				{
						Traps.WOODEN_SPIKE,
						Traps.STONE_SPIKE,
						Traps.IRON_SPIKE,
						Traps.DIAMOND_SPIKE,
						Traps.GOLDEN_SPIKE
				},
				{
						Traps.WOODEN_SPIKES,
						Traps.STONE_SPIKES,
						Traps.IRON_SPIKES,
						Traps.DIAMOND_SPIKES,
						Traps.GOLDEN_SPIKES
				}
		};
		
		for (int i = 0; i < recipeItems[0].length; ++i)
		{
			Object toolIngredient = recipeItems[0][i];
			Block block = (Block) recipeItems[2][i];
			
			ItemStack spike = Stack.of((Item) recipeItems[1][i], 6);
			
			manager.addRecipe(spike, recipePattern[0], '#', toolIngredient);
			
			ItemStack spikes = Stack.of(block, 4);
			
			manager.addRecipe(spikes, recipePattern[1], '#', spike, 'X', Blocks.HARDENED_CLAY);
			
			for (EnumDyeColor dyeColor : EnumDyeColor.values())
			{
				ItemStack oldColor = Stack.ofMeta(block, dyeColor.getMetadata());
				for (EnumDyeColor dyeColor1 : EnumDyeColor.values())
				{
					if (dyeColor != dyeColor1)
					{
						ItemStack dye = Stack.ofMeta(Items.DYE, dyeColor1.getDyeDamage());
						ItemStack newBlock = Stack.ofMeta(block, dyeColor1.getMetadata());
						manager.addShapelessRecipe(newBlock, oldColor, dye);
					}
				}
				
				ItemStack coloredSpikes = Stack.of(block, 4, dyeColor.getMetadata());
				ItemStack clay = Stack.ofMeta(Blocks.STAINED_HARDENED_CLAY, dyeColor.getMetadata());
				manager.addRecipe(coloredSpikes, recipePattern[1], '#', spike, 'X', clay);
			}
		}
	}
}

