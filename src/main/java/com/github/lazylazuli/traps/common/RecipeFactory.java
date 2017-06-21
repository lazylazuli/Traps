package com.github.lazylazuli.traps.common;

import com.github.lazylazuli.lib.common.inventory.Stack;
import com.github.lazylazuli.lib.common.item.crafting.JsonRecipeFactory;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import static com.github.lazylazuli.traps.common.TrapObjects.*;
import static net.minecraft.init.Blocks.STONE;

class RecipeFactory extends JsonRecipeFactory
{
	RecipeFactory(String path)
	{
		super(path);
	}
	
	void createJsonFiles()
	{
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
																 .substring(5),
						Stack.of(slabs[i].getItem(), 8, dye.getMetadata()),
						"###",
						"#X#",
						"###",
						'#',
						Stack.ofMeta(slabs[i].getItem(), 32767),
						'X',
						Stack.ofMeta(Items.DYE, dye.getDyeDamage())
				);
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
																	  .substring(5),
						Stack.of(spikeTraps[i].getItem(), 3, dye.getMetadata()),
						"XXX",
						"###",
						'X',
						Stack.of(spikes[i].getItem()),
						'#',
						Stack.ofMeta(slabs[1].getItem(), dye.getMetadata())
				);
				
				createShapedRecipe(dye.getName() + "_" + spikeTraps[i].getItem()
																	  .getUnlocalizedName()
																	  .substring(5) + "_",
						Stack.of(spikeTraps[i].getItem(), 8, dye.getMetadata()),
						"###",
						"#X#",
						"###",
						'#',
						Stack.ofMeta(spikeTraps[i].getItem(), 32767),
						'X',
						Stack.ofMeta(Items.DYE, dye.getDyeDamage())
				);
			}
		}
		
		createShapedRecipe(
				"grass_trap",
				Stack.of(GRASS_TRAP, 4),
				"#X#",
				"X#X",
				"#X#",
				'#',
				Stack.ofMeta(Blocks.TALLGRASS, 1),
				'X',
				Stack.of(Items.STICK)
		);
	}
	
	public static void registerRecipes()
	{
		ResourceLocation rl = new ResourceLocation("", "");
		ItemStack[] stones = new ItemStack[] {
				Stack.ofMeta(STONE, 1),
				Stack.ofMeta(STONE, 2),
				Stack.ofMeta(STONE, 3),
				Stack.ofMeta(STONE, 4),
				Stack.ofMeta(STONE, 5),
				Stack.ofMeta(STONE, 6)
		};
		ItemStack[] slabs = new ItemStack[] {
				Stack.of(GRANITE_SLAB, 6),
				Stack.of(SMOOTH_GRANITE_SLAB, 6),
				Stack.of(DIORITE_SLAB, 6),
				Stack.of(SMOOTH_DIORITE_SLAB, 6),
				Stack.of(ANDESITE_SLAB, 6),
				Stack.of(SMOOTH_ANDESITE_SLAB, 6)
		};
		
		ItemStack[] spikes = new ItemStack[] {
				Stack.of(WOODEN_SPIKE, 6),
				Stack.of(STONE_SPIKE, 6),
				Stack.of(IRON_SPIKE, 6),
				Stack.of(GOLDEN_SPIKE, 6),
				Stack.of(DIAMOND_SPIKE, 6)
		};
		ItemStack[] spikeIngredients = new ItemStack[] {
				Stack.ofMeta(Blocks.PLANKS, 32767),
				Stack.of(Blocks.COBBLESTONE),
				Stack.of(Items.IRON_INGOT),
				Stack.of(Items.GOLD_INGOT),
				Stack.of(Items.DIAMOND)
		};
		
		ResourceLocation name;
		
		// basic slab recipes
		for (int i = 0; i < 6; i++)
		{
			name = slabs[i].getItem()
						   .getRegistryName();
			GameRegistry.register(new ShapedOreRecipe(rl, slabs[i], "XXX", 'X', stones[i]).setRegistryName(name));
		}
		
		// spike recipes
		for (int i = 0; i < 5; i++)
		{
			name = spikes[i].getItem()
							.getRegistryName();
			GameRegistry.register(new ShapedOreRecipe(rl, spikes[i],
					" X ",
					"X X",
					'X',
					spikeIngredients[i]
			).setRegistryName(name));
		}
		
		for (EnumDyeColor color : EnumDyeColor.values())
		{
			ItemStack dye = Stack.ofMeta(Items.DYE, color.getDyeDamage());
			
			// slab coloring recipes
			for (int i = 0; i < 6; i++)
			{
				ItemStack slabColored = Stack.of(slabs[i].getItem(), 8, color.getMetadata());
				ItemStack slabAny = Stack.ofMeta(slabs[i].getItem(), 32767);
				
				name = new ResourceLocation(Traps.MODID,
						color.getName() + "_" + slabs[i].getUnlocalizedName()
														.substring(5)
				);
				
				GameRegistry.register(new ShapedOreRecipe(rl,
						slabColored,
						"XXX",
						"X#X",
						"XXX",
						'X',
						slabAny,
						'#',
						dye
				).setRegistryName(name));
			}
			
			ItemStack[] traps = new ItemStack[] {
					Stack.of(WOODEN_SPIKE_TRAP, 3, color.getMetadata()),
					Stack.of(STONE_SPIKE_TRAP, 3, color.getMetadata()),
					Stack.of(IRON_SPIKE_TRAP, 3, color.getMetadata()),
					Stack.of(GOLDEN_SPIKE_TRAP, 3, color.getMetadata()),
					Stack.of(DIAMOND_SPIKE_TRAP, 3, color.getMetadata())
			};
			ItemStack slab = Stack.ofMeta(slabs[1].getItem(), color.getMetadata());
			ItemStack redstone = Stack.of(Items.REDSTONE);
			for (int i = 0; i < 5; i++)
			{
				ItemStack trapAny = Stack.ofMeta(traps[i].getItem(), 32767);
				name = new ResourceLocation(Traps.MODID,
						color.getName() + "_" + traps[i].getUnlocalizedName()
														.substring(5)
				);
				
				// spike trap basic recipes
				GameRegistry.register(new ShapedOreRecipe(rl,
						traps[i],
						"XXX",
						"#Y#",
						"Y#Y",
						'X',
						spikes[i],
						'#',
						slab,
						'Y',
						redstone
				).setRegistryName(name));
				
				name = new ResourceLocation(Traps.MODID,
						color.getName() + "_" + traps[i].getUnlocalizedName()
														.substring(5) + "_"
				);
				
				// spike trap color change recipes
				GameRegistry.register(new ShapedOreRecipe(rl,
						Stack.of(traps[i].getItem(), 8, color.getMetadata()),
						"XXX",
						"X#X",
						"XXX",
						'X',
						trapAny,
						'#',
						dye
				).setRegistryName(name));
			}
		}
		
		name = new ResourceLocation(Traps.MODID, "grass_trap");
		GameRegistry.register(new ShapedOreRecipe(rl,
				Stack.of(GRASS_TRAP, 4),
				"#X#",
				"X#X",
				"#X#",
				'X',
				Items.STICK,
				'#',
				Stack.ofMeta(Blocks.TALLGRASS, 1)
		).setRegistryName(name));
	}
}
