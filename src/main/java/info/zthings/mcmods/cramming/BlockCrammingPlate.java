package info.zthings.mcmods.cramming;

import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.SoundType;
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
		setHardness(0.5F);
		setSoundType(SoundType.WOOD);
	}
	
	public void setMaxWeight(int max) {
		max--; //else it'll trigger when the extra entity (that dies) is added, and turnoff when it's dead
		maxWeight = max;
	}
	
	@Override
	protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
		if (maxWeight < 0) return 0; //there is no entity limit so I can never be triggered
		
		if (CrammingDetectorMod.spreadSignalIncrease) {
			//return super.computeRedstoneStrength(worldIn, pos);
			
			/////////////////// COPIED FROM net.minecraft.block.BlockPressurePlateWeighted.computeRedstoneStrength
			int i = Math.min(worldIn.getEntitiesWithinAABB(EntityLivingBase.class, PRESSURE_AABB.offset(pos)).size(), this.maxWeight); // modified to only be sensitive to mobs
			
			if (i > 0) {
				float f = (float) Math.min(this.maxWeight, i) / (float) this.maxWeight;
				return MathHelper.ceil(f * 15.0F);
			} else return 0;
	        /////////////////// COPIED FROM net.minecraft.block.BlockPressurePlateWeighted.computeRedstoneStrength
		}
		else if(worldIn.getEntitiesWithinAABB(EntityLivingBase.class, PRESSURE_AABB.offset(pos)).size() > maxWeight) return 15;
		else return 0;
	}
}
