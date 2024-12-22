package com.lasagnerd.odin.lang;

import com.intellij.psi.PsiFile;
import com.intellij.psi.StubBuilder;
import com.intellij.psi.stubs.DefaultStubBuilder;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.tree.IStubFileElementType;
import com.lasagnerd.odin.lang.psi.OdinFile;
import com.lasagnerd.odin.lang.stubs.OdinFileStub;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OdinFileElementType extends IStubFileElementType<OdinFileStub> {
    public static final int VERSION = 0;
    public static final OdinFileElementType INSTANCE = new OdinFileElementType();

    public OdinFileElementType() {
        super("ODIN_FILE", OdinLanguage.INSTANCE);
    }

    @Override
    public int getStubVersion() {
        return VERSION;
    }

    @Override
    public StubBuilder getBuilder() {
        return new DefaultStubBuilder() {
            @SuppressWarnings("rawtypes")
            @Override
            protected @NotNull StubElement createStubForFile(@NotNull PsiFile file) {
                if (file instanceof OdinFile odinFile) {
                    return new OdinFileStub(odinFile);
                }
                return super.createStubForFile(file);
            }
        };
    }

    @Override
    public @NotNull OdinFileStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new OdinFileStub(null);
    }
}
