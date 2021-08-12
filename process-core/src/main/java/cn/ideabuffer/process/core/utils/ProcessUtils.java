package cn.ideabuffer.process.core.utils;

import cn.ideabuffer.process.core.KeyManager;
import cn.ideabuffer.process.core.context.Key;
import cn.ideabuffer.process.core.exceptions.UnregisteredKeyException;
import cn.ideabuffer.process.core.nodes.ExecutableNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sangjian.sj
 * @date 2021/07/25
 */
public class ProcessUtils {

    private ProcessUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void checkRegisteredKeys(KeyManager parent, List<? extends KeyManager> children) {
        Set<Key<?>> readableKeys = parent.getReadableKeys() == null ? new HashSet<>() : parent.getReadableKeys();
        Set<Key<?>> writableKeys = parent.getWritableKeys() == null ? new HashSet<>() : parent.getWritableKeys();
        if (children != null) {
            for (KeyManager manager : children) {
                Set<Key<?>> mrKeys = manager.getReadableKeys();
                Set<Key<?>> mwKeys = manager.getWritableKeys();
                Key<?> resultKey = null;
                if (manager instanceof ExecutableNode) {
                    resultKey = ((ExecutableNode)manager).getResultKey();
                }
                if (mrKeys != null && !mrKeys.isEmpty()) {
                    Set<Key<?>> unregisteredKeys = new HashSet<>();
                    for (Key<?> key : mrKeys) {
                        if (!readableKeys.contains(key)) {
                            unregisteredKeys.add(key);
                        }
                    }
                    if (!unregisteredKeys.isEmpty()) {
                        throw new UnregisteredKeyException(String
                            .format("found unregistered readable keys:%s, please register first! ", unregisteredKeys));
                    }
                }

                if (mwKeys != null && !mwKeys.isEmpty()) {
                    Set<Key<?>> unregisteredKeys = new HashSet<>();
                    for (Key<?> key : mwKeys) {
                        if (!writableKeys.contains(key)) {
                            unregisteredKeys.add(key);
                        }
                    }
                    if (!unregisteredKeys.isEmpty()) {
                        throw new UnregisteredKeyException(String
                            .format("found unregistered writable keys:%s, please register first! ", unregisteredKeys));
                    }
                }
                if (resultKey != null) {
                    if (!writableKeys.contains(resultKey)) {
                        throw new UnregisteredKeyException(String
                            .format("found unregistered result key:%s, please register the key in parent writableKeys field first! ", resultKey));
                    }
                }
            }
        }
    }
}
