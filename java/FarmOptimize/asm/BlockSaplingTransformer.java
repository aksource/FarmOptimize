package FarmOptimize.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Created by A.K. on 14/03/18.
 */
public class BlockSaplingTransformer implements IClassTransformer, Opcodes{
    private static final String TARGET_CLASS_NAME = "net.minecraft.block.BlockSapling";
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!TARGET_CLASS_NAME.equals(transformedName)) {return basicClass;}
        try {
            basicClass = changeConst(basicClass, name);
        } catch (Exception e) {
            throw new RuntimeException("failed : BlockSaplingTransformer loading", e);
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
            AbstractInsnNode oldInsnNode1 = mnode.instructions.get(22/*26*/);//BIPUSH 7
            AbstractInsnNode newInsnNode1 = new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "growSpeedSapling", "I");
            mnode.instructions.set(oldInsnNode1, newInsnNode1);

            InsnList insnList = new InsnList();
            insnList.add(new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "growSpeedSapling", "I"));
            LabelNode label = (LabelNode)mnode.instructions.get(25/*29*/);//Label 4
            insnList.add(new JumpInsnNode(IFEQ, label));
            mnode.instructions.insert(mnode.instructions.get(20/*15*/), insnList);//After IF_ICMPLT

            ClassWriter cw = new ClassWriter(/*ClassWriter.COMPUTE_FRAMES | */ClassWriter.COMPUTE_MAXS);
            cnode.accept(cw);
            bytes = cw.toByteArray();
        }

        return bytes;
    }
}
