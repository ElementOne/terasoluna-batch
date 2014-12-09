/*
 * Copyright (c) 2011 NTT DATA Corporation
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

package jp.terasoluna.fw.collector.exception;

/**
 * �R���N�^��O
 */
public class CollectorException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -4662102198786531151L;

    /**
     * �R���X�g���N�^
     */
    public CollectorException() {
        super();
    }

    /**
     * �R���X�g���N�^
     * @param message
     */
    public CollectorException(String message) {
        super(message);
    }

    /**
     * �R���X�g���N�^
     * @param cause
     */
    public CollectorException(Throwable cause) {
        super(cause);
    }

    /**
     * �R���X�g���N�^
     * @param message
     * @param cause
     */
    public CollectorException(String message, Throwable cause) {
        super(message, cause);
    }

}
