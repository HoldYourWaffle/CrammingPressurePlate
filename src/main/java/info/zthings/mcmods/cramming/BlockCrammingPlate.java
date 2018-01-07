package info.zthings.mcmods.cramming;

import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BlockCrammingPlate extends BlockPressurePlateWeighted {
	private final String name = "crammingplate";
	protected int maxWeight;
	
	public BlockCrammingPlate() {
		super(Material.IRON, 24, MapColor.EMERALD); // start with the default mob-craming
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.REDSTONE);
	}
	
	public void setMaxWeight(int max) {
		max--; //else it'll trigger when the extra entity (that dies) is added, and turnoff when it's dead
		maxWeight = max;
		// STUB do reflection stuff on super.maxWeight
	}
	
	//FIXME hardness/resistance
	//FIXME only be sensitive to mobs (requires full override)
	@Override
	protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
		if (maxWeight < 0) return 0; //there is no entity limit so I can never be triggered
		
		if (CrammingDetectorMod.spreadSignalIncrease) return super.computeRedstoneStrength(worldIn, pos);
		else if(worldIn.getEntitiesWithinAABB(EntityLivingBase.class, PRESSURE_AABB.offset(pos)).size() > maxWeight) return 15;
		else return 0;
	}
}
