package com.neroware.mocreaturesfix.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.neroware.mocreaturesfix.util.References;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {

	private static Configuration config;
	  
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID().equalsIgnoreCase(References.MOD_ID)) {
			loadConfiguration(); 
		}
	}
	
	
	private static void loadConfiguration() {
	    loadMobConfiguration();
	    if (config.hasChanged()) {
	    	config.save(); 
	    }
	}
	
	
	private static void loadMobConfiguration() {
		List<Integer> blacklistedDims = new ArrayList<>();
		
		for(String cat : config.getCategoryNames()) {
			ConfigCategory category = config.getCategory(cat);
		    if (category == null) {
		    	continue; 
		    }
			
		    String dimName = "";
		    for(int i = cat.length() - 1; i >= 0; i--) {
		    	char c = cat.charAt(i);
		    	if(c == '.') {
		    		break;
		    	}
		    	dimName = c + dimName;
		    }
			
		    String dimIDStr = category.getValues().get("Dim-ID").getString();
			int dimID = Integer.parseInt(dimIDStr.substring(0, dimIDStr.length() - 2));
			if(dimName.equals("wyvernlair")) {
				ConfigurationSettings.WYVERNLAIR_DIM_ID = dimID;
			}
			else {
				blacklistedDims.add(dimID);
			}
		}
		
		int[] blacklistedDimsArr = new int[blacklistedDims.size()];
		for(int i = 0; i < blacklistedDims.size(); i++) {
			blacklistedDimsArr[i] = blacklistedDims.get(i);
		}
		ConfigurationSettings.BLACKLISTED_DIMENSIONS = blacklistedDimsArr;
	}


	public static Configuration getConfig() {
		return config;
	}
	
	
	public static void init(File configFile) {
		if (config == null) {
			config = new Configuration(configFile);
		}
		loadConfiguration();
	}	
}
