package FarmOptimize.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

/**
 * Created by A.K. on 14/03/19.
 */
public class BlockCocoaTransformer implements IClassTransformer, Opcodes{
    private static final String TARGET_CLASS_NAME = "net.minecraft.block.BlockCocoa";
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!TARGET_CLASS_NAME.equals(transformedName)) {return basicClass;}
        try {
            ClassReader classReader = new ClassReader(basicClass);
            ClassWriter classWriter = new ClassWriter(1);
            classReader.accept(new CustomVisitor(name,classWriter), 8);
        } catch (Exception e) {
            throw new RuntimeException("failed : BlockCocoaTransformer loading", e);
        }
        return basicClass;
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

        boolean const5 = false;

        @Override
        public void visitInsn(int opcode) {
            if (opcode == ICONST_5 && !const5) {
                const5 = true;
                super.visitFieldInsn(GETSTATIC, "FarmOptimize/asm/FarmOptimizeCorePlugin", "growSpeedCocoa", "I");
                return;
            }
            super.visitInsn(opcode);
        }
    }
}
