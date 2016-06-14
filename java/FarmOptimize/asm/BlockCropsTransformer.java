package FarmOptimize.asm;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import net.minecraft.block.BlockCrops;
import net.minecraft.init.Blocks;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Created by A.K. on 14/03/19.
 */
public class BlockCropsTransformer implements IClassTransformer, Opcodes{
    private static final String TARGET_CLASS_NAME = "net.minecraft.block.BlockCrops";
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!TARGET_CLASS_NAME.equals(transformedName)) {return basicClass;}
        try {
            basicClass = changeConst(basicClass, name);
        } catch (Exception e) {
            throw new RuntimeException("failed : BlockCropsTransformer loading", e);
        }
        return basicClass;
    }

    private byte[] changeConst(byte[] bytes, String owner) {
        ClassNode cnode = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept(cnode, 0);
        String targetMethodName = "func_149674_a";//updateTick
        MethodNode mnode = null;
        for (MethodNode curMnode :cnode.methods)
        {
            if (targetMethodName.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, curMnode.name, curMnode.desc)))
            {
                mnode = curMnode;
                break;
            }
        }
        if (mnode != null)
        {
            FarmOptimizeCorePlugin.logger.debug("transform updateTick Method");
            AbstractInsnNode oldInsnNode1 = mnode.instructions.get(45);
            AbstractInsnNode newInsnNode1 = new LdcInsnNode(0.25F);
            mnode.instructions.set(oldInsnNode1, newInsnNode1);

            LabelNode labelNode = (LabelNode)mnode.instructions.get(53);
            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(ALOAD, 0));
            insnList.add(new MethodInsnNode(INVOKESTATIC, "FarmOptimize/asm/BlockCropsTransformer", "getCropsGrowSpeed", "(Lnet/minecraft/block/BlockCrops;)I"));
            insnList.add(new InsnNode(I2F));
            insnList.add(new InsnNode(FMUL));

            InsnList insnList3 = new InsnList();
            insnList3.add(new VarInsnNode(ALOAD, 0));
            insnList3.add(new MethodInsnNode(INVOKESTATIC, "FarmOptimize/asm/BlockCropsTransformer", "getCropsGrowSpeed", "(Lnet/minecraft/block/BlockCrops;)I"));
            insnList3.add(new JumpInsnNode(IFEQ, labelNode));

            mnode.instructions.insert(mnode.instructions.get(47), insnList);
            mnode.instructions.insert(mnode.instructions.get(43), insnList3);

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            cnode.accept(cw);
            bytes = cw.toByteArray();

        }

        return bytes;
    }
    public static int getCropsGrowSpeed(BlockCrops blockCrops) {
        if (blockCrops == Blocks.carrots) return FarmOptimizeCorePlugin.growSpeedCarrot;
        else if (blockCrops == Blocks.potatoes) return FarmOptimizeCorePlugin.growSpeedPotato;
        else return FarmOptimizeCorePlugin.growSpeedCrops;

    }
}
