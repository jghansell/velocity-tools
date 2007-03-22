package org.apache.velocity.tools;

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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * @author Nathan Bubna
 * @version $Id: Toolbox.java 511959 2007-02-26 19:24:39Z nbubna $
 */
//TODO: make this class serializable
public class Toolbox
{
    private Map<String,ToolInfo> infoMap;
    private Map properties;
    private Map<String,Object> cache;

    public Toolbox(Map<String,ToolInfo> toolInfo)
    {
        this(toolInfo, null);
    }

    public Toolbox(Map<String,ToolInfo> toolInfo, Map<String,Object> properties)
    {
        if (toolInfo == null || toolInfo.isEmpty())
        {
            throw new IllegalArgumentException("Toolbox must have one or more tools");
        }

        this.properties = properties;
    }


    protected void cacheData(Map<String,Object> data)
    {
        if (data != null && !data.isEmpty())
        {
            cache = new HashMap<String,Object>(data);
        }
    }


    public Map<String,Object> getProperties()
    {
        return properties;
    }

    public Object get(String key, Map<String,Object> context)
    {
        return get(key, null, context);
    }

    public Object get(String key, String path, Map<String,Object> context)
    {
        Object tool = null;
        if (cache != null)
        {
            tool = getFromCache(key, path);
        }
        if (tool == null)
        {
            tool = getFromInfo(key, path, context);
        }
        return tool;
    }


    protected Object getFromCache(String key, String path)
    {
        if (cache == null)
        {
            return null;
        }
        else
        {
            Object tool = cache.get(key);
            if (tool == null || path == null)
            {
                return tool;
            }
            else if (hasPermission(infoMap.get(key), path))
            {
                return tool;
            }
            else
            {
                return null;
            }
        }
    }

    protected Object getFromInfo(String key, String path,
                                 Map<String,Object> context)
    {
        ToolInfo info = infoMap.get(key);
        if (info != null && (path == null || hasPermission(info, path)))
        {
            Object tool = info.create(context);
            if (cache == null)
            {
                cache = new HashMap<String,Object>();
            }
            cache.put(key, tool);
            return tool;
        }
        return null;
    }

    protected boolean hasPermission(ToolInfo info, String path)
    {
        return info.hasPermission(path);
    }

    public Set<String> getKeys()
    {
        return Collections.unmodifiableSet(infoMap.keySet());
    }

    public Map<String,Object> getAll(Map<String,Object> context)
    {
        // request all tools we have info for
        for (ToolInfo info : infoMap.values())
        {
            get(info.getKey(), context);
        }
        // then return a copy of the cache
        return new HashMap<String,Object>(this.cache);
    }

}
