package info.zthings.mcmods.cramming;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import info.zthings.mcmods.cramming.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.command.CommandGameRule;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = CrammingDetectorMod.MODID, version = CrammingDetectorMod.VERSION)
@EventBusSubscriber
public class CrammingDetectorMod {
	public static final String MODID = "cramming";
	public static final String VERSION = "1.0";
	
	@Instance("cramming")
	public static CrammingDetectorMod instance;
	
	@SidedProxy(serverSide = "info.zthings.mcmods.cramming.proxy.CommonProxy", clientSide = "info.zthings.mcmods.cramming.proxy.ClientProxy")
	public static CommonProxy proxy;
	
	public static BlockCrammingPlate plate;
	public static Item plateItem;
	public static boolean spreadSignalIncrease;
	
	@EventHandler
	public void init(FMLPreInitializationEvent event) {
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try {
			spreadSignalIncrease = cfg.getBoolean("spreadSignalIncrease", "cramming", false, "When false, the cramming plate ONLY outputs a redstone signal when the maxEntityCramming has been reached. When true, the increase in signal strength is spread out like the other pressure plates");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cfg.hasChanged()) cfg.save();
		}
		MinecraftForge.EVENT_BUS.register(this);
		
		plate = new BlockCrammingPlate();
		plateItem = new ItemBlock(plate).setRegistryName(plate.getRegistryName());
	}
	
	
	
	//Event handling
	@SubscribeEvent
	public void updatedGameRule(CommandEvent ev) {
		if (!(ev.getCommand() instanceof CommandGameRule) || !ev.getParameters()[0].equals("maxEntityCramming")) return;
		if (ev.getParameters().length > 1) plate.setMaxWeight(Integer.valueOf(ev.getParameters()[1]));
	}
	
	@EventHandler
	public void onWorldLoad(FMLServerStartedEvent event) {
		MinecraftServer minecraftServer = new FakePlayer(DimensionManager.getWorld(0), new GameProfile(UUID.randomUUID(), "FakePlayer")).mcServer;
		plate.setMaxWeight(minecraftServer.getServer().getEntityWorld().getGameRules().getInt("maxEntityCramming"));
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(plateItem);
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(plate);
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		CrammingDetectorMod.proxy.registerItemRenderer(plateItem, 0, plate.getUnlocalizedName().substring(5));
	}
	
}
