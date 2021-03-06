package helpertools;

import java.util.Random;

import helpertools.Com.Config;
import helpertools.Utils.ForgeEventHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/** Rewrite Source for 1.8+ updates
 * https://github.com/TheGreyGhost/MinecraftByExample
 * https://bedrockminer.jimdo.com **/
@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION, guiFactory = Main.GUIFactory)
public class Main {
	
	public static final String MODID = "helpertools";
	public static final String PATH = MODID + ":";
	public static final String MODNAME = "HelperTools";
	public static final String VERSION = "v4.0b";
	public static final String GUIFactory  = "helpertools.Com.Config_Factory";
	public static final Logger logger = LogManager.getLogger(Main.MODID);

	public static ForgeEventHandler eventHandler = new ForgeEventHandler();
	public static SimpleNetworkWrapper network;
	
	@Instance
	public static Main instance = new Main();
	public static Random Randy = new Random();
	
	@SidedProxy(clientSide="helpertools.ClientProxy", serverSide="helpertools.CommonProxy")
	public static CommonProxy proxy;
	
	/////////////////////////////
	/** Initialization Chain **/
	///////////////////////////
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {		
		proxy.preInit(e);
		
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
		MinecraftForge.EVENT_BUS.register(this);	
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (eventArgs.getModID().equals(Main.MODID))
			Config.syncConfig();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}
	

	
}
