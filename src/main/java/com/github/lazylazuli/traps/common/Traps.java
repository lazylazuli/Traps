package com.github.lazylazuli.traps.common;

import com.github.lazylazuli.lib.common.LazyLazuliMod;
import com.github.lazylazuli.lib.common.Proxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Traps.MODID,
	 version = Traps.VERSION + "-" + Traps.BUILD,
	 acceptedMinecraftVersions = Traps.MCVERSION,
	 dependencies = "required-after:lazylazulilib@[2.0.0]",
	 updateJSON = Traps.UPDATE)
public class Traps extends LazyLazuliMod
{
	public static final String MODID = "lazylazulitraps";
	
	public static final String MCVERSION = "1.12";
	
	public static final String VERSION = "2.0.0";
	public static final String BUILD = "41";
	
	public static final String UPDATE = "https://github.com/lazylazuli/Traps/releases/download/Latest/update.json";
	
	@Mod.Instance
	public static Traps instance;
	
	@SidedProxy(clientSide = "com.github.lazylazuli.traps.client.ClientProxy",
				serverSide = "com.github.lazylazuli.traps.common.CommonProxy")
	public static Proxy proxy;
	
	public Traps()
	{
		instance = this;
	}
	
	@Override
	public String getId()
	{
		return MODID;
	}
	
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
