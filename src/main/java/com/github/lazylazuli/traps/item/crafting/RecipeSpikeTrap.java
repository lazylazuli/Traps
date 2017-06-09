package com.github.lazylazuli.traps.item.crafting;

import com.github.lazylazuli.lazylazulilib.inventory.Craftory;
import com.github.lazylazuli.lazylazulilib.inventory.Stack;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

import java.util.Arrays;

import static com.github.lazylazuli.lazylazulilib.inventory.Craftory.horizontal;
import static com.github.lazylazuli.traps.TrapObjects.*;
import static net.minecraft.block.BlockStone.EnumType.ANDESITE;
import static net.minecraft.block.BlockStone.EnumType.ANDESITE_SMOOTH;
import static net.minecraft.block.BlockStone.EnumType.DIORITE;
import static net.minecraft.block.BlockStone.EnumType.DIORITE_SMOOTH;
import static net.minecraft.block.BlockStone.EnumType.GRANITE;
import static net.minecraft.block.BlockStone.EnumType.GRANITE_SMOOTH;
import static net.minecraft.init.Blocks.COBBLESTONE;
import static net.minecraft.init.Blocks.PLANKS;
import static net.minecraft.init.Blocks.STONE;

public final class RecipeSpikeTrap
{
	public static void addRecipes(CraftingManager manager)
	{
		for (int i = 0; i < 16; i++)
		{
			horizontal(Stack.of(ANDESITE_SLAB, 6, i), Stack.ofMeta(STONE, ANDESITE.getMetadata()));
			horizontal(Stack.of(SMOOTH_ANDESITE_SLAB, 6, i), Stack.ofMeta(STONE, ANDESITE_SMOOTH.getMetadata()));
			horizontal(Stack.of(DIORITE_SLAB, 6, i), Stack.ofMeta(STONE, DIORITE.getMetadata()));
			horizontal(Stack.of(SMOOTH_DIORITE_SLAB, 6, i), Stack.ofMeta(STONE, DIORITE_SMOOTH.getMetadata()));
			horizontal(Stack.of(GRANITE_SLAB, 6, i), Stack.ofMeta(STONE, GRANITE.getMetadata()));
			horizontal(Stack.of(SMOOTH_GRANITE_SLAB, 6, i), Stack.ofMeta(STONE, GRANITE_SMOOTH.getMetadata()));
			
			for (Block block : Arrays.asList(
					ANDESITE_SLAB,
					SMOOTH_ANDESITE_SLAB,
					DIORITE_SLAB,
					SMOOTH_DIORITE_SLAB,
					GRANITE_SLAB,
					SMOOTH_GRANITE_SLAB
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
				WOODEN_SPIKE_TRAP,
				STONE_SPIKE_TRAP,
				IRON_SPIKE_TRAP,
				DIAMOND_SPIKE_TRAP,
				GOLDEN_SPIKE_TRAP
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
						Stack.ofMeta(SMOOTH_GRANITE_SLAB, j)
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

