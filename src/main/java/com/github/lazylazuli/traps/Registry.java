package com.github.lazylazuli.traps;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

public final class Registry
{
	public static CreativeTabs newCreativeTab(String label, Item icon)
	{
		return new CreativeTabs(label)
		{
			@Nonnull
			@Override
			public ItemStack getTabIconItem()
			{
				return new ItemStack(icon);
			}
		};
	}
	
	static void registerBlocks(FMLPreInitializationEvent event, Block... blocks)
	{
		Logger logger = LogManager.getLogger(Traps.MODID);
		logger.info("Registering Blocks:");
		for (Block block : blocks)
		{
			if (event.getSide() == Side.CLIENT)
			{
				logger.info("\t" + block.getUnlocalizedName()
										.substring(5));
				
				ItemBlock itemBlock;
				if (block instanceof BlockSpikes)
				{
					itemBlock = new ItemSpikes((BlockSpikes) block);
					
					for (int i = 0; i < 16; i++)
					{
						String s = "traps:" + block.getUnlocalizedName()
												   .substring(5) + "_" + EnumDyeColor.byMetadata(i)
																					 .getUnlocalizedName();
						ModelLoader.setCustomModelResourceLocation(itemBlock, i, new ModelResourceLocation(s,
								"inventory"));
					}
				} else
				{
					itemBlock = new ItemBlock(block);
				}
				
				GameRegistry.register(block);
				
				ResourceLocation resourceLocation = block.getRegistryName();
				if (resourceLocation != null)
				{
					GameRegistry.register(itemBlock, resourceLocation);
					ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new
							ModelResourceLocation(resourceLocation, "inventory"));
				}
			}
		}
	}
	
	static void registerItems(FMLPreInitializationEvent event, Item... items)
	{
		Logger logger = LogManager.getLogger(Traps.MODID);
		logger.info("Registering Items:");
		for (Item item : items)
		{
			logger.info("\t" + item.getUnlocalizedName()
								   .substring(5));
			if (event.getSide() == Side.CLIENT)
			{
				GameRegistry.register(item);
				
				ResourceLocation resourceLocation = item.getRegistryName();
				if (resourceLocation != null)
				{
					ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(resourceLocation,
							"inventory"));
				}
			}
		}
	}
}
