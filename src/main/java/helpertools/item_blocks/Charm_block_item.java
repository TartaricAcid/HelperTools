package helpertools.item_blocks;

import helpertools.item_blocks.Charm_block_item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Charm_block_item extends ItemBlock
{
    public final Block blocky;
    @SideOnly(Side.CLIENT)
    private IIcon icon;

    public Charm_block_item(Block block)
    {	super(block);
        this.blocky = block;
    }

    public Charm_block_item setUnlocalizedName(String unlocal)
    {
        super.setUnlocalizedName(unlocal);
        return this;
    }
    

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List par3List, boolean par4)
      {
    	par3List.add(EnumChatFormatting.DARK_RED + "Careful use advised"); 
    	par3List.add(EnumChatFormatting.ITALIC + "Boosts Bomb Radius for dispensers");      
    	par3List.add(EnumChatFormatting.ITALIC + "Place next to dispenser"); 
    	par3List.add(EnumChatFormatting.ITALIC + "Right click to toggle");
      
      }
    
    
}