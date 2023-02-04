package com.neroware.mocreaturesfix.proxy;

import com.neroware.mocreaturesfix.MocfixSpawner;
import com.neroware.mocreaturesfix.config.ConfigurationHandler;
import com.neroware.mocreaturesfix.event.EventHandler;
import com.neroware.mocreaturesfix.event.TickHandler;

import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import drzhark.mocreatures.entity.aquatic.*;
import drzhark.mocreatures.init.MoCBiomes;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;


public class CommonProxy {
	
	
	private void registerEntitySpawns() {
		EntityRegistry.addSpawn(MoCEntityWyvern.class, 10, 1, 4, EnumCreatureType.AMBIENT, MoCBiomes.WyvernLairBiome);
	}
	
	
	public void onPreInit(FMLPreInitializationEvent e) {
		ConfigurationHandler.init(e.getSuggestedConfigurationFile());
		registerEvents();
	}
	  
	
	public void onInit(FMLInitializationEvent e) {
		registerEntitySpawns();
	}
	  

	public void onPostInit(FMLPostInitializationEvent e) {
		
	}
	
	
	protected void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		MinecraftForge.EVENT_BUS.register(new TickHandler(new MocfixSpawner()));
	}
}
