package helpertools.Utils;

import helpertools.Main;
import helpertools.Com.ItemRegistry;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/** Bomb Logic **/
public class BombHelper {
	
	/** Basic Sphere shape to create **/
	public static Set<BlockPos> Sphere_Shape(BlockPos pos, int radius){
		Set<BlockPos> positions = new HashSet<BlockPos>();
		
	      for (int x = -radius; x <= radius; x++)
	      {
	          for (int y = -radius; y <= radius; y++)
	          {
	              for (int z = -radius; z <= radius; z++)
	              {
	                  if (!(x * x + y * y + z * z >= (radius + 0.50f) * (radius + 0.50f)))
	                  {
	                      positions.add(pos.add(x, y, z));
	                  }}}}
		
		return positions;
	}
	
	
	public static void Block_Sphere(World world, IBlockState block, BlockPos pos, int radius, boolean flag){
		
		for (BlockPos location : Sphere_Shape(pos, radius)) {
			int roll = Main.Randy.nextInt(100);
			if(roll <80 || flag)Place_Block(world, block, location, false);
		}
		
	}
	public static void Void_Sphere(World world, BlockPos pos, int radius){
		
		for (BlockPos location : Sphere_Shape(pos, radius)) {
			//ModUtil.dropblock(world, location);
			if(world.getBlockState(location)!= Blocks.BEDROCK.getDefaultState()){
				placer(world, location, Blocks.AIR);}
		}
	}
		
	public static void Place_Block(World world, IBlockState block, BlockPos pos, boolean flag){
		
		if(ModUtil.isValid(world, pos)){
			ModUtil.Destructables(world, pos);
			
			world.setBlockState(pos, block, 02);  			
		}
		else if(flag)ModUtil.itemdrop(world, pos, block.getBlock());
	}
	
	public static void Miracle_Sphere(World world, BlockPos pos, int radius){
		
		for (BlockPos location : Sphere_Shape(pos, radius)) {
			
			BlockPos PosAbove = location.add(0,1,0);
			IBlockState above = world.getBlockState(PosAbove);
			BlockPos PosBelow = location.add(0,-1,0);
			IBlockState below = world.getBlockState(PosBelow);	
			
			IBlockState state = world.getBlockState(location);
			
			Block target = state.getBlock();
			int roll = Main.Randy.nextInt(100);
			
			if(target == Blocks.DIRT || target == ItemRegistry.LooseDirtBlock){
				placer(world, location, Blocks.GRASS);
			}
			if(target == Blocks.COBBLESTONE){
				if(roll <= 5)placer(world, location, Blocks.GRAVEL);
				else if(roll <=9)placer(world, location, Blocks.MOSSY_COBBLESTONE);
			}
			if(target == Blocks.STONE){
				if(roll <= 35)placer(world, location, Blocks.COBBLESTONE);
			}
			if(target == Blocks.CACTUS && above.getBlock().isAir(above, world, PosAbove) && roll <= 20){
				placer(world, PosAbove, Blocks.CACTUS);				
			}
			if(target == Blocks.REEDS && above.getBlock().isAir(above, world, PosAbove) && roll <= 20){
				placer(world, PosAbove, Blocks.REEDS);				
			}
			if(target == Blocks.DEADBUSH && roll <= 24){	
				world.setBlockState(location, Blocks.SAPLING.getStateFromMeta(3));	
				Grow(world, location);
				Grow(world, location);
				Grow(world, location);
				placer(world, PosBelow, Blocks.DIRT);	
			}
			if(target == Blocks.GRASS || target == Blocks.TALLGRASS){
				if(roll <= 3)Grow(world, location);
			}
			else Grow(world, location);
			
		}
		
	}
	public static void Frost_Sphere(World world, BlockPos pos, int radius){
		
		try{
		Boolean hell = (boolean)ReflectionHelper.getPrivateValue(WorldProvider.class, world.provider, "isHellWorld");
		if(hell){radius = radius -2;}}catch(Exception e){}
		
		for (BlockPos location : Sphere_Shape(pos, radius)) {

			BlockPos PosAbove = location.add(0,1,0);
			IBlockState above = world.getBlockState(PosAbove);
			BlockPos PosBelow = location.add(0,-1,0);
			IBlockState below = world.getBlockState(PosBelow);	

			IBlockState state = world.getBlockState(location);

			Block target = state.getBlock();
			int roll = Main.Randy.nextInt(100);
			
			
			if(above.getBlock().isAir(above, world, PosAbove) && target.isBlockSolid(world, location, EnumFacing.UP)
				||	 above.getBlock().isAir(above, world, PosAbove) && state.getMaterial() == Material.LEAVES)		
				{
					placer(world, PosAbove, Blocks.SNOW_LAYER);}
			else if(target == Blocks.SNOW_LAYER){
				
				int snowlevel = state.getBlock().getMetaFromState(state);
				if(snowlevel == 7)placer(world, location, Blocks.SNOW);
				else world.setBlockState(location, Blocks.SNOW_LAYER.getStateFromMeta(snowlevel+1));
				
			}
			if(state == Blocks.FARMLAND)placer(world, location, Blocks.DIRT);
			if(state.getMaterial() == Material.PLANTS ||
					state.getMaterial() == Material.VINE){
				ModUtil.Destructables(world, location);
				placer(world, location, Blocks.AIR);				
			}
			if(target == Blocks.ICE && roll <= 8)placer(world, location, Blocks.PACKED_ICE);			
			if(target == Blocks.WATER ||target == Blocks.FLOWING_WATER&& roll <= 35 )
				placer(world, location, Blocks.ICE);
			
			if(target == Blocks.LAVA )placer(world, location, Blocks.OBSIDIAN);
			if(target == Blocks.FLOWING_LAVA && roll <= 30)placer(world, location, Blocks.COBBLESTONE);	
			if(target == Blocks.FIRE)placer(world, location, Blocks.AIR);	
			
			
			
		}
	}
	
