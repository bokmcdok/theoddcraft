package deuscraft.oak;

public class Line 
{
	public static void generate(int x0, int z0, int x1, int z1, GenerateBlock generate)
	{
		int dx = Math.abs(x1 - x0);
		int dz = Math.abs(z1 - z0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sz = z0 < z1 ? 1 : -1;
		
		int error = dx - dz;
		
		while(true)
		{
			generate.Call(x0, z0);
			if(x0 == x1 &&
			   z0 == z1) 
			{
				break;
			}
			
			int error2 = 2 * error;
			if(error2 > -dz)
			{
				error -= dz;
				x0 += sx;
			}
			
			if(x0 == x1 &&
			   z0 == z1)
			{
				generate.Call(x0, z0);
				break;
			}
			
			if(error2 < dx)
			{
				error += dx;
				z0 += sz;
			}
		}
	}
}
