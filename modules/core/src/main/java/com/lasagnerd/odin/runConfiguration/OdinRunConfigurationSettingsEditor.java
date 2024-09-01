package com.lasagnerd.odin.runConfiguration;

import com.intellij.execution.configuration.EnvironmentVariablesTextFieldWithBrowseButton;
import com.intellij.execution.impl.CheckableRunConfigurationEditor;
import com.intellij.ide.macro.MacrosDialog;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.RawCommandLineEditor;
import com.intellij.ui.components.fields.ExtendableTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class OdinRunConfigurationSettingsEditor extends SettingsEditor<OdinRunConfiguration> implements CheckableRunConfigurationEditor<OdinRunConfiguration> {

    private final JPanel panel;
    private final JTextField compilerOptions;
    private final ExtendableTextField workingDirectory;
    private final RawCommandLineEditor programArguments;
    private final JTextField projectProjectDirectoryPath;
    private final ExtendableTextField outputPath;

    public OdinRunConfigurationSettingsEditor(@NotNull Project project) {
        // Get project
        compilerOptions = new JTextField("run .");

        outputPath = new ExtendableTextField("$ProjectFileDir$/bin");
        outputPath.setColumns(0);
        MacrosDialog.addMacroSupport(outputPath, (x) -> true, () -> false);

        projectProjectDirectoryPath = new ExtendableTextField("$ProjectFileDir$");
        TextFieldWithBrowseButton extendableProjectDirectoryPath = createDirectoryChooser(project, projectProjectDirectoryPath);

        workingDirectory = new ExtendableTextField("$ProjectFileDir$");
        TextFieldWithBrowseButton extendableWorkingDirectory = createDirectoryChooser(project, workingDirectory);
        MacrosDialog.addMacroSupport(workingDirectory, (x) -> true, () -> false);

        EnvironmentVariablesTextFieldWithBrowseButton environmentVariables = new EnvironmentVariablesTextFieldWithBrowseButton();

        programArguments = new RawCommandLineEditor();
        MacrosDialog.addMacroSupport(programArguments.getEditorField(), (x) -> true, () -> false);

        panel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JLabel("Package Directory"), extendableProjectDirectoryPath)
                .addLabeledComponent("Program arguments", programArguments)
                .addLabeledComponent("Working directory", extendableWorkingDirectory)

                .addLabeledComponent("Compiler options", compilerOptions, 15)
                .addLabeledComponent("Output path", outputPath)
                .addLabeledComponent("Environment variables", environmentVariables)

                .getPanel();
    }

    @NotNull
    private TextFieldWithBrowseButton createDirectoryChooser(@NotNull Project project, JTextField textField) {
        return new TextFieldWithBrowseButton(textField, e -> {
            VirtualFile virtualFile = FileChooser.chooseFile(
                    FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                    project,
                    null);
            if (virtualFile == null) return;
            textField.setText(virtualFile.getPath());
        }, null);
    }

    @Override
    protected void resetEditorFrom(@NotNull OdinRunConfiguration s) {
        compilerOptions.setText(s.getOptions().getCompilerOptions());
        projectProjectDirectoryPath.setText(s.getOptions().getProjectDirectoryPath());
        outputPath.setText(s.getOptions().getOutputPath());
        workingDirectory.setText(s.getOptions().getWorkingDirectory());
        programArguments.setText(s.getOptions().getProgramArguments());
    }

    @Override
    protected void applyEditorTo(@NotNull OdinRunConfiguration s) {
        s.getOptions().setCompilerOptions(compilerOptions.getText());
        s.getOptions().setProjectDirectoryPath(projectProjectDirectoryPath.getText());
        s.getOptions().setOutputPath(outputPath.getText());
        s.getOptions().setWorkingDirectory(workingDirectory.getText());
        s.getOptions().setProgramArguments(programArguments.getText());

    }

    @Override
    protected @NotNull JComponent createEditor() {
        return panel;
    }

    @Override
    public void checkEditorData(OdinRunConfiguration s) {

    }
}
