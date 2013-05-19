//	Sphere Generator

package deuscraft.oak;

import java.util.Hashtable;

import net.minecraft.world.World;

//--------------------------------------------------------------------------
//Sphere Callback
//	A callable interface passed into the generate functions that tells
//	the sphere generator what we want to generate at each point.
//--------------------------------------------------------------------------

interface SphereCallback
{
	void call(int x, int y, int z);
}

public class Sphere 
{
	//--------------------------------------------------------------------------
	//	Generate 
	//		Generates a hollow sphere at the specified location.
	//
	//	Example usage:
	//		Sphere.generate(x, y, z, 7, new GenerateBlock()
	//		{
	//			public void Call(int x, int y, int z)
	//			{
	//				mWorld.setBlock(x, y, z, 22);
	//			}
	//		});
	//--------------------------------------------------------------------------
	
	public static void generate(int x0, int y0, int z0, int radius, SphereCallback callback)
	{
		sSphereGenerator.pregenerate(x0, y0, z0, callback, false);
		Circle.generate(x0, z0, radius, sSphereGenerator);
	}
	
	//--------------------------------------------------------------------------
	//	Generate Filled
	//		Same as the above function only it generates a filled sphere.
	//--------------------------------------------------------------------------
	
	public static void generateFilled(int x0, int y0, int z0, int radius, SphereCallback callback)
	{
		sSphereGenerator.pregenerate(x0, y0, z0, callback, true);
		Circle.generate(x0, z0, radius, sSphereGenerator);
	}
	
	//--------------------------------------------------------------------------
	//	Sphere Generator
	//		This implements a Circle Callback that generates circles in both the
	//		x/y and y/z axes.  Each position will only be generated once so we
	//		can do some fancy stuff without worrying about multiple callbacks
	//		for a single location.
	//--------------------------------------------------------------------------
	
	private static class SphereGenerator implements CircleCallback
	{		
		public void pregenerate(int x0, int y0, int z0, SphereCallback callback, boolean filled)
		{
			mX0 = x0;
			mY0 = y0;
			mZ0 = z0;
			mCallback = callback;
			mTouched.clear();
			mFilled = filled;
		}

		public void call(int x1, int z1)
		{
			mTemp = z1;
			Circle.generate(0, mY0, x1 - mX0, new CircleCallback()
			{
				public void call(int x2, int y2)
				{
					updateBlock(mX0 + x2, y2, mTemp);
				}
			});
			
			mTemp = x1;
			if(mFilled)
			{
				Circle.generateFilled(0, mY0, z1 - mZ0, new CircleCallback()
				{
					public void call(int x2, int y2)
					{
						updateBlock(mTemp, y2, mZ0 + x2);
					}
				});
			}
			else
			{
				Circle.generate(0, 100, z1 - mZ0, new CircleCallback()
				{
					public void call(int x2, int y2)
					{
						updateBlock(mTemp, y2, mZ0 + x2);
					}
				});
			}
		}
		
		private void updateBlock(int x, int y, int z)
		{
			String key = x + " " + y + " " + " " + z;
			if(mTouched.containsKey(key))
			{
				return;
			}
			
			mTouched.put(key, true);
			
			mCallback.call(x, y, z);
		}
		
		private Hashtable<String, Boolean> mTouched = new Hashtable<String, Boolean>();
		private SphereCallback mCallback;
		private int mX0;
		private int mY0;
		private int mZ0;
		private int mTemp;
		private boolean mFilled;
	}

	private static SphereGenerator sSphereGenerator = new SphereGenerator();
}