	public static void Desert_Sphere(World world, BlockPos pos, int radius, boolean flag){
		
		for (BlockPos location : Sphere_Shape(pos, radius)) {			
			BlockPos PosAbove = location.add(0,1,0);
			IBlockState above = world.getBlockState(PosAbove);
			BlockPos PosBelow = location.add(0,-1,0);
			IBlockState below = world.getBlockState(PosBelow);	

			IBlockState state = world.getBlockState(location);
			Block target = state.getBlock();
			int roll = Main.Randy.nextInt(100);
			
			if(target == Blocks.TALLGRASS || target == Blocks.RED_FLOWER){
				if(below != target && roll <= 5)placer(world, location, Blocks.DEADBUSH);
			}
			if(target == Blocks.DEADBUSH && roll <= 10)placer(world, location, Blocks.CACTUS);
			if(target == Blocks.STONE && roll <= 50)placer(world, location, Blocks.COBBLESTONE);
			if(target == Blocks.GRASS && roll <= 50 || target == Blocks.GRASS && flag)placer(world, location, Blocks.DIRT);
			if(target == Blocks.DIRT || target == ItemRegistry.LooseDirtBlock)placer(world, location, Blocks.SAND);
			
			if(target == Blocks.STONE_STAIRS && roll <= 50){
				world.setBlockState(location, Blocks.SANDSTONE_STAIRS.getStateFromMeta(target.getMetaFromState(state)));
			}		
			if(target == Blocks.ICE && roll <= 12 ||
					target == Blocks.ICE && flag)placer(world, location, Blocks.WATER);
			if(target == Blocks.COBBLESTONE && roll <= 12)placer(world, location, Blocks.SAND);
			if(target == Blocks.COBBLESTONE && roll <= 25)placer(world, location, Blocks.SANDSTONE);
			if(target == Blocks.PACKED_ICE && roll <= 25)placer(world, location, Blocks.ICE);
			if(target == Blocks.SNOW_LAYER){
				int meta = target.getMetaFromState(state);
				if(meta >0)world.setBlockState(location, Blocks.SNOW_LAYER.getStateFromMeta(meta -1));
				else placer(world, location, Blocks.AIR);
			}			
			if(target == Blocks.SNOW){
				world.setBlockState(location, Blocks.SNOW_LAYER.getStateFromMeta(3));
			}
			
		}
	}
	
	public static void Mushroom_Sphere(World world, BlockPos pos, int radius){

		for (BlockPos location : Sphere_Shape(pos, radius)) {			
			BlockPos PosAbove = location.add(0,1,0);
			IBlockState above = world.getBlockState(PosAbove);
			BlockPos PosBelow = location.add(0,-1,0);
			IBlockState below = world.getBlockState(PosBelow);	

			IBlockState state = world.getBlockState(location);
			Block target = state.getBlock();
			int roll = Main.Randy.nextInt(100);
			
		if(state.getMaterial()== Material.PLANTS || state.getMaterial() == Material.VINE
				|| state.getMaterial() == Material.CACTUS){
			//placer(world, location, Blocks.LAVA);
			if(target != below.getBlock() && roll <10
					&& target != Blocks.BROWN_MUSHROOM && target != Blocks.RED_MUSHROOM ){
				
				if(roll <= 2){placer(world, location, Blocks.RED_MUSHROOM);}
				else{ placer(world, location, Blocks.BROWN_MUSHROOM);}
				placer(world, PosBelow, Blocks.MYCELIUM);
			}
		}
			
		
		if(target == Blocks.GRASS && roll <= 60)placer(world, location, Blocks.MYCELIUM);
		if(target == Blocks.DIRT && roll <= 6)placer(world, location, Blocks.MYCELIUM);
		if(target == Blocks.STONE && roll <= 6)placer(world, location, Blocks.COBBLESTONE);
		if(target == Blocks.BROWN_MUSHROOM || target == Blocks.RED_MUSHROOM){
			if( roll <= 12)Grow(world, location);
		}
		
		
		}
	}
	/** shortcut **/
	public static void placer(World world, BlockPos location, Block block){
		world.setBlockState(location, block.getDefaultState());	
	}	
	/** Bonemeal function **/
	public static void Grow(World world, BlockPos pos){	
		IBlockState state = world.getBlockState(pos);
		
		if (state.getBlock() instanceof IGrowable)
        {
            IGrowable igrowable = (IGrowable)state.getBlock();

            if (igrowable.canGrow(world, pos, state, world.isRemote))
            {
                if (!world.isRemote)
                {
                    if (igrowable.canUseBonemeal(world, world.rand, pos, state))
                    {
                        igrowable.grow(world, world.rand, pos, state);
                    }}}}
	}
	

}
