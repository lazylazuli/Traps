package com.github.lazylazuli.traps;

import com.github.lazylazuli.lib.common.LazyLazuliMod;
import com.github.lazylazuli.lib.common.Proxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Traps.MODID, version = Traps.VERSION, acceptedMinecraftVersions = Traps.MCVERSION, dependencies =
		"required-after:lazylazulilib", updateJSON = Traps.UPDATE)
public class Traps extends LazyLazuliMod
{
	public static final String MODID = "traps";
	public static final String VERSION = "@version@";
	public static final String MCVERSION = "@mcversion@";
	public static final String UPDATE = "@update@";
	
	@Mod.Instance
	public static Traps instance;
	
	@SidedProxy(
			clientSide = "com.github.lazylazuli.traps.client.ClientProxy",
			serverSide = "com.github.lazylazuli.traps.common.CommonProxy"
	)
	public static Proxy proxy;
	
	@Override
	public Proxy getProxy()
	{
		return proxy;
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
	}
}
