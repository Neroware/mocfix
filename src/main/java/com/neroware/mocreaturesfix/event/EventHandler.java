package com.neroware.mocreaturesfix.event;

import com.neroware.mocreaturesfix.config.ConfigurationSettings;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class EventHandler {
	
	
	/**
	 * This Function despawns all MoCreatures Entities depending on the given dimension id dimID
	 * @param event The entity's spawn event
	 * @param entity The spawning entity
	 * @param dimID The dimension the MoCreatures entity is not allowed to be in
	 */
	private void handleMoCreaturesInDimension(EntityJoinWorldEvent event, Entity entity, int dimID) {
		if(DimensionManager.getWorld(dimID) != event.getWorld()){
			return;
		}
		
		boolean moccreature = entity instanceof IMoCEntity;
		boolean wyvern = entity instanceof MoCEntityWyvern;
		
		if(moccreature && !wyvern) {
			event.setCanceled(true);
		}
	}
	
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();
		
		// Inserted due to JellyFish Error
		if(entity instanceof MoCEntityJellyFish) {
			event.setCanceled(true);
		}
		
		handleMoCreaturesInDimension(event, entity, ConfigurationSettings.WYVERNLAIR_DIM_ID);
		for(int dimID : ConfigurationSettings.BLACKLISTED_DIMENSIONS) {
			handleMoCreaturesInDimension(event, entity, dimID);
		}
	}
	
}
