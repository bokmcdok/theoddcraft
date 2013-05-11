//--------------------------------------------------------------------------
//	Circle Generator
//		A helper class for circle generation.
//--------------------------------------------------------------------------

package deuscraft.oak;

import java.util.Random;

import net.minecraft.world.World;

interface GenerateBlock 
{
	public void Call(int x1, int z1);
}

public class Circle 
{
	public static void generate(int x0, int z0, int radius, GenerateBlock callback)
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
	
	private static void generateBlocks(int x0, int z0, int x, int z, GenerateBlock callback)
	{
	    callback.Call(x0 + x, z0 + z);
		if (x != 0) 
		{
			callback.Call(x0 - x, z0 + z);
		}
		
		if (z != 0)
		{
			callback.Call(x0 + x, z0 - z);
		}
		
		if (x != 0 && z != 0) 
		{
			callback.Call(x0 - x, z0 - z);
		}
	}
	
	public static void generateFilled(int x0, int z0, int radius, GenerateBlock callback)
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
	
	private static void generateLines(int x0, int z0, int x, int z, GenerateBlock callback)
	{
		line(x0 - x, z0 + z, x0 + x, callback);
		if(x!= 0 && z != 0)
		{
			line(x0 - x, z0 - z, x0 + x, callback);
		}
	}
	
	private static void line(int x0, int z0, int x1, GenerateBlock callback)
    {
        for (int x = x0; x <= x1; ++x)
        {
            callback.Call(x, z0);
        }
    }
}
