package dmillerw.armor.asm.transformer;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author dmillerw
 */
public class TransformInventoryPlayer implements IClassTransformer {

    protected static boolean obfuscated = false;

    private static enum Class {

        INVENTORY_PLAYER("yx", "net/minecraft/entity/player/InventoryPlayer"),
        ITEMSTACK("add", "net/minecraft/item/ItemStack");

        public final String obf;
        public final String deobf;

        private Class(String obf, String deobf) {
            this.obf = obf;
            this.deobf = deobf;
        }

        public String get() {
            return obfuscated ? obf : deobf;
        }
    }

    private static enum Method {
        ARMOR_ITEM_IN_SLOT("func_70440_f", "armorItemInSlot");

        public final String srg;
        public final String deobf;

        private Method(String srg, String deobf) {
            this.srg = srg;
            this.deobf = deobf;
        }

        public String get() {
            return obfuscated ? srg : deobf;
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        obfuscated = !name.equals(transformedName);
        if (transformedName.equals("net.minecraft.entity.player.InventoryPlayer"))
            return transformInventoryPlayer(basicClass);

        return basicClass;
    }

    private byte[] transformInventoryPlayer(byte[] basicClass) {
        ClassReader classReader = new ClassReader(basicClass);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        MethodNode armorMethod = null;
        for (MethodNode node : classNode.methods) {
            if (node.name.equals(Method.ARMOR_ITEM_IN_SLOT.get())) {
//                if (node.desc.equals("(I)L" + Class.ITEMSTACK.get() + ";")) {
                    armorMethod = node;
//                }
            }
        }


        if (armorMethod != null) {
            FMLLog.info("[CosmeticArmor]: Found armorItemInSlot method. Injecting...");
            armorMethod.instructions.clear();
            armorMethod.instructions.add(new VarInsnNode(ALOAD, 0));
            armorMethod.instructions.add(new VarInsnNode(ILOAD, 1));
            armorMethod.instructions.add(new MethodInsnNode(
                    INVOKESTATIC,
                    "dmillerw/armor/asm/StaticMethods",
                    "armorInSlot",
                    "(L" + Class.INVENTORY_PLAYER.get() + ";I)L" + Class.ITEMSTACK.get() + ";",
                    false));
            armorMethod.instructions.add(new InsnNode(ARETURN));

            ClassWriter classWriter = new ClassWriter(0);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        } else {
            FMLLog.info("[CosmeticArmor]: Failed to find armorItemInSlot method! Aborting...");
            return basicClass;
        }
    }
}
