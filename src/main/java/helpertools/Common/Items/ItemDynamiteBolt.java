package helpertools.Common.Items;

import helpertools.Utils.HelpTab;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemDynamiteBolt extends Item {

	  public ItemDynamiteBolt(String unlocalizedName) {
	       super();
	       this.maxStackSize = 16; 
	       this.setUnlocalizedName(unlocalizedName);
	       setCreativeTab(HelpTab.HelperTools);
	       
	   }
	  @Override
	    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	    {
	    par3List.add(EnumChatFormatting.GRAY + "*Danger-Explosive*");
	    }
}