package FarmOptimize.asm;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/**
 * Created by A.K. on 14/03/15.
 */
public class BlockCactusTransformer implements IClassTransformer, Opcodes{
    private static final String TARGET_CLASS_NAME = "net.minecraft.block.BlockCactus";
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!FMLLaunchHandler.side().isClient() || !TARGET_CLASS_NAME.equals(transformedName)) {return basicClass;}
        try {
            FarmOptimizeCorePlugin.logger.info("Start " + TARGET_CLASS_NAME + " transform");
            ClassReader classReader = new ClassReader(basicClass);
            ClassWriter classWriter = new ClassWriter(1);
            classReader.accept(new CustomVisitor(name,classWriter), 8);
//            basicClass = changeConst(basicClass, name);
            FarmOptimizeCorePlugin.logger.info("Finish " + TARGET_CLASS_NAME + " transform");
        } catch (Exception e) {
            throw new RuntimeException("failed : BlockCactusTransformer loading", e);
        }
        return basicClass;
    }

    private byte[] changeConst(byte[] bytes, String owner) {
        ClassNode cnode = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept(cnode, 0);
        String targetMethodName = FarmOptimizeCorePlugin.updateTickMethodObfName;//updateTick
        MethodNode mnode = null;
        for (MethodNode curMnode :cnode.methods) {
            if (targetMethodName.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, curMnode.name, curMnode.desc))) {
                mnode = curMnode;
                break;
            }
        }
        if (mnode != null)
        {
            FarmOptimizeCorePlugin.logger.info("transform updateTick Method");
            AbstractInsnNode oldInsnNode1 = mnode.instructions.get(31);//ICONST_3
            AbstractInsnNode newInsnNode1 = new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "CactusLimit", "I");
            mnode.instructions.set(oldInsnNode1, newInsnNode1);
            AbstractInsnNode oldInsnNode2 = mnode.instructions.get(44);//BIPUSH 15
            AbstractInsnNode newInsnNode2 = new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "CactusSpeed", "I");
            mnode.instructions.set(oldInsnNode2, newInsnNode2);
            AbstractInsnNode oldInsnNode3 = mnode.instructions.get(45);//IF_ICMPNE L8
            AbstractInsnNode label = mnode.instructions.get(79);
            AbstractInsnNode newInsnNode3 = new JumpInsnNode(IF_ICMPLT, (LabelNode)label);
            mnode.instructions.set(oldInsnNode3, newInsnNode3);

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            cnode.accept(cw);
            bytes = cw.toByteArray();
        }

        return bytes;
    }
    class CustomVisitor extends ClassVisitor {
        String owner;
        public CustomVisitor(String name, ClassVisitor cv) {
            super(ASM5, cv);
            owner = name;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            String deobfMethodName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, name, desc);
            if (deobfMethodName.equals(FarmOptimizeCorePlugin.updateTickMethodObfName)
                    || deobfMethodName.equals(FarmOptimizeCorePlugin.updateTickMethodDeobfName)) {
                return new CustomMethodVisitor(this.api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }

    class CustomMethodVisitor extends MethodVisitor {
        public CustomMethodVisitor(int api, MethodVisitor mv) {
            super(api, mv);
        }

        boolean bipush15 = false;
        boolean const3 = false;
        boolean icmpne = false;
        @Override
        public void visitIntInsn(int opcode, int operand) {
            if (opcode == BIPUSH) {
                if (operand == 15 && !bipush15) {
                    bipush15 = true;
                    super.visitFieldInsn(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "CactusSpeed", "I");
                }
            } else {
                super.visitIntInsn(opcode, operand);
            }
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == ICONST_3 && !const3) {
                const3 = true;
                super.visitFieldInsn(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "CactusLimit", "I");
                return;
            }
            super.visitInsn(opcode);
        }

        @Override
        public void visitJumpInsn(int opcode, Label label) {
            if (opcode == IF_ICMPNE && !icmpne) {
                super.visitJumpInsn(IF_ICMPLT, label);
            }
            super.visitJumpInsn(opcode, label);
        }
    }
}
