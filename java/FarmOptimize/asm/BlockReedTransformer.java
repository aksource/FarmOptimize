package FarmOptimize.asm;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Created by A.K. on 14/03/15.
 */
public class BlockReedTransformer implements IClassTransformer,Opcodes{
    private static final String TARGET_CLASS_NAME = "net.minecraft.block.BlockReed";
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!FMLLaunchHandler.side().isClient() || !TARGET_CLASS_NAME.equals(transformedName)) {return basicClass;}
        try {
            System.out.println("Start " + TARGET_CLASS_NAME + " transform");
            basicClass = changeConst(basicClass, name);
            System.out.println("Finish " + TARGET_CLASS_NAME + " transform");
        } catch (Exception e) {
            throw new RuntimeException("failed : BlockReedTransformer loading", e);
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
            System.out.println("transform updateTick Method");
            AbstractInsnNode oldInsnNode1 = mnode.instructions.get(50);
            AbstractInsnNode newInsnNode1 = new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "SugarcaneLimit", "I");
            mnode.instructions.set(oldInsnNode1, newInsnNode1);
            AbstractInsnNode oldInsnNode2 = mnode.instructions.get(63);
            AbstractInsnNode newInsnNode2 = new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "SugarcaneSpeed", "I");
            mnode.instructions.set(oldInsnNode2, newInsnNode2);
            AbstractInsnNode oldInsnNode3 = mnode.instructions.get(64);
            AbstractInsnNode label = mnode.instructions.get(87);
            AbstractInsnNode newInsnNode3 = new JumpInsnNode(IF_ICMPLT, (LabelNode)label);
            mnode.instructions.set(oldInsnNode3, newInsnNode3);

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            cnode.accept(cw);
            bytes = cw.toByteArray();
        }

        return bytes;
    }
}