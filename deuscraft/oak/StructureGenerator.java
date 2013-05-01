package deuscraft.oak;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenTrees;
import cpw.mods.fml.common.IWorldGenerator;

public class StructureGenerator implements IWorldGenerator
{
	//--------------------------------------------------------------------------
	//	Construction
	//--------------------------------------------------------------------------
	
	public StructureGenerator()
	{
        this.worldGeneratorTrees = new WorldGenTrees(false, 5, 0, 0, false);		
	}

	//--------------------------------------------------------------------------
	//	Generate
	//		Effectively the entry point for the structure generation.
	//--------------------------------------------------------------------------
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{        
		if(random.nextInt(192) == 0)
		{
			int x = chunkX * 16 + random.nextInt(16);
			int z = chunkZ * 16 + random.nextInt(16);
			
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
			
			//	Plains, Extreme Hills and Swampland
			if(biome.biomeID == 1 ||
			   biome.biomeID == 3 ||
			   biome.biomeID == 6)
			{
				generateOakShrine(random, x, z, world);
			}	
			
			//	Forest and Forest Hills only.
			else if(biome.biomeID == 4 ||
					biome.biomeID == 18)
			{
				if(random.nextInt(2) == 0)
				{
					generateOakShrine(random, x, z, world);
				}
				else
				{
					generateOakTemple(random, x, z, world);
				}
			}
		}
	}
	
	//--------------------------------------------------------------------------
	//	Generate Random Stone
	//		A 50/50 chance that a random stone will be generated.  Hopefully 
	//		this gives the stone circles a random and 'ruined' look.
	//--------------------------------------------------------------------------
	
	void generateRandomStone(Random random, World world, int x, int z)
	{
		if(random.nextInt(2) == 1)
		{
			int y = world.getTopSolidOrLiquidBlock(x, z) - 1;
			
			//	Ice will cause these to generate on the surface of frozen lakes,
			//	which looks terrible (Ice blockId is 79).
			while(y > 0 &&
				  (world.getBlockId(x, y, z) == 79 ||
				   world.getBlockId(x, y, z) == 8 ||
				   world.getBlockId(x, y, z) == 9))
			{
				--y;
			}
			
			world.setBlock( x, y, z, 48);
		}
	}
	
	//--------------------------------------------------------------------------
	//	Generate Stone Circle
	//		Generates a broken stone circle at ground level.
	//--------------------------------------------------------------------------

	void generateStoneCircle(Random random, int x, int z, World world)
	{
		clearTrees(x, z, 10, world);		
		
		generateRandomStone(random, world, x - 7, z + 0);
		generateRandomStone(random, world, x - 7, z - 1);
		generateRandomStone(random, world, x - 7, z - 2);
		generateRandomStone(random, world, x - 6, z - 3);
		generateRandomStone(random, world, x - 6, z - 4);
		generateRandomStone(random, world, x - 5, z - 5);
		generateRandomStone(random, world, x - 4, z - 6);
		generateRandomStone(random, world, x - 3, z - 6);
		generateRandomStone(random, world, x - 2, z - 7);	
		generateRandomStone(random, world, x - 1, z - 7);
		generateRandomStone(random, world, x + 0, z - 7);
		generateRandomStone(random, world, x + 1, z - 7);
		generateRandomStone(random, world, x + 2, z - 7);
		generateRandomStone(random, world, x + 3, z - 6);
		generateRandomStone(random, world, x + 4, z - 6);
		generateRandomStone(random, world, x + 5, z - 5);
		generateRandomStone(random, world, x + 6, z - 4);
		generateRandomStone(random, world, x + 6, z - 3);
		generateRandomStone(random, world, x + 7, z - 2);
		generateRandomStone(random, world, x + 7, z - 1);
		generateRandomStone(random, world, x + 7, z + 0);
		generateRandomStone(random, world, x - 7, z + 1);
		generateRandomStone(random, world, x - 7, z + 2);
		generateRandomStone(random, world, x - 6, z + 3);
		generateRandomStone(random, world, x - 6, z + 4);
		generateRandomStone(random, world, x - 5, z + 5);
		generateRandomStone(random, world, x - 4, z + 6);
		generateRandomStone(random, world, x - 3, z + 6);
		generateRandomStone(random, world, x - 2, z + 7);	
		generateRandomStone(random, world, x - 1, z + 7);
		generateRandomStone(random, world, x + 0, z + 7);
		generateRandomStone(random, world, x + 1, z + 7);
		generateRandomStone(random, world, x + 2, z + 7);
		generateRandomStone(random, world, x + 3, z + 6);
		generateRandomStone(random, world, x + 4, z + 6);
		generateRandomStone(random, world, x + 5, z + 5);
		generateRandomStone(random, world, x + 6, z + 4);
		generateRandomStone(random, world, x + 6, z + 3);
		generateRandomStone(random, world, x + 7, z + 2);
		generateRandomStone(random, world, x + 7, z + 1);
	}

