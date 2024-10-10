package com.lasagnerd.odin.lang.psi;

import com.intellij.codeInsight.highlighting.HighlightedReference;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.util.IncorrectOperationException;
import com.lasagnerd.odin.codeInsight.imports.OdinImport;
import com.lasagnerd.odin.codeInsight.imports.OdinImportUtils;
import com.lasagnerd.odin.projectSettings.OdinSdkUtils;
import com.lasagnerd.odin.projectStructure.OdinRootTypeUtils;
import com.lasagnerd.odin.projectStructure.collection.OdinRootTypeResult;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@EqualsAndHashCode(callSuper = true)
public class OdinPackageReference extends PsiReferenceBase<OdinImportPath> implements HighlightedReference {

    private final Path wholePath;
    private final Path subdirectory;
    private final int subPathIndex;

    public OdinPackageReference(@NotNull OdinImportPath element, TextRange rangeInElement, Path wholePath,
                                Path subdirectory,
                                int subPathIndex) {
        super(element, rangeInElement);
        this.wholePath = wholePath;
        this.subdirectory = subdirectory;

        this.subPathIndex = subPathIndex;
    }

    public static @Nullable PsiDirectory resolvePackagePathDirectory(OdinImportPath importPath) {

        OdinImport importInfo = OdinImportUtils.getImportInfo(importPath);
        if (importInfo != null) {
            VirtualFile containingVirtualFile = OdinImportUtils.getContainingVirtualFile(importPath);
            VirtualFile importDir = findDirectoryFileForImportPath(importPath.getProject(),
                    importInfo,
                    importInfo.path(),
                    containingVirtualFile);
            if (importDir != null) {
                return PsiManager.getInstance(importPath.getProject()).findDirectory(importDir);
            }
        }
        return null;
    }

    private static VirtualFile findDirectoryFileForImportPath(Project project,
                                                              OdinImport importInfo,
                                                              String importPath,
                                                              VirtualFile containingVirtualFile) {
        Path directoryPath = findDirectoryPathForImportPath(project, importInfo, importPath, containingVirtualFile);
        if (directoryPath != null) {
            return VfsUtil.findFile(directoryPath, false);
        }

        return null;
    }

    private static @Nullable Path findDirectoryPathForImportPath(Project project,
                                                                 OdinImport importInfo,
                                                                 String importPath,
                                                                 VirtualFile containingVirtualFile) {
        Path directoryPath = null;

        if (containingVirtualFile != null) {
            if (importInfo.collection() != null) {
                Optional<String> sdkPath = OdinSdkUtils.getSdkPath(project);
                if (sdkPath.isPresent()) {
                    Path absolutePath = Path.of(sdkPath.get(), importInfo.collection(), importPath).toAbsolutePath();
                    if (absolutePath.toFile().exists()) {
                        directoryPath = absolutePath;
                    }
                }

                if (directoryPath == null) {
                    Map<String, Path> collectionPaths = OdinImportUtils.getCollectionPaths(project, containingVirtualFile.getPath());
                    Path path = collectionPaths.get(importInfo.collection());
                    if (path != null) {
                        directoryPath = path.resolve(importPath);
                    }
                }
            } else {
                directoryPath = Path.of(
                        containingVirtualFile.getParent().getPath(),
                        importPath
                );
            }
        }
        return directoryPath;
    }

    public Path getDirectoryPath() {
        return getDirectoryPath(subdirectory.toString());
    }

    public Path getDirectoryPath(String importPath) {
        OdinImport importInfo = OdinImportUtils.getImportInfo(getElement());
        VirtualFile containingVirtualFile = OdinImportUtils.getContainingVirtualFile(getElement());
        if (importInfo != null && containingVirtualFile != null) {
            return findDirectoryPathForImportPath(getElement().getProject(), importInfo,
                    importPath,
                    containingVirtualFile
            );
        }
        return null;
    }

    @Override
    public @Nullable PsiElement resolve() {
        VirtualFile containingVirtualFile = OdinImportUtils.getContainingVirtualFile(getElement());
        if (containingVirtualFile != null) {
            OdinImport importInfo = OdinImportUtils.getImportInfo(getElement());
            VirtualFile importDir = findDirectoryFileForImportPath(getElement().getProject(),
                    importInfo,
                    subdirectory.toString(),
                    containingVirtualFile
            );
            if (importDir != null) {
                return PsiManager.getInstance(getElement().getProject()).findDirectory(importDir);
            }
        }
        return null;
    }

    // Gets the path to which the import path is relative to
    public Path getImportRootPath() {
        return getDirectoryPath("");
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement newTarget) throws IncorrectOperationException {
        // sub path: current path
        Path directoryPath = getDirectoryPath();

        if(directoryPath != null) {
            if (newTarget instanceof PsiDirectory psiTargetDirectory) {
                Project project = myElement.getProject();

                Path newDirPath = Path.of(psiTargetDirectory.getVirtualFile().getPath());

                OdinRootTypeResult odinCollection = OdinRootTypeUtils
                        .findContainingCollection(project, psiTargetDirectory.getVirtualFile());

                String newCollection;
                if(odinCollection != null && odinCollection.isCollectionRoot()) {
                    newCollection = odinCollection.collectionName();
                } else {
                    newCollection = null;
                }

                // Now we have to change the new path such that
                // current path: path-before/current-dir/path-after
                // new path: normalize(relative-path-from-curr-dir-to-new-dir/path-after)
                OdinImport importInfo = getImportInfo();
                Path rootPath = getImportRootPath();

                Path relativizedPath = directoryPath.relativize(newDirPath);
                String pathAfter;

                if(wholePath.getNameCount() > subPathIndex + 1) {
                    pathAfter = wholePath.subpath(subPathIndex + 1, wholePath.getNameCount()).toString();
                } else {
                    pathAfter = "";
                }

                Path newAbsolutePath = Path.of(rootPath.toString(), subdirectory.toString(), relativizedPath.toString(), pathAfter);
                Path normalized = newAbsolutePath.normalize();

                Path newRootPath;
                if(Objects.equals(newCollection, importInfo.collection())) {
                    newRootPath = rootPath;
                } else if(odinCollection != null) {
                    VirtualFile file = odinCollection.sourceFolder().getFile();
                    if(file != null) {
                        newRootPath = file.toNioPath();
                    } else {
                        newRootPath = rootPath;
                    }
                } else {
                    newRootPath = rootPath;
                }

                Path newRelativePath = newRootPath.relativize(normalized);

                String fullImportPath = "";
                if(newCollection != null) {
                    fullImportPath += newCollection +":";
                }

                fullImportPath += FileUtil.toSystemIndependentName(newRelativePath.toString());
                OdinImportPath importPath = OdinPsiElementFactory
                        .getInstance(project)
                        .createImportPath(StringUtil.wrapWithDoubleQuote(fullImportPath));

                return getElement().replace(importPath);
            }
        }
        return super.bindToElement(newTarget);
    }

    public OdinImport getImportInfo() {
        return OdinImportUtils.getImportInfo(getElement());
    }
}