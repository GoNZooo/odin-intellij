package com.lasagnerd.odin.runConfiguration;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.util.ProgramParametersConfigurator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.lasagnerd.odin.codeInsight.OdinContext;
import com.lasagnerd.odin.codeInsight.OdinInsightUtils;
import com.lasagnerd.odin.codeInsight.OdinSymbolTable;
import com.lasagnerd.odin.codeInsight.annotators.buildErrorsAnnotator.OdinBuildProcessRunner;
import com.lasagnerd.odin.codeInsight.imports.OdinImportUtils;
import com.lasagnerd.odin.codeInsight.symbols.OdinSymbol;
import com.lasagnerd.odin.codeInsight.typeSystem.TsOdinPointerType;
import com.lasagnerd.odin.codeInsight.typeSystem.TsOdinProcedureType;
import com.lasagnerd.odin.codeInsight.typeSystem.TsOdinStructType;
import com.lasagnerd.odin.codeInsight.typeSystem.TsOdinType;
import com.lasagnerd.odin.lang.psi.*;
import com.lasagnerd.odin.projectSettings.OdinSdkUtils;
import com.lasagnerd.odin.runConfiguration.build.OdinBuildRunConfigurationOptions;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;

public class OdinRunConfigurationUtils {
    public static boolean isMainProcedure(PsiElement element) {
        if (element.getParent() instanceof OdinDeclaredIdentifier declaredIdentifier) {
            PsiElement declaration = declaredIdentifier.getParent();
            if (declaration instanceof OdinConstantInitDeclaration constantInitDeclaration
                    && !OdinInsightUtils.isLocal(element)
                    && OdinInsightUtils.isProcedureDeclaration(constantInitDeclaration)
            ) {
                return element.getText().equals("main");
            }
        }

        return false;
    }

    public static List<OdinConstantInitDeclaration> findTestProcedures(OdinFile file) {
        List<OdinConstantInitDeclaration> testProcedures = new ArrayList<>();
        OdinSymbolTable symbolTable = file.getFileScope().getFullSymbolTable();
        for (OdinSymbol symbol : symbolTable.getSymbols()) {
            OdinConstantInitDeclaration testProcedure = getTestProcedure(symbol.getDeclaration());
            if (testProcedure != null) {
                testProcedures.add(testProcedure);
            }
        }

        return testProcedures;
    }

