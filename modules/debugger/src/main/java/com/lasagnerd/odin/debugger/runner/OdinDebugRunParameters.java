package com.lasagnerd.odin.debugger.runner;

import com.google.protobuf.Message;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessListener;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.Key;
import com.intellij.util.system.CpuArch;
import com.jetbrains.cidr.ArchitectureType;
import com.jetbrains.cidr.execution.Installer;
import com.jetbrains.cidr.execution.RunParameters;
import com.jetbrains.cidr.execution.TrivialInstaller;
import com.jetbrains.cidr.execution.debugger.backend.DebuggerDriver;
import com.jetbrains.cidr.execution.debugger.backend.DebuggerDriverConfiguration;
import com.jetbrains.cidr.execution.debugger.backend.lldb.LLDBDriver;
import com.jetbrains.cidr.execution.debugger.backend.lldb.LLDBDriverConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OdinDebugRunParameters extends RunParameters {

    GeneralCommandLine commandLine;
    private final DebuggerDriverConfiguration debuggerDriverConfiguration;

    public OdinDebugRunParameters(GeneralCommandLine commandLine, DebuggerDriverConfiguration debuggerDriverConfiguration) {
        this.commandLine = commandLine;
        this.debuggerDriverConfiguration = debuggerDriverConfiguration;
    }

	@Override
	public @NotNull Installer getInstaller() {
		return new TrivialInstaller(this.commandLine);
	}

	@Override
	public @NotNull DebuggerDriverConfiguration getDebuggerDriverConfiguration() {
		return debuggerDriverConfiguration;
	}

	@Override
	public @Nullable String getArchitectureId() {
		return CpuArch.CURRENT.name();
	}

	private static class MyLLDBDriver extends LLDBDriver {

		private static final Logger LOG = Logger.getInstance(MyLLDBDriver.class);

		public MyLLDBDriver(@NotNull Handler handler, @NotNull LLDBDriverConfiguration starter, @NotNull ArchitectureType architectureType) throws ExecutionException {
			super(handler, starter, architectureType);
			getProcessHandler().addProcessListener(new ProcessListener() {
				@Override
				public void onTextAvailable(@NotNull ProcessEvent event, @NotNull Key outputType) {
					LOG.info("LLDB says: " + event.getText());
				}

				@Override
				public void processTerminated(@NotNull ProcessEvent event) {
					LOG.info("Process terminated with code " + event.getExitCode());
				}
			});
		}

		@Override
		public <R extends Message, E extends Exception> @NotNull R sendMessageAndWaitForReply(@NotNull Message message, @NotNull Class<R> responseClass, @NotNull ResponseMessageConsumer<? super R, E> errorHandler, long msTimeout) throws ExecutionException, E {
			LOG.info("Sending message: " + message);
			R r = super.sendMessageAndWaitForReply(message, responseClass, errorHandler, msTimeout);
			LOG.info("Response: " + r);
			return r;
		}
	}

	private static class MyLLDBDriverConfiguration extends LLDBDriverConfiguration {
		@Override
		public @NotNull GeneralCommandLine createDriverCommandLine(@NotNull DebuggerDriver driver, @NotNull ArchitectureType architectureType) throws ExecutionException {
			GeneralCommandLine driverCommandLine = super.createDriverCommandLine(driver, architectureType);
			driverCommandLine.withEnvironment("LLDB_USE_NATIVE_PDB_READER", "yes");
			return driverCommandLine;
		}

		@Override
		public @NotNull LLDBDriver createDriver(DebuggerDriver.@NotNull Handler handler, @NotNull ArchitectureType architectureType) throws ExecutionException {
			return new MyLLDBDriver(handler, this, architectureType);
		}
	}
}
