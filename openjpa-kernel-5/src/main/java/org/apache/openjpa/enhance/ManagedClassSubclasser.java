/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.openjpa.enhance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.BytecodeWriter;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.GeneratedClasses;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UserException;
import serp.bytecode.BCClass;

/**
 * Redefines the method bodies of existing unenhanced classes to make them
 * notify state managers of mutations.
 *
 * @since 1.0.0
 */
public class ManagedClassSubclasser {
    private static final Localizer _loc = Localizer.forPackage(
        ManagedClassSubclasser.class);

    /**
     * For each element in <code>classes</code>, creates and registers a
     * new subclass that implements {@link PersistenceCapable}, and prepares
     * OpenJPA to handle new instances of the unenhanced type. If this is
     * invoked in a Java 6 environment, this method will redefine the methods
     * for each class in the argument list such that field accesses are
     * intercepted in-line. If invoked in a Java 5 environment, this
     * redefinition is not possible; in these contexts, when using field
     * access, OpenJPA will need to do state comparisons to detect any change
     * to any instance at any time, and when using property access, OpenJPA
     * will need to do state comparisons to detect changes to newly inserted
     * instances after a flush has been called.
     *
     * @return the new subclasses, or <code>null</code> if <code>classes</code>
     * is <code>null</code>.
     * @throws UserException if <code>conf</code> requires build-time
     * enhancement and <code>classes</code> includes unenhanced types.
     *
     * @since 1.0.0
     */
    public static List<Class> prepareUnenhancedClasses(
        final OpenJPAConfiguration conf,
        final Collection<? extends Class> classes,
        final ClassLoader envLoader) {
        if (classes == null)
            return null;
        if (classes.size() == 0)
            return Collections.EMPTY_LIST;
        if (!conf.getRuntimeClassOptimization()) {
            Collection unenhanced = new ArrayList();
            for (Class cls : classes)
                if (!PersistenceCapable.class.isAssignableFrom(cls))
                    unenhanced.add(cls);
            if (unenhanced.size() > 0)
                throw new UserException(_loc.get(
                    "runtime-optimization-disabled", unenhanced));
            return null;
        }

        Log log = conf.getLog(OpenJPAConfiguration.LOG_ENHANCE);
        boolean redefine = ClassRedefiner.canRedefineClasses();
        if (redefine)
            log.info(_loc.get("enhance-and-subclass-no-redef-start",
                classes));
        else
            log.info(_loc.get("enhance-and-subclass-and-redef-start",
                classes));

        final Map<Class, byte[]> map = new HashMap<Class, byte[]>();
        final List subs = new ArrayList(classes.size());
        final List ints = new ArrayList(classes.size());
        for (Iterator iter = classes.iterator(); iter.hasNext(); ) {
            final Class cls = (Class) iter.next();
            final PCEnhancer enhancer = new PCEnhancer(conf, cls);

            enhancer.setBytecodeWriter(new BytecodeWriter() {
                public void write(BCClass bc) throws IOException {
                    ManagedClassSubclasser.write(bc, enhancer, map,
                        cls, subs, ints);
                }
            });
            if (redefine)
                enhancer.setRedefine(true);
            enhancer.setCreateSubclass(true);
            enhancer.setAddDefaultConstructor(true);

            // set this before enhancement as well as after since enhancement
            // uses a different metadata repository, and the metadata config
            // matters in the enhancement contract. Don't do any warning here,
            // since we'll issue warnings when we do the final metadata
            // reconfiguration at the end of this method.
            configureMetaData(enhancer.getMetaData(), conf, redefine, false);

            enhancer.run();
            try {
                enhancer.record();
            } catch (IOException e) {
                // our impl of BytecodeWriter doesn't throw IOException
                throw new InternalException(e);
            }
        }

        ClassRedefiner.redefineClasses(conf, map);
        for (Class cls : map.keySet()) {
            setIntercepting(conf, envLoader, cls);
            configureMetaData(conf, envLoader, cls, redefine);
        }
        for (Class cls : (Collection<Class>) subs)
            configureMetaData(conf, envLoader, cls, redefine);
        for (Class cls : (Collection<Class>) ints)
            setIntercepting(conf, envLoader, cls);

        return subs;
    }

    private static void configureMetaData(OpenJPAConfiguration conf,
        ClassLoader envLoader, Class cls, boolean redefineAvailable) {
        ClassMetaData meta = conf.getMetaDataRepositoryInstance()
            .getMetaData(cls, envLoader, true);
        configureMetaData(meta, conf, redefineAvailable, true);
    }

    private static void configureMetaData(ClassMetaData meta,
        OpenJPAConfiguration conf, boolean redefineAvailable, boolean warn) {

        setDetachedState(meta);

        if (warn && meta.getAccessType() == ClassMetaData.ACCESS_FIELD
            && !redefineAvailable) {
            // only warn about declared fields; superclass fields will be
            // warned about when the superclass is handled
            for (FieldMetaData fmd : meta.getDeclaredFields()) {
                switch (fmd.getTypeCode()) {
                    case JavaTypes.COLLECTION:
                    case JavaTypes.MAP:
                        // we can lazily load these, since we own the
                        // relationship container
                        break;
                    default:
                        if (!fmd.isInDefaultFetchGroup()
                            && !(fmd.isVersion() || fmd.isPrimaryKey())) {
                            Log log = conf.getLog(
                                OpenJPAConfiguration.LOG_ENHANCE);
                            log.warn(_loc.get("subclasser-fetch-group-override",
                                meta.getDescribedType().getName(),
                                fmd.getName()));
                            fmd.setInDefaultFetchGroup(true);
                        }
                }
            }
        }
    }

    private static void write(BCClass bc, PCEnhancer enhancer,
        Map<Class, byte[]> map, Class cls, List subs, List ints)
        throws IOException {

        if (bc == enhancer.getManagedTypeBytecode()) {
            // if it was already defined, don't put it in the map,
            // but do set the metadata accordingly.
            if (enhancer.isAlreadyRedefined())
                ints.add(bc.getType());
            else if (JavaVersions.VERSION >= 5)
                map.put(bc.getType(), bc.toByteArray());
        } else {
            if (!enhancer.isAlreadySubclassed()) {
                // this is the new subclass
                ClassLoader loader = GeneratedClasses.getMostDerivedLoader(
                    cls, PersistenceCapable.class);
                subs.add(GeneratedClasses.loadBCClass(bc, loader));
            }
        }
    }

    private static void setIntercepting(OpenJPAConfiguration conf,
        ClassLoader envLoader, Class cls) {
        ClassMetaData meta = conf.getMetaDataRepositoryInstance()
            .getMetaData(cls, envLoader, true);
        meta.setIntercepting(true);
    }

    /**
     * If the metadata is configured to use a synthetic
     * detached state, reset it to not use a detached
     * state field, since we can't add fields when redefining.
     */
    private static void setDetachedState(ClassMetaData meta) {
        if (ClassMetaData.SYNTHETIC.equals(meta.getDetachedState()))
            meta.setDetachedState(null);
    }
}
