package com.github.lazylazuli.traps;

import com.github.lazylazuli.lazylazulilib.Craftory;
import com.github.lazylazuli.lazylazulilib.Stack;
import com.github.lazylazuli.traps.block.BlockDyedSlab;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

import java.util.Arrays;

import static com.github.lazylazuli.lazylazulilib.Craftory.horizontal;
import static com.github.lazylazuli.traps.Traps.*;
import static net.minecraft.block.BlockStone.EnumType.ANDESITE;
import static net.minecraft.block.BlockStone.EnumType.ANDESITE_SMOOTH;
import static net.minecraft.block.BlockStone.EnumType.DIORITE;
import static net.minecraft.block.BlockStone.EnumType.DIORITE_SMOOTH;
import static net.minecraft.block.BlockStone.EnumType.GRANITE;
import static net.minecraft.block.BlockStone.EnumType.GRANITE_SMOOTH;
import static net.minecraft.init.Blocks.COBBLESTONE;
import static net.minecraft.init.Blocks.PLANKS;
import static net.minecraft.init.Blocks.STONE;

final class RecipeSpikeTrap
{
	static void addRecipes(CraftingManager manager)
	{
		for (int i = 0; i < 16; i++)
		{
			horizontal(Stack.of(ANDESITE_SLAB, 6, i), Stack.ofMeta(STONE, ANDESITE.getMetadata()));
			horizontal(Stack.of(ANDESITE_SMOOTH_SLAB, 6, i), Stack.ofMeta(STONE, ANDESITE_SMOOTH.getMetadata()));
			horizontal(Stack.of(DIORITE_SLAB, 6, i), Stack.ofMeta(STONE, DIORITE.getMetadata()));
			horizontal(Stack.of(DIORITE_SMOOTH_SLAB, 6, i), Stack.ofMeta(STONE, DIORITE_SMOOTH.getMetadata()));
			horizontal(Stack.of(GRANITE_SLAB, 6, i), Stack.ofMeta(STONE, GRANITE.getMetadata()));
			horizontal(Stack.of(GRANITE_SMOOTH_SLAB, 6, i), Stack.ofMeta(STONE, GRANITE_SMOOTH.getMetadata()));
			
			for (BlockDyedSlab block : Arrays.asList(
					ANDESITE_SLAB,
					ANDESITE_SMOOTH_SLAB,
					DIORITE_SLAB,
					DIORITE_SMOOTH_SLAB,
					GRANITE_SLAB,
					GRANITE_SMOOTH_SLAB
			))
			{
				ItemStack stack = Stack.of(block, 8, i);
				ItemStack stack1 = Stack.ofMeta(
						Items.DYE,
						EnumDyeColor.byMetadata(i)
									.getDyeDamage()
				);
				
				for (int k = 0; k < 16; k++)
				{
					Craftory.framed(stack, Stack.ofMeta(block, k), stack1);
				}
				
			}
		}
		
		Object[] spikeIngred = new Object[] {
				PLANKS,
				COBBLESTONE,
				Items.IRON_INGOT,
				Items.DIAMOND,
				Items.GOLD_INGOT
		};
		
		Item[] trapIngred = new Item[] {
				WOODEN_SPIKE,
				STONE_SPIKE,
				IRON_SPIKE,
				DIAMOND_SPIKE,
				GOLDEN_SPIKE
		};
		
		Block[] trapResult = new Block[] {
				WOODEN_SPIKES,
				STONE_SPIKES,
				IRON_SPIKES,
				DIAMOND_SPIKES,
				GOLDEN_SPIKES
		};
		
		for (int i = 0; i < 5; i++)
		{
			// spike recipe
			manager.addRecipe(Stack.of(trapIngred[i], 6), " # ", "# #", '#', spikeIngred[i]);
			
			for (int j = 0; j < 16; j++)
			{
				// spike trap recipe
				Craftory.mix(
						Stack.of(trapResult[i], 4, j),
						Stack.of(trapIngred[i]),
						Stack.ofMeta(GRANITE_SMOOTH_SLAB, j)
				);
				
				// spike trap color swap recipe
				for (int k = 0; k < 16; k++)
				{
					Craftory.framed(
							Stack.of(trapResult[i], 8, k),
							Stack.of(trapResult[i]),
							Stack.ofMeta(
									Items.DYE,
									EnumDyeColor.byMetadata(k)
												.getDyeDamage()
							)
					);
				}
			}
		}
	}
}

