package deuscraft.oak;

import java.util.HashSet;
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
	
	public StructureGenerator(int spawnRate, String oakShrineBiomes[], String oakTempleBiomes[])
	{
        mWorldGeneratorTrees = new WorldGenTrees(false, 5, 0, 0, false);	
        mSpawnRate = spawnRate;
        
        for(String biome : oakShrineBiomes)
        {
        	mOakShrineBiomes.add(biome);
        }
        
        for(String biome : oakTempleBiomes)
        {
        	mOakTempleBiomes.add(biome);
        }
	}

	//--------------------------------------------------------------------------
	//	Generate
	//		Effectively the entry point for the structure generation.
	//--------------------------------------------------------------------------

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{        
		mRandom = random;
		mWorld = world;
		
		if(mRandom.nextInt(mSpawnRate) == 0)
		{
			int x = chunkX * 16 + mRandom.nextInt(16);
			int z = chunkZ * 16 + mRandom.nextInt(16);
			
			BiomeGenBase biome = mWorld.getBiomeGenForCoords(x, z);
			
			//	Plains, Extreme Hills and Swampland
			if(mOakShrineBiomes.contains(biome.biomeName))
			{
				generateOakShrine(x, z);
			}	
			
			//	Forest and Forest Hills only.
			else if(mOakTempleBiomes.contains(biome.biomeName))
			{
				if(mRandom.nextInt(2) == 0)
				{
					generateOakShrine(x, z);
				}
				else
				{
					generateOakTemple(x, z);
				}
			}
		}
	}
	
	//--------------------------------------------------------------------------
	//	Generate Random Stone
	//		A 50/50 chance that a mRandom stone will be generated.  Hopefully 
	//		this gives the stone circles a mRandom and 'ruined' look.
	//--------------------------------------------------------------------------
	
	void generateRandomStone(int x, int z)
	{
		if(mRandom.nextInt(2) == 1)
		{
			int y = mWorld.getTopSolidOrLiquidBlock(x, z) - 1;
			
			//	Ice will cause these to generate on the surface of frozen lakes,
			//	which looks terrible (Ice blockId is 79).
			while(y > 0 &&
				  (mWorld.getBlockId(x, y, z) == 79 ||
				   mWorld.getBlockId(x, y, z) == 8 ||
				   mWorld.getBlockId(x, y, z) == 9))
			{
				--y;
			}
			
			mWorld.setBlock( x, y, z, 48);
		}
	}
	
	//--------------------------------------------------------------------------
	//	Generate Stone Circle
	//		Generates a broken stone circle at ground level.
	//--------------------------------------------------------------------------

	void generateStoneCircle(int x, int z)
	{
		clearTrees(x, z, 10);
		
		Circle.generate(x, z, 9, new GenerateBlock() 
		{ 
			public void Call(int x1, int z1) 
			{ 
				generateRandomStone(x1, z1 ); 
			}
		});
	}

	//--------------------------------------------------------------------------
	//	Generate Tree Circle
	//		Generates a large circle of trees in a clearing
	//--------------------------------------------------------------------------
	
	void generateTreeCircle(int x, int z)
	{
		clearTrees(x, z, 17);

		generateTree(x - 10, z);
		generateTree(x - 10, z - 2);
		generateTree(x - 9, z - 4);
		generateTree(x - 8, z - 6);
		generateTree(x - 6, z - 8);
		generateTree(x - 4, z - 9);
		generateTree(x - 2, z - 10);
		generateTree(x, z - 10);
		generateTree(x + 2, z - 10);
		generateTree(x + 4, z - 9);
		generateTree(x + 6, z - 8);
		generateTree(x + 8, z - 6);
		generateTree(x + 9, z - 4);
		generateTree(x + 10, z - 2);
		generateTree(x + 10, z);
		generateTree(x - 10, z + 2);
		generateTree(x - 9, z + 4);
		generateTree(x - 8, z + 6);
		generateTree(x - 6, z + 8);
		generateTree(x - 4, z + 9);
		generateTree(x - 2, z + 10);
		generateTree(x, z + 10);
		generateTree(x + 2, z + 10);
		generateTree(x + 4, z + 9);
		generateTree(x + 6, z + 8);
		generateTree(x + 8, z + 6);
		generateTree(x + 9, z + 4);
		generateTree(x + 10, z + 2);
	}


	//--------------------------------------------------------------------------
	//	Generate Tree
	//		Grows a small oak tree at the specified position.
	//--------------------------------------------------------------------------
	
	void generateTree(int x, int z)
	{
		int y = mWorld.getTopSolidOrLiquidBlock(x, z);
		mWorld.setBlock(x, y, z, 0);
		mWorldGeneratorTrees.generate(mWorld, mRandom, x, y, z);		
	}
	

	//--------------------------------------------------------------------------
	//	Generate Wood Base
	//		If the block above it is a block of wood this will generate one more
	//		block beneath.
	//--------------------------------------------------------------------------
	
	void generateWoodBase(int x, int y, int z)
	{
		if(mWorld.getBlockId(x, y + 1, z) == 17)
		{
			mWorld.setBlock(x, y, z, 17);
		}
	}
	

	//--------------------------------------------------------------------------
	//	Generate Tree Shrine
	//		This generates a 3x3 tree that you can walk into and make tribute to
	//		Oak.
	//--------------------------------------------------------------------------
	
	void generateTreeShrine(int x, int z)
	{	
		int y = mWorld.getTopSolidOrLiquidBlock(x, z);
		generateTree(x, z);
		generateTree(x - 1, z);
		generateTree(x, z - 1);
		generateTree(x + 1, z);
		generateTree(x, z + 1);
		generateTree(x - 1, z + 1);
		generateTree(x - 1, z - 1);
		generateTree(x + 1, z - 1);
		generateTree(x + 1, z + 1);

		generateWoodBase(x, y - 1, z);
		generateWoodBase(x, y - 1, z + 1);
		generateWoodBase(x, y - 1, z - 1);
		generateWoodBase(x + 1, y - 1, z);
		generateWoodBase(x + 1, y - 1, z + 1);
		generateWoodBase(x + 1, y - 1, z - 1);
		generateWoodBase(x - 1, y - 1, z);
		generateWoodBase(x - 1, y - 1, z + 1);
		generateWoodBase(x - 1, y - 1, z - 1);

		mWorld.setBlock(x, y + 1, z, 0);
		mWorld.setBlock(x, y, z, 0);
		
		switch(mRandom.nextInt(4))
		{
		case 0:
			mWorld.setBlock(x, y + 1, z + 1, 0);
			mWorld.setBlock(x, y, z + 1, 0);
			break;
			
		case 1:
			mWorld.setBlock(x, y + 1, z - 1, 0);
			mWorld.setBlock(x, y, z - 1, 0);
			break;
			
		case 2:
			mWorld.setBlock(x + 1, y + 1, z, 0);
			mWorld.setBlock(x + 1, y, z, 0);
			break;
			
		case 3:
			mWorld.setBlock(x - 1, y + 1, z, 0);
			mWorld.setBlock(x - 1, y, z, 0);
			break;		
		}
	}
	
	//--------------------------------------------------------------------------
	//	Generate Oak Shrine
	//		Generates a tree shrine surrounded by a stone circle.
	//--------------------------------------------------------------------------
	
	void generateOakShrine(int x, int z)
	{
		generateStoneCircle(x, z);
		generateTreeShrine(x, z);
	}
	
	//--------------------------------------------------------------------------
	//	Generate Oak Temple
	//		Generates a tree shrine surrounded by a tree circle with the 
	//		possibility of some stone circles.
	//--------------------------------------------------------------------------
	
	void generateOakTemple(int x, int z)
	{		
		int numStoneCircles = mRandom.nextInt(3);
		for(int i = 0; i < numStoneCircles; ++i)
		{
			switch(mRandom.nextInt(8))
			{
			case 0:
				generateStoneCircle(x + 18, z);
				break;
				
			case 1:
				generateStoneCircle(x - 18, z);
				break;
				
			case 2:
				generateStoneCircle(x, z + 18);
				break;
				
			case 3:
				generateStoneCircle(x, z - 18);
				break;
				
			case 4:
				generateStoneCircle(x + 12, z + 13);
				break;
				
			case 5:
				generateStoneCircle(x + 12, z - 13);
				break;
				
			case 6:
				generateStoneCircle(x - 12, z + 13);
				break;
				
			case 7:
				generateStoneCircle(x - 12, z - 13);
				break;
			}
			
			generateTreeCircle(x, z);
			generateTreeShrine(x, z);
		}
	}
	
	//--------------------------------------------------------------------------
	//	Remove Wood
	//		Removes all wood blocks at the specified x, z coordinates.  Used to 
	//		create clearings for the Oak shrines and temples.
	//--------------------------------------------------------------------------
	
	void removeWood(int x, int z)
	{
		int y = mWorld.getHeightValue(x, z) - 1;
		
		while(mWorld.getBlockId(x, y, z) == 17 ||
			  mWorld.getBlockId(x, y, z) == 18)
		{
			if(mWorld.getBlockId(x, y, z) == 17)
			{
				mWorld.setBlock(x, y, z, 0);
			}
			
			--y;
		}
	}
	
	//--------------------------------------------------------------------------
	//	Clear Trees
	//		Clears out all trees in the specified radius.  This creates a 
	//		clearing which we can then place our shrines/temples inside. 
	//--------------------------------------------------------------------------
	
	void clearTrees(int x, int z, int radius)
	{
		Circle.generateFilled(x, z, radius, new GenerateBlock() 
		{ 
			public void Call(int x1, int z1) 
			{ 
				removeWood(x1, z1 ); 
			}
		});
	}
	
	//	Our tree generator
	private WorldGenTrees mWorldGeneratorTrees;
	private Random mRandom;
	private World mWorld;
	
	private HashSet<String> mOakShrineBiomes = new HashSet<String>();
	private HashSet<String> mOakTempleBiomes = new HashSet<String>();
    
    private int mSpawnRate;
}