	//--------------------------------------------------------------------------
	//	Generate Tree Circle
	//		Generates a large circle of trees in a clearing
	//--------------------------------------------------------------------------
	
	void generateTreeCircle(Random random, int x, int z, World world)
	{
		clearTrees(x, z, 15, world);

		generateTree(random, x - 10, z, world);
		generateTree(random, x - 10, z - 2, world);
		generateTree(random, x - 9, z - 4, world);
		generateTree(random, x - 8, z - 6, world);
		generateTree(random, x - 6, z - 8, world);
		generateTree(random, x - 4, z - 9, world);
		generateTree(random, x - 2, z - 10, world);
		generateTree(random, x, z - 10, world);
		generateTree(random, x + 2, z - 10, world);
		generateTree(random, x + 4, z - 9, world);
		generateTree(random, x + 6, z - 8, world);
		generateTree(random, x + 8, z - 6, world);
		generateTree(random, x + 9, z - 4, world);
		generateTree(random, x + 10, z - 2, world);
		generateTree(random, x + 10, z, world);
		generateTree(random, x - 10, z + 2, world);
		generateTree(random, x - 9, z + 4, world);
		generateTree(random, x - 8, z + 6, world);
		generateTree(random, x - 6, z + 8, world);
		generateTree(random, x - 4, z + 9, world);
		generateTree(random, x - 2, z + 10, world);
		generateTree(random, x, z + 10, world);
		generateTree(random, x + 2, z + 10, world);
		generateTree(random, x + 4, z + 9, world);
		generateTree(random, x + 6, z + 8, world);
		generateTree(random, x + 8, z + 6, world);
		generateTree(random, x + 9, z + 4, world);
		generateTree(random, x + 10, z + 2, world);
	}

	//--------------------------------------------------------------------------
	//	Generate Tree
	//		Grows a small oak tree at the specified position.
	//--------------------------------------------------------------------------
	
	void generateTree(Random random, int x, int z, World world)
	{
		int y = world.getTopSolidOrLiquidBlock(x, z);
		world.setBlock(x, y, z, 0);
		worldGeneratorTrees.generate(world, random, x, y, z);		
	}
	

	//--------------------------------------------------------------------------
	//	Generate Wood Base
	//		If the block above it is a block of wood this will generate one more
	//		block beneath.
	//--------------------------------------------------------------------------
	
	void generateWoodBase(int x, int y, int z, World world)
	{
		if(world.getBlockId(x, y + 1, z) == 17)
		{
			world.setBlock(x, y, z, 17);
		}
	}
	

	//--------------------------------------------------------------------------
	//	Generate Tree Shrine
	//		This generates a 3x3 tree that you can walk into and make tribute to
	//		Oak.
	//--------------------------------------------------------------------------
	
