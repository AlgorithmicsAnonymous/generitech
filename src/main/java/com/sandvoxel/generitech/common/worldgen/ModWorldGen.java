package com.sandvoxel.generitech.common.worldgen;

import com.sandvoxel.generitech.common.blocks.Blocks;
import com.sandvoxel.generitech.common.enumtypes.EnumOres;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class ModWorldGen implements IWorldGenerator{

    private WorldGenerator copperOre;
    private WorldGenerator tinOre;
    private WorldGenerator leadOre;

    public ModWorldGen(){
        this.copperOre = new ModWorldGenMinable(Blocks.BLOCK_ORE.getBlock().getStateFromMeta(EnumOres.COPPER.getMeta()), 8);
        this.tinOre = new ModWorldGenMinable(Blocks.BLOCK_ORE.getBlock().getStateFromMeta(EnumOres.TIN.getMeta()), 8);
        this.leadOre = new ModWorldGenMinable(Blocks.BLOCK_ORE.getBlock().getStateFromMeta(EnumOres.LEAD.getMeta()), 8);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        switch (world.provider.getDimension()){
            case 0: //Overworld
                this.runGenerator(this.copperOre, world, random, chunkX, chunkZ, 20, 0, 228);
                this.runGenerator(this.tinOre, world, random, chunkX, chunkZ, 20, 0, 228);
                this.runGenerator(this.leadOre, world, random, chunkX, chunkZ, 20, 0, 228);
            break;
            case -1: //Nether

            break;
            case 1: //The End

            break;
        }
    }

    private void runGenerator (WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ, int chanceToSpawn, int minHeight, int maxHeight){
        if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
            throw new IllegalArgumentException("Minimum or Max height out of bounds");

        int heightDiff = maxHeight - minHeight + 1;
        for (int i = 0; i < chanceToSpawn; i++) {
            int x = chunkX * 16 + rand.nextInt(16);
            int y = minHeight + rand.nextInt(heightDiff);
            int z = chunkZ * 16 + rand.nextInt(16);
            generator.generate(world, rand, new BlockPos(x, y, z));
        }
    }

}
