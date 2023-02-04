package com.neroware.mocreaturesfix.event;

import java.util.ArrayList;
import java.util.List;

import com.neroware.mocreaturesfix.MocfixSpawner;

import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.aquatic.MoCEntityAnchovy;
import drzhark.mocreatures.entity.aquatic.MoCEntityAngelFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityAngler;
import drzhark.mocreatures.entity.aquatic.MoCEntityBass;
import drzhark.mocreatures.entity.aquatic.MoCEntityClownFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityCod;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityGoldFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityHippoTang;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityManderin;
import drzhark.mocreatures.entity.aquatic.MoCEntityMantaRay;
import drzhark.mocreatures.entity.aquatic.MoCEntityPiranha;
import drzhark.mocreatures.entity.aquatic.MoCEntityRay;
import drzhark.mocreatures.entity.aquatic.MoCEntitySalmon;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import drzhark.mocreatures.entity.aquatic.MoCEntityStingRay;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class TickHandler {

	private MocfixSpawner spawner;
	private int tickCounter = 0;
	
	
	public TickHandler(MocfixSpawner spawner) {
		this.spawner = spawner;
	}
	
	
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		World world = event.world;
		if(world.provider.getDimension() != 0) {
			return;
		}
		
		if(tickCounter > 0) {
			tickCounter--;
			return;
		}
		tickCounter = 64;
		
		List<Entity> loadedEntities = new ArrayList<>(world.loadedEntityList);
		List<EntityPlayer> players = new ArrayList<>();
		for(Entity entity : loadedEntities) {
			if(entity instanceof EntityPlayer) {
				players.add((EntityPlayer) entity);
			}
		}
		
		Class[] oceanEntities = new Class[] {
			MoCEntityAnchovy.class,
			MoCEntityAngelFish.class,
			MoCEntityAngler.class,
			MoCEntityBass.class,
			MoCEntityClownFish.class,
			MoCEntityCod.class,
			MoCEntityDolphin.class,
			MoCEntityFishy.class,
			MoCEntityHippoTang.class,
			/*MoCEntityJellyFish.class,*/ // JellyFish Server Bug
			MoCEntityManderin.class,
			MoCEntityMantaRay.class,
			MoCEntityPiranha.class,
			MoCEntityRay.class,
			MoCEntitySalmon.class,
			MoCEntityShark.class,
			MoCEntityStingRay.class
		};
		Class[] riverEntities = new Class[] {
			MoCEntityGoldFish.class,
			MoCEntityBass.class,
			MoCEntitySalmon.class,
			MoCEntityGoldFish.class,
			MoCEntityBass.class,
			MoCEntitySalmon.class
		};
		
		for(EntityPlayer player : players) {
			for(Class oceanEntity : oceanEntities) {
				spawner.updateOceanSpawns(event, world, player, oceanEntity);
			}
			for(Class riverEntity : riverEntities) {
				spawner.updateRiverSpawns(event, world, player, riverEntity);
			}
		}
	}
	
}
