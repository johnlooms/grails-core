/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.grails.support.encoding;

import java.io.IOException;
import java.util.Collections;

/**
 * EncodedAppender implementation used for piping / chaining several StreamingEncoders
 *
 */
public class StreamingEncoderEncodedAppender extends AbstractEncodedAppender {
    private final StreamingEncoder encoder;
    private final EncodedAppender target;
    
    public StreamingEncoderEncodedAppender(StreamingEncoder encoder, EncodedAppender target) {
        this.encoder = encoder;
        this.target = target;
    }

    @Override
    public void close() throws IOException {
        target.close();
    }
    
    @Override
    public void flush() throws IOException {
        target.flush();
    }

    @Override
    protected void write(EncodingState encodingState, char[] b, int off, int len) throws IOException {
        encoder.encodeToStream(encoder, CharSequences.createCharSequence(b), off, len, target, encodingState);
    }

    @Override
    protected void write(EncodingState encodingState, String str, int off, int len) throws IOException {
        encoder.encodeToStream(encoder, str, off, len, target, encodingState);
    }

    @Override
    protected void appendCharSequence(EncodingState encodingState, CharSequence str, int start, int end)
            throws IOException {
        encoder.encodeToStream(encoder, str, start, end-start, target, encodingState);
    }
    
    @Override
    public void append(Encoder encoderStateEncoder, char character) throws IOException {
        encoder.encodeToStream(encoder, CharSequences.createSingleCharSequence(character), 0, 1, target, encoderStateEncoder != null ? new EncodingStateImpl(Collections.singleton(encoderStateEncoder)) : null);
    }    
}
