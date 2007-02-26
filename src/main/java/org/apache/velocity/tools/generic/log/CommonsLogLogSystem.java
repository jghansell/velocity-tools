package org.apache.velocity.tools.generic.log;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.velocity.runtime.log.LogSystem;

/**
 * Redirects Velocity's LogSystem messages to commons-logging.
 *
 * <p>This is basically an empty subclass of CommmonsLogLogChute that exists
 *    merely for backwards compatibility with VelocityTools 1.x. Please
 *    use CommonsLogLogChute directly, as this will likely be removed
 *    in VelocityTools 2.1, if not earlier.
 * </p>
 *
 * @author Nathan Bubna
 * @since VelocityTools 1.1
 * @deprecated Use CommonsLogLogChute instead
 * @version $Id$
 */
public class CommonsLogLogSystem
    extends CommonsLogLogChute implements LogSystem
{
    public static final String LOGSYSTEM_COMMONS_LOG_NAME =
        LOGCHUTE_COMMONS_LOG_NAME;

    public void logVelocityMessage(int level, String message)
    {
        log(level, message);
    }

}
