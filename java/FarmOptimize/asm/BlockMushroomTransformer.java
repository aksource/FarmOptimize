package FarmOptimize.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Created by A.K. on 14/03/15.
 */
public class BlockMushroomTransformer implements IClassTransformer, Opcodes{
    private static final String TARGET_CLASS_NAME = "net.minecraft.block.BlockMushroom";
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!FMLLaunchHandler.side().isClient() || !TARGET_CLASS_NAME.equals(transformedName)) {return basicClass;}
        try {
            FarmOptimizeCorePlugin.logger.info("Start " + TARGET_CLASS_NAME + " transform");
//            basicClass = changeConst(basicClass, name);
            ClassReader classReader = new ClassReader(basicClass);
            ClassWriter classWriter = new ClassWriter(1);
            classReader.accept(new CustomVisitor(name,classWriter), 8);
            FarmOptimizeCorePlugin.logger.info("Finish " + TARGET_CLASS_NAME + " transform");
        } catch (Exception e) {
            throw new RuntimeException("failed : BlockMushroomTransformer loading", e);
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
            AbstractInsnNode oldInsnNode1 = mnode.instructions.get(8);//ICONST_5
            AbstractInsnNode newInsnNode1 = new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "MushroomLimit", "I");
            mnode.instructions.set(oldInsnNode1, newInsnNode1);
            AbstractInsnNode oldInsnNode21 = mnode.instructions.get(17);//BIPUSH -4
            AbstractInsnNode oldInsnNode22 = mnode.instructions.get(19);//BIPUSH -4
            AbstractInsnNode newInsnNode21 = new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "MushroomAreaMinus", "B");
            mnode.instructions.set(oldInsnNode21, newInsnNode21);
            mnode.instructions.set(oldInsnNode22, newInsnNode21);
            AbstractInsnNode oldInsnNode23 = mnode.instructions.get(22);//ICONST_4
            AbstractInsnNode oldInsnNode24 = mnode.instructions.get(24);//ICONST_4
            AbstractInsnNode newInsnNode22 = new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "MushroomArea", "B");
            mnode.instructions.set(oldInsnNode23, newInsnNode22);
            mnode.instructions.set(oldInsnNode24, newInsnNode22);
            AbstractInsnNode oldInsnNode3 = mnode.instructions.get(3);//BIPUSH 25
            AbstractInsnNode newInsnNode3 = new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "MushroomSpeed", "I");
            mnode.instructions.set(oldInsnNode3, newInsnNode3);

/*            InsnList insnList = new InsnList();
            insnList.add(new FieldInsnNode(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "MushroomSpeed", "I"));
            LabelNode label = (LabelNode)mnode.instructions.get(6);//label 2
            insnList.add(new JumpInsnNode(IFEQ, label));
            mnode.instructions.insert(mnode.instructions.get(1), insnList);//After LINENUMBER*/

            ClassWriter cw = new ClassWriter(0/*ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS*/);
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

        boolean bipush25 = false;
        boolean const5 = false;
        int const4 = 2;
        int bipushm4 = 2;
        @Override
        public void visitIntInsn(int opcode, int operand) {
            if (opcode == BIPUSH) {
                if (operand == 25 && !bipush25) {
                    bipush25 = true;
                    super.visitFieldInsn(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "MushroomSpeed", "I");
                    return;
                }
                if (operand == -4 && bipushm4 > 0) {
                    bipushm4--;
                    super.visitFieldInsn(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "MushroomAreaMinus", "B");
                }
            } else {
                super.visitIntInsn(opcode, operand);
            }
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == ICONST_5 && !const5) {
                const5 = true;
                super.visitFieldInsn(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "MushroomLimit", "I");
                return;
            }
            if (opcode == ICONST_4 && const4 > 0) {
                const4--;
                super.visitFieldInsn(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "MushroomArea", "B");
            }
            super.visitInsn(opcode);
        }
    }
}