    public static OdinConstantInitDeclaration getTestProcedure(PsiElement element) {
        if (!OdinInsightUtils.isLocal(element)) {
            OdinDeclaration declaration = PsiTreeUtil.getParentOfType(element, false, OdinDeclaration.class);
            if (declaration instanceof OdinConstantInitDeclaration constantInitDeclaration
                    && OdinInsightUtils.isProcedureDeclaration(constantInitDeclaration)
            ) {
                if (OdinInsightUtils.containsAttribute(constantInitDeclaration.getAttributesDefinitionList(), "test")) {
                    OdinDeclaredIdentifier first = ((OdinConstantInitDeclaration) declaration).getDeclaredIdentifierList().getFirst();
                    TsOdinType type = first.getType(new OdinContext());
                    if (type.dereference().baseType(true) instanceof TsOdinProcedureType procedureType) {
                        if (procedureType.getParameters().size() == 1 && procedureType.getReturnParameters().isEmpty()) {
                            if (procedureType.baseType(true) instanceof TsOdinPointerType pointerType) {
                                TsOdinType dereferencedType = pointerType.getDereferencedType();
                                if (dereferencedType.baseType() instanceof TsOdinStructType structType) {
                                    if (structType.getName().equals("T")) {
                                        OdinDeclaration structTypeDeclaration = structType.getDeclaration();
                                        VirtualFile containingVirtualFile = OdinImportUtils.getContainingVirtualFile(structTypeDeclaration);
                                        Path declarationPath = Path.of(containingVirtualFile.getPath());
                                        Optional<String> validSdkPath = OdinSdkUtils.getValidSdkPath(element.getProject());
                                        if (validSdkPath.isPresent()) {
                                            Path sdkPath = Path.of(validSdkPath.get());
                                            if (declarationPath.startsWith(sdkPath)) {
                                                if (sdkPath.relativize(declarationPath).equals(Path.of("testing/testing.odin"))) {
                                                    return constantInitDeclaration;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return constantInitDeclaration;
                }
            }
        }
        return null;
    }

    public static boolean isPackageClause(PsiElement element) {
        if (element.getParent() instanceof OdinDeclaredIdentifier declaredIdentifier) {
            return element.getParent().getParent() instanceof OdinPackageClause;
        }

        return false;
    }

    public static @NotNull GeneralCommandLine createCommandLine(boolean debug,
                                                                ExecutionEnvironment environment,
                                                                OdinBuildRunConfigurationOptions options) {
        Project project = environment.getProject();
        OdinToolMode mode = debug ? OdinToolMode.BUILD : OdinToolMode.RUN;
        String projectDirectoryPath = options.getProjectDirectoryPath();
        String programArguments = options.getProgramArguments();
        String compilerOptions = Objects.requireNonNullElse(options.getCompilerOptions(), "");
        String outputPathString = Objects.requireNonNullElse(options.getOutputPath(), "");
        String basePath = project.getBasePath();
        String workingDirectory;
        if (options.getWorkingDirectory() != null) {
            workingDirectory = options.getWorkingDirectory();
        } else {
            workingDirectory = basePath;
        }

        return createCommandLine(project, debug,
                mode,
                compilerOptions,
                outputPathString,
                projectDirectoryPath,
                programArguments,
                workingDirectory);
    }

    /**
     * Creates a command line with the currently set odin compiler
     *
     * @param project              Current project
     * @param debug                debug mode?
     * @param mode                 "run" or "build"
     * @param compilerOptions      compiler options
     * @param outputPathString     output path, i.e. -out:path
     * @param projectDirectoryPath where to build
     * @param programArguments     the arguments to pass to the built executable
     * @param workingDirectory     working directory
     * @return General command line object
     */
    public static @NotNull GeneralCommandLine createCommandLine(Project project,
                                                                boolean debug,
                                                                OdinToolMode mode,
                                                                String compilerOptions,
                                                                String outputPathString,
                                                                String projectDirectoryPath,
                                                                String programArguments,
                                                                String workingDirectory) {
        ProgramParametersConfigurator configurator = new ProgramParametersConfigurator();
        Function<String, String> expandPath = s -> configurator.expandPathAndMacros(s, null, project);

        projectDirectoryPath = expandPath.apply(projectDirectoryPath);
        workingDirectory = expandPath.apply(workingDirectory);
        outputPathString = expandPath.apply(outputPathString);

        List<String> command = new ArrayList<>();
        String odinBinaryPath = OdinSdkUtils.getOdinBinaryPath(project);

        if (odinBinaryPath == null) {
            throw new RuntimeException("'odin' executable not found. Please setup SDK.");
        }
        String compilerPath = FileUtil.toSystemIndependentName(odinBinaryPath);


        OdinSdkUtils.addCommandPart(command, compilerPath);
        OdinSdkUtils.addCommandPart(command, mode.getCommandLineArgument());

        OdinSdkUtils.addCommandPart(command, projectDirectoryPath);

        if (compilerOptions != null) {
            Collections.addAll(command, compilerOptions.split(" +"));
        }

        if (debug && !command.contains("-debug")) {
            OdinSdkUtils.addCommandPart(command, "-debug");
        }
        OdinBuildProcessRunner.addCollectionPaths(project, projectDirectoryPath, command);

        if (!outputPathString.isEmpty()) {

            Path outputPath = OdinSdkUtils.getAbsolutePath(project, outputPathString);
            if (!outputPath.getParent().toFile().exists()) {
                boolean success = outputPath.getParent().toFile().mkdirs();
                if (!success) {
                    throw new RuntimeException("Failed to create output directory");
                }
            }

            OdinSdkUtils.addCommandPart(command, "-out:" + outputPathString);
        }

        if (programArguments != null && !programArguments.isEmpty()) {
            OdinSdkUtils.addCommandPart(command, "--");
            Collections.addAll(command, programArguments.split(" +"));
        }

        GeneralCommandLine commandLine = new GeneralCommandLine(command);

        commandLine.setWorkDirectory(workingDirectory);

        return commandLine;
    }

    @Getter
    public enum OdinToolMode {
        RUN("run"),
        BUILD("build"),
        TEST("test");

        private final String commandLineArgument;

        OdinToolMode(String commandLineArgument) {
            this.commandLineArgument = commandLineArgument;
        }
    }
}
