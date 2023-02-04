package com.neroware.mocreaturesfix;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.neroware.mocreaturesfix.config.ConfigurationSettings;

import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class MocfixSpawner {
	public void updateOceanSpawns(TickEvent.WorldTickEvent event, World world, EntityPlayer player, Class entityClass) {
		Random random = new Random();
		if(random.nextInt(28) != 0) {
			return;
		}
		
		int alreadySpawned = 0;
		List<Entity> loadedEntities = new ArrayList<>(world.loadedEntityList);
		for(Entity entity : loadedEntities) {
			if(!(entity instanceof MoCEntityAquatic)) {
				continue;
			}
			MoCEntityAquatic aquatic = (MoCEntityAquatic) entity;
			Vec3d playerPos = player.getPositionVector();
			Vec3d aquaticPos = aquatic.getPositionVector();
			Vec3d v = playerPos.subtract(aquaticPos);
			double d = Math.sqrt(v.x * v.x + v.z + v.z);
			if(d < ConfigurationSettings.SPAWN_COUNTING_CREATURE_DIST) {
				alreadySpawned++;
			}
		}
		if(alreadySpawned > ConfigurationSettings.SPAWN_LIMIT_CREATURE_COUNT) {
			return;
		}
		
		if(entityClass.getName().equals(MoCEntityShark.class.getName()) || entityClass.getName().equals(MoCEntityDolphin.class.getName())) {
			if(random.nextInt(3) == 0) {
				return;
			}
		}
		
		BlockPos playerPos = player.getPosition();
		int deltaX = random.nextInt(16);
		int deltaZ = random.nextInt(16);
		int factX = random.nextInt(2);
		if(factX == 0) {
			factX = -1;
		}
		int factZ = random.nextInt(2);
		if(factZ == 0) {
			factZ = -1;
		}
		int x = playerPos.getX() + factX * (20 + deltaX);
		int y = ConfigurationSettings.WATER_LEVEL - 2;
		int z = playerPos.getZ() + factZ * (20 + deltaZ);
		BlockPos pos = new BlockPos(x, y, z);
		
		Block block = world.getBlockState(pos).getBlock();
		if(Block.getIdFromBlock(block) != Block.getIdFromBlock(Blocks.WATER)) {
			return;
		}
		Biome biome = world.getBiome(pos);
		if(biome != Biomes.DEEP_OCEAN && biome != Biomes.OCEAN && biome != Biomes.BEACH) {
			return;
		}
		
		if(entityClass.getName().equals(MoCEntityFishy.class.getName())) {
			MoCEntityFishy fishy = new MoCEntityFishy(world);
			fishy.selectType();
			fishy.setLocationAndAngles(x, y, z, 0, 0);
			world.spawnEntity(fishy);
			return;
		}
		
		try {
			Constructor cons = entityClass.getConstructor(World.class);
			MoCEntityAquatic entity = (MoCEntityAquatic) cons.newInstance(world);
			entity.setLocationAndAngles(x, y, z, 0, 0);
			world.spawnEntity(entity);
			
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println("Mocfix Spawner f'ed up! This happened!??? \\(°°)/");
			return;
		}
	}
	
	
	public void updateRiverSpawns(TickEvent.WorldTickEvent event, World world, EntityPlayer player, Class entityClass) {
		Random random = new Random();
		if(random.nextInt(12) != 0) {
			return;
		}
		
		int alreadySpawned = 0;
		List<Entity> loadedEntities = new ArrayList<>(world.loadedEntityList);
		for(Entity entity : loadedEntities) {
			if(!(entity instanceof MoCEntityAquatic)) {
				continue;
			}
			MoCEntityAquatic aquatic = (MoCEntityAquatic) entity;
			Vec3d playerPos = player.getPositionVector();
			Vec3d aquaticPos = aquatic.getPositionVector();
			Vec3d v = playerPos.subtract(aquaticPos);
			double d = Math.sqrt(v.x * v.x + v.z + v.z);
			if(d < ConfigurationSettings.SPAWN_COUNTING_CREATURE_DIST) {
				alreadySpawned++;
			}
		}
		if(alreadySpawned > ConfigurationSettings.SPAWN_LIMIT_CREATURE_COUNT) {
			return;
		}
		
		BlockPos playerPos = player.getPosition();
		int deltaX = random.nextInt(16);
		int deltaZ = random.nextInt(16);
		int factX = random.nextInt(2);
		if(factX == 0) {
			factX = -1;
		}
		int factZ = random.nextInt(2);
		if(factZ == 0) {
			factZ = -1;
		}
		int x = playerPos.getX() + factX * (8 + deltaX);
		int y = ConfigurationSettings.WATER_LEVEL - 2;
		int z = playerPos.getZ() + factZ * (8 + deltaZ);
		BlockPos pos = new BlockPos(x, y, z);
		
		Block block = world.getBlockState(pos).getBlock();
		if(Block.getIdFromBlock(block) != Block.getIdFromBlock(Blocks.WATER)) {
			return;
		}
		Biome biome = world.getBiome(pos);
		if(biome == Biomes.DEEP_OCEAN || biome == Biomes.OCEAN || biome == Biomes.BEACH) {
			return;
		}
		
		try {
			Constructor cons = entityClass.getConstructor(World.class);
			MoCEntityAquatic entity = (MoCEntityAquatic) cons.newInstance(world);
			entity.setLocationAndAngles(x, y, z, 0, 0);
			world.spawnEntity(entity);
			
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println("Mocfix Spawner f'ed up! This happened!??? \\(°°)/");
			return;
		}
	}
	
}
