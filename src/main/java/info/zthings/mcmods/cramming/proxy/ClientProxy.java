package info.zthings.mcmods.cramming.proxy;

import info.zthings.mcmods.cramming.CrammingDetectorMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(CrammingDetectorMod.MODID + ":" + id, "inventory"));
	}
	
}
