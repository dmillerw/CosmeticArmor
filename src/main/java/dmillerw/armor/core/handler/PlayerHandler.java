package dmillerw.armor.core.handler;

import com.google.common.io.Files;
import dmillerw.armor.core.inventory.InventoryArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

public class PlayerHandler {

    private static HashMap<String, InventoryArmor> playerArmor = new HashMap<String, InventoryArmor>();

    public static void clearArmor(EntityPlayer player) {
        playerArmor.remove(player.getCommandSenderName());
    }

    public static InventoryArmor getArmor(EntityPlayer player) {
        if (!playerArmor.containsKey(player.getCommandSenderName())) {
            InventoryArmor inventory = new InventoryArmor(player);
            playerArmor.put(player.getCommandSenderName(), inventory);
        }
        return playerArmor.get(player.getCommandSenderName());
    }

    public static void setArmor(EntityPlayer player, InventoryArmor inventory) {
        playerArmor.put(player.getCommandSenderName(), inventory);
    }

    public static void loadPlayerArmor(EntityPlayer player, File file1, File file2) {
        if (player != null && !player.worldObj.isRemote) {
            try {
                NBTTagCompound data = null;
                boolean save = false;
                if (file1 != null && file1.exists()) {
                    try {
                        FileInputStream fileinputstream = new FileInputStream(file1);
                        data = CompressedStreamTools.readCompressed(fileinputstream);
                        fileinputstream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (file1 == null || !file1.exists() || data == null || data.hasNoTags()) {
                    if (file2 != null && file2.exists()) {
                        try {
                            FileInputStream fileinputstream = new FileInputStream(file2);
                            data = CompressedStreamTools.readCompressed(fileinputstream);
                            fileinputstream.close();
                            save = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (data != null) {
                    InventoryArmor inventory = new InventoryArmor(player);
                    inventory.readFromNBT(data);
                    playerArmor.put(player.getCommandSenderName(), inventory);
                    if (save)
                        savePlayerArmor(player, file1, file2);
                }
            } catch (Exception exception1) {
                exception1.printStackTrace();
            }
        }
    }

    public static void savePlayerArmor(EntityPlayer player, File file1, File file2) {
        if (player != null && !player.worldObj.isRemote) {
            try {
                if (file1 != null && file1.exists()) {
                    try {
                        Files.copy(file1, file2);
                    } catch (Exception e) {

                    }
                }

                try {
                    if (file1 != null) {
                        InventoryArmor inventory = getArmor(player);
                        NBTTagCompound data = new NBTTagCompound();
                        inventory.writeToNBT(data);

                        FileOutputStream fileoutputstream = new FileOutputStream(file1);
                        CompressedStreamTools.writeCompressed(data, fileoutputstream);
                        fileoutputstream.close();
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                    if (file1.exists()) {
                        try {
                            file1.delete();
                        } catch (Exception e2) {
                        }
                    }
                }
            } catch (Exception exception1) {
                exception1.printStackTrace();
            }
        }
    }
}
