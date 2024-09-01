/*
 * Copyright 2023-2024 FalsePattern
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lasagnerd.odin.debugger.dap;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

class BlockingPipedInputStream extends PipedInputStream {
        boolean closed;

        public BlockingPipedInputStream(PipedOutputStream pout, int pipeSize) throws IOException {
            super(pout, pipeSize);
        }

        public synchronized int read() throws IOException {
            if (this.closed) {
                throw new IOException("stream closed");
            } else {
                while(super.in < 0) {
                    this.notifyAll();

                    try {
                        this.wait(750L);
                    } catch (InterruptedException var2) {
                        throw new InterruptedIOException();
                    }
                }

                int ret = this.buffer[super.out++] & 255;
                if (super.out >= this.buffer.length) {
                    super.out = 0;
                }

                if (super.in == super.out) {
                    super.in = -1;
                }

                return ret;
            }
        }

        public void close() throws IOException {
            this.closed = true;
            super.close();
        }
    }