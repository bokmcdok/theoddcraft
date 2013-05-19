//--------------------------------------------------------------------------
//	Circle Generator
//		A helper class for circle generation.
//--------------------------------------------------------------------------

package deuscraft.oak;

import java.util.Random;

import net.minecraft.world.World;

//--------------------------------------------------------------------------
//	Circle Callback
//		A callable interface passed into the generate functions that tells
//		the circle generator what we want to generate at each point.
//--------------------------------------------------------------------------

interface CircleCallback 
{
	public void call(int x1, int z1);
}

public class Circle 
{
	//--------------------------------------------------------------------------
	//	Generate 
	//		Generates an unfilled circle in the x/z axis of the specified 
	//		radius.
	//
	//	Example usage:
	//		Circle.generate(x, z, 7, new GenerateBlock()
	//		{
	//			public void Call(int x1, int z1)
	//			{
	//				for(int y = mWorld.getTopSolidOrLiquidBlock(x1, z1); y < 100; ++y)
	//				{
	//					mWorld.setBlock(x1, y, z1, 98);
	//				}
	//			}
	//		});
	//--------------------------------------------------------------------------
	
	public static void generate(int x0, int z0, int radius, CircleCallback callback)
	{
		int error = -radius;
		int x = radius;
		int z = 0;
		
		while (x >= z)
		{
			generateBlocks(x0, z0, x, z, callback);
			if (x != z)
			{
				generateBlocks(x0, z0, z, x, callback);
			}
		 
			int lastZ = z;
			
			error += z;
			++z;
			error += z;
		 
			if (error >= 0)
			{				
				error -= x;
				--x;
		      	error -= x;
			}
		}
	}
	
	//--------------------------------------------------------------------------
	//	Generate Filled
	//		Generates a filled circle - where a block is generated at every 
	//		point inside the circle as well.  Usage is the same as for generate.
	//--------------------------------------------------------------------------
	
	public static void generateFilled(int x0, int z0, int radius, CircleCallback callback)
	{
		int error = -radius;
		int x = radius;
		int z = 0;
		
		while (x >= z)
		{
			generateLines(x0, z0, x, z, callback);		 
			if (x != z)
			{
				generateLines(x0, z0, z, x, callback);
			}
			
			error += z;
			++z;
			error += z;
		 
			if (error >= 0)
			{
				
				
				error -= x;
				--x;
		      	error -= x;
			}
		}
	}
	
	//--------------------------------------------------------------------------
	//	Generate Blocks
	//		Generates up to 4 blocks at each side of the circle.
	//--------------------------------------------------------------------------
	
	private static void generateBlocks(int x0, int z0, int x, int z, CircleCallback callback)
	{
	    callback.call(x0 + x, z0 + z);
		if (x != 0) 
		{
			callback.call(x0 - x, z0 + z);
		}
		
		if (z != 0)
		{
			callback.call(x0 + x, z0 - z);
		}
		
		if (x != 0 && z != 0) 
		{
			callback.call(x0 - x, z0 - z);
		}
	}
	
	//--------------------------------------------------------------------------
	//	Generate Lines
	//		Generates up to 2 lines to fill the circle.
	//--------------------------------------------------------------------------
	
	private static void generateLines(int x0, int z0, int x, int z, CircleCallback callback)
	{
		line(x0 - x, z0 + z, x0 + x, callback);
		if(x!= 0 && z != 0)
		{
			line(x0 - x, z0 - z, x0 + x, callback);
		}
	}
	
	//--------------------------------------------------------------------------
	//	Line
	//		Only draws lines along the x-axis.
	//--------------------------------------------------------------------------
	
	private static void line(int x0, int z0, int x1, CircleCallback callback)
    {
        for (int x = x0; x <= x1; ++x)
        {
            callback.call(x, z0);
        }
    }
}
