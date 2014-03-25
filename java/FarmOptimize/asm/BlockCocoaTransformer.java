package FarmOptimize.asm;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Created by A.K. on 14/03/19.
 */
public class BlockCocoaTransformer implements IClassTransformer, Opcodes{
    private static final String TARGET_CLASS_NAME = "net.minecraft.block.BlockCocoa";
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!FMLLaunchHandler.side().isClient() || !TARGET_CLASS_NAME.equals(transformedName)) {return basicClass;}
        try {
            FarmOptimizeCorePlugin.logger.info("Start " + TARGET_CLASS_NAME + " transform");
            basicClass = changeConst(basicClass, name);
            FarmOptimizeCorePlugin.logger.info("Finish " + TARGET_CLASS_NAME + " transform");
        } catch (Exception e) {
            throw new RuntimeException("failed : BlockCocoaTransformer loading", e);
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
            FarmOptimizeCorePlugin.logger.info("transform updateTick Method");
            AbstractInsnNode oldInsnNode1 = mnode.instructions.get(41);
            AbstractInsnNode newInsnNode1 = new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "growSpeedCocoa", "I");
            mnode.instructions.set(oldInsnNode1, newInsnNode1);

            InsnList insnList = new InsnList();
            insnList.add(new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "growSpeedCocoa", "I"));
            LabelNode label = (LabelNode)mnode.instructions.get(44);
            insnList.add(new JumpInsnNode(IFEQ, label));
            mnode.instructions.insert(mnode.instructions.get(38), insnList);

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            cnode.accept(cw);
            bytes = cw.toByteArray();
        }

        return bytes;
    }
}
