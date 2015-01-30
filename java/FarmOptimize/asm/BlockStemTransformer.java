package FarmOptimize.asm;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraft.block.BlockStem;
import net.minecraft.init.Blocks;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Created by A.K. on 14/03/18.
 */
public class BlockStemTransformer implements IClassTransformer, Opcodes{
    private static final String TARGET_CLASS_NAME = "net.minecraft.block.BlockStem";
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!FMLLaunchHandler.side().isClient() || !TARGET_CLASS_NAME.equals(transformedName)) {return basicClass;}
        try {
            FarmOptimizeCorePlugin.logger.info("Start " + TARGET_CLASS_NAME + " transform");
            basicClass = changeConst(basicClass, name);
            FarmOptimizeCorePlugin.logger.info("Finish " + TARGET_CLASS_NAME + " transform");
        } catch (Exception e) {
            throw new RuntimeException("failed : BlockStemTransformer loading", e);
        }
        return basicClass;
    }

    private byte[] changeConst(byte[] bytes, String owner) {
        ClassNode cnode = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept(cnode, 0);
        String targetMethodName = FarmOptimizeCorePlugin.updateTickMethodObfName;//updateTick
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
            FarmOptimizeCorePlugin.logger.info("transform updateTick Method");
            AbstractInsnNode oldInsnNode1 = mnode.instructions.get(26/*32*/);//LDC 25.0
            AbstractInsnNode newInsnNode1 = new LdcInsnNode(0.25F);
            mnode.instructions.set(oldInsnNode1, newInsnNode1);

            LabelNode labelNode = (LabelNode)mnode.instructions.get(34/*40*/);//Label 5
            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(ILOAD, 7));
            insnList.add(new InsnNode(I2F));
            insnList.add(new InsnNode(FMUL));


            InsnList insnList3 = new InsnList();
            insnList3.add(new VarInsnNode(ALOAD, 0));
            insnList3.add(new MethodInsnNode(INVOKESTATIC, "FarmOptimize/asm/BlockStemTransformer", "getStemGrowSpeed", "(Lnet/minecraft/block/BlockStem;)I", false));
            insnList3.add(new VarInsnNode(ISTORE, 7));
            insnList3.add(new VarInsnNode(ILOAD, 7));
            insnList3.add(new JumpInsnNode(IFEQ, labelNode));
            mnode.instructions.insert(mnode.instructions.get(28/*34*/), insnList);//After FDIV
            mnode.instructions.insert(mnode.instructions.get(24/*30*/), insnList3);//After LINENUMBER


            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            cnode.accept(cw);
            bytes = cw.toByteArray();
        }

        return bytes;
    }
    public static int getStemGrowSpeed(BlockStem blockCrops) {
        if (blockCrops == Blocks.pumpkin_stem) return FarmOptimizeCorePlugin.growSpeedPumpkin;
        else if (blockCrops == Blocks.melon_stem) return FarmOptimizeCorePlugin.growSpeedWaterMelon;
        else return 100;
    }
}