	void generateTreeShrine(Random random, int x, int z, World world)
	{	
		int y = world.getTopSolidOrLiquidBlock(x, z);
		generateTree(random, x, z, world);
		generateTree(random, x - 1, z, world);
		generateTree(random, x, z - 1, world);
		generateTree(random, x + 1, z, world);
		generateTree(random, x, z + 1, world);
		generateTree(random, x - 1, z + 1, world);
		generateTree(random, x - 1, z - 1, world);
		generateTree(random, x + 1, z - 1, world);
		generateTree(random, x + 1, z + 1, world);

		generateWoodBase(x, y - 1, z, world);
		generateWoodBase(x, y - 1, z + 1, world);
		generateWoodBase(x, y - 1, z - 1, world);
		generateWoodBase(x + 1, y - 1, z, world);
		generateWoodBase(x + 1, y - 1, z + 1, world);
		generateWoodBase(x + 1, y - 1, z - 1, world);
		generateWoodBase(x - 1, y - 1, z, world);
		generateWoodBase(x - 1, y - 1, z + 1, world);
		generateWoodBase(x - 1, y - 1, z - 1, world);

		world.setBlock(x, y + 1, z, 0);
		world.setBlock(x, y, z, 0);
		
		switch(random.nextInt(4))
		{
		case 0:
			world.setBlock(x, y + 1, z + 1, 0);
			world.setBlock(x, y, z + 1, 0);
			break;
			
		case 1:
			world.setBlock(x, y + 1, z - 1, 0);
			world.setBlock(x, y, z - 1, 0);
			break;
			
		case 2:
			world.setBlock(x + 1, y + 1, z, 0);
			world.setBlock(x + 1, y, z, 0);
			break;
			
		case 3:
			world.setBlock(x - 1, y + 1, z, 0);
			world.setBlock(x - 1, y, z, 0);
			break;		
		}
	}
	
	//--------------------------------------------------------------------------
	//	Generate Oak Shrine
	//		Generates a tree shrine surrounded by a stone circle.
	//--------------------------------------------------------------------------
	
	void generateOakShrine(Random random, int x, int z, World world)
	{
		generateStoneCircle(random, x, z, world);
		generateTreeShrine(random, x, z, world);
	}
	
	//--------------------------------------------------------------------------
	//	Generate Oak Temple
	//		Generates a tree shrine surrounded by a tree circle with the 
	//		possibility of some stone circles.
	//--------------------------------------------------------------------------
	
	void generateOakTemple(Random random, int x, int z, World world)
	{		
		int numStoneCircles = random.nextInt(3);
		for(int i = 0; i < numStoneCircles; ++i)
		{
			switch(random.nextInt(8))
			{
			case 0:
				generateStoneCircle(random, x + 18, z, world);
				break;
				
			case 1:
				generateStoneCircle(random, x - 18, z, world);
				break;
				
			case 2:
				generateStoneCircle(random, x, z + 18, world);
				break;
				
			case 3:
				generateStoneCircle(random, x, z - 18, world);
				break;
				
			case 4:
				generateStoneCircle(random, x + 12, z + 13, world);
				break;
				
			case 5:
				generateStoneCircle(random, x + 12, z - 13, world);
				break;
				
			case 6:
				generateStoneCircle(random, x - 12, z + 13, world);
				break;
				
			case 7:
				generateStoneCircle(random, x - 12, z - 13, world);
				break;
			}
			
			generateTreeCircle(random, x, z, world);
			generateTreeShrine(random, x, z, world);
		}
	}
	
	//--------------------------------------------------------------------------
	//	Remove Wood
	//		Removes all wood blocks at the specified x, z coordinates.  Used to 
	//		create clearings for the Oak shrines and temples.
	//--------------------------------------------------------------------------
	
	void removeWood(int x, int z, World world)
	{
		int y = world.getHeightValue(x, z) - 1;
		while(world.getBlockId(x, y, z) == 17 ||
			  world.getBlockId(x, y, z) == 18)
		{
			if(world.getBlockId(x, y, z) == 17)
			{
				world.setBlock(x, y, z, 0);
			}
			
			--y;
		}
	}
	
	//--------------------------------------------------------------------------
	//	Clear Trees
	//		Clears out all trees in the specified radius.  This creates a 
	//		clearing which we can then place our shrines/temples inside. 
	//--------------------------------------------------------------------------
	
	void clearTrees(int x, int z, int radius, World world)
	{
		int radiusSquared = radius * radius;
		for(int i = radius * -1; i <= radius; ++i)
		{
			for(int j = radius * -1; j <= radius; ++j)
			{
				if(((i * i) + (j * j)) <= radiusSquared)
				{
					removeWood(x + i, z + j, world);
				}
			}
		}
	}
	
	//	Our tree generator
    protected WorldGenTrees worldGeneratorTrees;
}
