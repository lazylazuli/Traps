package com.github.lazylazuli.traps;

import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;

public enum TrapMaterial implements IStringSerializable
{
	WOOD(Item.ToolMaterial.WOOD),
	STONE(Item.ToolMaterial.STONE),
	IRON(Item.ToolMaterial.IRON),
	DIAMOND(Item.ToolMaterial.DIAMOND),
	GOLD(Item.ToolMaterial.GOLD);
	
	private final Item.ToolMaterial toolMaterial;
	
	TrapMaterial(Item.ToolMaterial toolMaterial)
	{
		this.toolMaterial = toolMaterial;
	}
	
	@Override
	public String getName()
	{
		return name().toLowerCase();
	}
	
	public Item.ToolMaterial getToolMaterial()
	{
		return toolMaterial;
	}
}
