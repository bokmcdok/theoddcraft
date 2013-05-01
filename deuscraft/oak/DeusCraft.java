//------------------------------------------------------------------------------
//	Deus Craft
//		Base file for the entire mod that registers any new items, blocks, 
//		structures and so on.
//
//	Created: 12/04/2013
//------------------------------------------------------------------------------

package deuscraft.oak;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="DeusCraft", name="Deus Craft", version="0.0.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class DeusCraft 
{
	@Instance("DeusCraft")
	public static DeusCraft Instance;
	
	@SidedProxy(clientSide="deuscraft.oak.ClientProxy", serverSide="deuscraft.oak.CommonProxy")
	public static CommonProxy proxy;

	//------------------------------------------------------------------------------
	//	Pre Init
	//------------------------------------------------------------------------------

	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
	}

	//------------------------------------------------------------------------------
	//	Init
	//------------------------------------------------------------------------------

	@Init
	public void load(FMLInitializationEvent event)
	{
		GameRegistry.registerWorldGenerator(mStructureGenerator);
    }

	//------------------------------------------------------------------------------
	//	Post Init
	//------------------------------------------------------------------------------

	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
	}
	
	public static StructureGenerator mStructureGenerator = new StructureGenerator();
}
