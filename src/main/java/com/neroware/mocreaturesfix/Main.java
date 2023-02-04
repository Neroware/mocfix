package com.neroware.mocreaturesfix;

import com.neroware.mocreaturesfix.proxy.CommonProxy;
import com.neroware.mocreaturesfix.util.References;

import net.minecraft.entity.EnumCreatureType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;


@Mod(modid = References.MOD_ID, name = References.NAME, version = References.VERSION)
public class Main {

	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = References.CLIENT_PROXY, serverSide = References.COMMON_PROXY)
	public static CommonProxy proxy;
	
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
		proxy.onPreInit(event);
	}
	
	
	@EventHandler
	public static void Init(FMLInitializationEvent event) {
		proxy.onInit(event);
	}
	
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
		proxy.onPostInit(event);
	}
}
