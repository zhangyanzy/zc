package cn.zhaocaiapp.zc_app_android.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.zhaocaiapp.zc_app_android.ZcApplication;

public class SpUtils {


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {

        SharedPreferences sp = ZcApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();

        if (object == null) return;
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {

        SharedPreferences sp = ZcApplication.getPreferences();

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    public static Set<String> getStringSet(String key, Set<String> defValues) {
        SharedPreferences sp = ZcApplication.getPreferences();
        return sp.getStringSet(key, defValues);
    }

    public static void putStringSet(String key, Set<String> values) {
        SharedPreferences sp = ZcApplication.getPreferences();
        SharedPreferences.Editor edit = sp.edit();
        edit.putStringSet(key, values);
        SharedPreferencesCompat.apply(edit);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sp = ZcApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param
     */
    public static void clear() {
        SharedPreferences sp = ZcApplication.getPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sp = ZcApplication.getPreferences();
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = ZcApplication.getPreferences();
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

}
