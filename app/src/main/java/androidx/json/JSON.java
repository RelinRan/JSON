package androidx.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;

/**
 * JSON解析
 */
public class JSON {

    /**
     * 调试模式
     */
    private boolean debug = false;

    /**
     * 设置调试模式
     *
     * @param debug
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * 是否为空
     *
     * @param value 字符
     * @return 是否为空
     */
    public boolean isNone(String value) {
        return value == null || value.length() == 0 || value.equals("null");
    }

    /**
     * 是否不为空
     *
     * @param value 字符
     * @return
     */
    public boolean isNotNone(String value) {
        return !isNone(value);
    }

    /**
     * 非空
     *
     * @param json
     * @return
     */
    public String nonempty(String json) {
        return isNone(json) ? "" : json;
    }

    /**
     * 打印日志
     *
     * @param content
     */
    public void print(Object... content) {
        if (debug) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < content.length; i++) {
                sb.append(content[i]).append(" ");
            }
            System.out.println(sb);
        }
    }

    /**
     * @param json 字符
     * @return JSONObject对象
     */
    public JSONObject toJSONObject(String json) {
        if (isNone(json)) {
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * @param object 对象
     * @return Map数据对象
     */
    public Map<String, Object> toMap(JSONObject object) {
        if (object == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Iterator<String> iterator = object.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                Object value = object.get(key);
                map.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * @param object 对象
     * @param clazz  类型
     * @return Map数据对象
     */
    public <T> Map<String, T> toMap(JSONObject object, Class<T> clazz) {
        if (object == null) {
            return null;
        }
        Map<String, T> map = new HashMap<>();
        Iterator<String> iterator = object.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                Object value = object.get(key);
                map.put(key, (T) value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * @param json 字符串
     * @return 字符转Map对象
     */
    public Map<String, Object> toMap(String json) {
        if (isNone(json)) {
            return null;
        }
        if (json.startsWith("{}")) {
            return null;
        }
        return toMap(toJSONObject(json));
    }

    /**
     * @param json  字符串
     * @param clazz 类型
     * @return 字符转Map对象
     */
    public <T> Map<String, T> toMap(String json, Class<T> clazz) {
        if (isNone(json)) {
            return null;
        }
        if (json.startsWith("{}")) {
            return null;
        }
        return toMap(toJSONObject(json), clazz);
    }

    /**
     * @param clazz     类
     * @param fieldName 字段名称
     * @return 是否是声明的字段
     */
    public boolean isDeclaredField(Class clazz, String fieldName) {
        if (clazz == null) {
            return false;
        }
        if (fieldName == null || fieldName.length() == 0) {
            return false;
        }
        Field[] fields = findClassDeclaredFields(clazz);
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            if (name == null) {
                return false;
            }
            if (fieldName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取JSONArray的值
     *
     * @param array JSONArray
     * @param index 下标
     * @return
     */
    public Object getJSONArrayValue(JSONArray array, int index) {
        Object value;
        try {
            value = array.get(index);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    /**
     * 根据类型转对应数组
     *
     * @param componentType 数组类型
     * @param jsonArray     json数组
     * @return 数组实例
     */
    public Object toArray(Class componentType, JSONArray jsonArray) {
        int length = jsonArray.length();
        print("to array length:", length, "componentType:", componentType);
        Object arrayObj = Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            Object obj = getJSONArrayValue(jsonArray, i);
            if (componentType == String.class) {
                String[] arrays = (String[]) arrayObj;
                arrays[i] = (String) obj;
            }
            if (componentType == Character.class) {
                Character[] arrays = (Character[]) arrayObj;
                arrays[i] = (Character) obj;
            }
            if (componentType == CharSequence.class) {
                CharSequence[] arrays = (CharSequence[]) arrayObj;
                arrays[i] = (CharSequence) obj;
            }
            if (componentType == int.class) {
                int[] arrays = (int[]) arrayObj;
                arrays[i] = (int) obj;
            }
            if (componentType == Integer.class) {
                Integer[] arrays = (Integer[]) arrayObj;
                arrays[i] = (Integer) obj;
            }
            if (componentType == long.class) {
                long[] arrays = (long[]) arrayObj;
                arrays[i] = (long) obj;
            }
            if (componentType == Long.class) {
                Long[] arrays = (Long[]) arrayObj;
                arrays[i] = (Long) obj;
            }
            if (componentType == double.class) {
                double[] arrays = (double[]) arrayObj;
                arrays[i] = (double) obj;
            }
            if (componentType == Double.class) {
                Double[] arrays = (Double[]) arrayObj;
                arrays[i] = (Double) obj;
            }
            if (componentType == float.class) {
                float[] arrays = (float[]) arrayObj;
                arrays[i] = (float) obj;
            }
            if (componentType == Float.class) {
                Float[] arrays = (Float[]) arrayObj;
                arrays[i] = (Float) obj;
            }
            if (componentType == short.class) {
                short[] arrays = (short[]) arrayObj;
                arrays[i] = (short) obj;
            }
            if (componentType == Short.class) {
                Short[] arrays = (Short[]) arrayObj;
                arrays[i] = (Short) obj;
            }
            if (componentType == boolean.class) {
                boolean[] arrays = (boolean[]) arrayObj;
                arrays[i] = (boolean) obj;
            }
            if (componentType == Boolean.class) {
                Boolean[] arrays = (Boolean[]) arrayObj;
                arrays[i] = (Boolean) obj;
            }
        }
        return arrayObj;
    }

    /**
     * 根据字段+占位符参数获取类型
     *
     * @param field           字段
     * @param variableTypeMap 变量类型
     * @return 字段类型
     */
    public Class<?> getType(Field field, Map<String, Class<?>> variableTypeMap) {
        Class fieldType = field.getType();
        //泛型占位符
        if (field.getGenericType() instanceof TypeVariable && variableTypeMap != null) {
            fieldType = findVariableType(variableTypeMap, field.getName());
        }
        return fieldType;
    }

    /**
     * 是否小数
     *
     * @param value
     * @return
     */
    public boolean isDecimal(String value) {
        return value != null && value.contains(".");
    }

    /**
     * 小数
     *
     * @param value
     * @return
     */
    public String decimal(String value) {
        value = value.length() == 0 ? "0.00" : value;
        return value.contains(".") ? value : value + ".00";
    }

    /**
     * 整数
     *
     * @param value
     * @return
     */
    public String integer(String value) {
        return value.length() == 0 ? "0" : value;
    }

    /**
     * 设置字段值
     *
     * @param field    字段
     * @param bean     对象
     * @param value    值
     * @param variable 变量类型
     */
    public void setFieldValue(Field field, Object bean, String value, Map<String, Class<?>> variable) {
        try {
            Class fieldType = getType(field, variable);
            //字符
            if (fieldType == String.class || fieldType == Character.class || fieldType == CharSequence.class) {
                field.set(bean, value);
            }
            //Int类型
            if (fieldType == int.class || fieldType == Integer.class) {
                if (!isDecimal(value)) {
                    field.set(bean, Integer.parseInt(integer(value)));
                }
            }
            //Short类型
            if (fieldType == short.class || fieldType == Short.class) {
                if (!isDecimal(value)) {
                    field.set(bean, Short.parseShort(integer(value)));
                }
            }
            //Long类型
            if (fieldType == long.class || fieldType == Long.class) {
                if (!isDecimal(value)) {
                    field.set(bean, Long.parseLong(integer(value)));
                }
            }
            //Double类型
            if (field.getType() == double.class || fieldType == Double.class) {
                field.set(bean, Double.parseDouble(decimal(value)));
            }
            //Float类型
            if (fieldType == float.class || fieldType == Float.class) {
                field.set(bean, Float.parseFloat(decimal(value)));
            }
            //Boolean类型
            if (fieldType == boolean.class || fieldType == Boolean.class) {
                value = value.length() == 0 ? "false" : value;
                boolean booleanValue = false;
                if (value.equals("false") || value.equals("0")) {
                    booleanValue = false;
                }
                if (value.equals("true") || value.equals("1")) {
                    booleanValue = true;
                }
                field.set(bean, booleanValue);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param clazz 类
     * @param name  字段名称
     * @return 本类及其父类寻找是否有此类
     */
    public Field findClassField(Class clazz, String name) {
        for (Field field : findClassDeclaredFields(clazz)) {
            field.setAccessible(true);
            if (field.getName().equals(name)) {
                return field;
            }
        }
        return null;
    }

    /**
     * 根据类获取对应的集合
     *
     * @param collectionType 类型
     * @return
     */
    public Collection toCollection(Class collectionType) {
        Collection collection = new ArrayList();
        if (List.class.isAssignableFrom(collectionType)) {
            collection = new ArrayList<>();
        }
        if (ArrayList.class.isAssignableFrom(collectionType)) {
            collection = new ArrayList();
        }
        if (LinkedList.class.isAssignableFrom(collectionType)) {
            collection = new LinkedList();
        }
        if (Vector.class.isAssignableFrom(collectionType)) {
            collection = new Vector();
        }
        if (Stack.class.isAssignableFrom(collectionType)) {
            collection = new Stack();
        }
        return collection;
    }

    /**
     * @param array 对象
     * @param clazz 数据对象类
     * @return JsonObject转对象
     */
    public <T> T toObject(JSONArray array, Class<T> clazz) {
        T bean = null;
        if (clazz == null || array == null) {
            return null;
        }
        print("to object class:", clazz);
        //Collection
        if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = toCollection(clazz);
            for (int i = 0; i < array.length(); i++) {
                collection.add(getJSONArrayValue(array, i));
            }
            bean = (T) collection;
        } else if (Array.class.isAssignableFrom(clazz)) {
            //Array
            Object[] arrays = new Object[array.length()];
            for (int i = 0; i < array.length(); i++) {
                arrays[i] = getJSONArrayValue(array, i);
            }
            bean = (T) arrays;
        }
        return bean;
    }

    /**
     * class转实列对象
     *
     * @param clazz 类
     * @param <T>
     * @return
     */
    public <T> T toBean(Class<T> clazz) {
        T bean = null;
        try {
            //获取到所有访问权限的构造函数（包括private的构造函数）
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            if (constructors.length == 0) {
                bean = clazz.newInstance();
            } else {
                Constructor constructor = null;
                for (Constructor item : constructors) {
                    if (item.getParameterTypes().length == 0) {
                        constructor = item;
                    }
                }
                if (constructor != null) {
                    constructor.setAccessible(true);
                    bean = (T) constructor.newInstance();
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return bean;
    }

    /**
     * @param object          对象
     * @param clazz           数据对象类
     * @param variableTypeMap 变量类型Map
     * @return JsonObject转对象
     */
    public <T> T toObject(JSONObject object, Class<T> clazz, Map<String, Class<?>> variableTypeMap) {
        T bean = null;
        if (clazz == null || object == null) {
            return null;
        }
        if (Map.class.isAssignableFrom(clazz)) {
            return (T) toMap(object.toString(), clazz);
        }
        //获取到所有访问权限的构造函数（包括private的构造函数）
        bean = toBean(clazz);
        Iterator<String> iterator = object.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (isDeclaredField(clazz, key)) {
                setObjectValue(clazz, variableTypeMap, bean, object, key);
            }
        }
        return bean;
    }

    /**
     * 找到变量类型
     *
     * @param variable 变量
     * @param key      字段名称
     * @return
     */
    public Class<?> findVariableType(Map<String, Class<?>> variable, String key) {
        if (variable != null && key != null) {
            return variable.get(key);
        }
        return null;
    }

    /**
     * 获取泛型字段的参数类型数组
     *
     * @param field 字段
     * @return
     */
    public Type[] getGenericParameterType(Field field) {
        Type genericType = field.getGenericType();//获取字段的泛型类型
        Type[] typeArguments;
        if (genericType instanceof ParameterizedType) {
            print("ParameterizedType", genericType);
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            typeArguments = parameterizedType.getActualTypeArguments();//获取实际类型参数
        } else {
            print("Not ParameterizedType", genericType);
            typeArguments = ((Class<?>) genericType).getTypeParameters();
        }
        return typeArguments;
    }

    /**
     * 获取泛型参数类
     *
     * @param argumentType 泛型参数类型
     * @return
     */
    public Class getGenericParameterClass(Type argumentType) {
        Class<?> parameterClazz;
        if (argumentType instanceof Class) {
            parameterClazz = (Class<?>) argumentType;
        } else if (argumentType instanceof ParameterizedType) {
            parameterClazz = (Class<?>) ((ParameterizedType) argumentType).getRawType();
        } else if (argumentType instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) argumentType).getGenericComponentType();
            if (componentType instanceof Class) {
                parameterClazz = Array.newInstance((Class<?>) componentType, 0).getClass();
            } else {
                throw new IllegalArgumentException("Unsupported generic array type");
            }
        } else {
            String typeName = getTypeName(argumentType);
            if (!typeName.equals("E")) {
                try {
                    parameterClazz = Class.forName(typeName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                //未指定泛型类型默认为Object
                parameterClazz = Object.class;
            }
        }
        return parameterClazz;
    }

    /**
     * 设置对象值
     *
     * @param clazz           对象类
     * @param variableTypeMap 占位泛型
     * @param clazzBean       对象实例化
     * @param object          json对象
     * @param key             JSON字段名称
     * @param <T>             实体
     */
    public <T> void setObjectValue(Class<T> clazz, Map<String, Class<?>> variableTypeMap, T clazzBean, JSONObject object, String key) {
        try {
            Field field = findClassField(clazz, key);
            if (field != null) {
                field.setAccessible(true);
                Object value = object.get(key);
                String valueString = nonempty(String.valueOf(value));
                Class<?> fieldType = getType(field, variableTypeMap);
                String log = debug ? key + " " + fieldType + " " + valueString : "";
                if (isPrimitive(fieldType)) {
                    //Primitive
                    print("Primitive", log);
                    setFieldValue(field, clazzBean, valueString, variableTypeMap);
                } else {
                    //Collection
                    if (Collection.class.isAssignableFrom(fieldType)) {
                        print("Collection", log);
                        Type[] typeArguments = getGenericParameterType(field);
                        if (typeArguments.length > 0) {
                            Class paramsClazz = getGenericParameterClass(typeArguments[0]);
                            print(paramsClazz);
                            if (isPrimitive(paramsClazz)) {
                                field.set(clazzBean, toCollection(valueString, fieldType, paramsClazz, variableTypeMap));
                            } else if (Collection.class.isAssignableFrom(paramsClazz)) {
                                field.set(clazzBean, toCollection(field, paramsClazz, variableTypeMap, valueString));
                            } else if (Map.class.isAssignableFrom(paramsClazz)) {
                                field.set(clazzBean, toMapCollection(valueString));
                            } else {
                                field.set(clazzBean, toCollection(valueString, fieldType, paramsClazz, variableTypeMap));
                            }
                        }
                    } else if (fieldType.isArray()) {
                        //Array
                        print("Array", log);
                        JSONArray jsonArray = (JSONArray) value;
                        Class componentType = fieldType.getComponentType();
                        field.set(clazzBean, toArray(componentType, jsonArray));
                    } else if (Map.class.isAssignableFrom(fieldType)) {
                        //Map
                        print("Map", log);
                        JSONObject jsonObject = (JSONObject) value;
                        Map<String, Object> map = new HashMap<>();
                        Iterator it = jsonObject.keys();
                        while (it.hasNext()) {
                            String name = (String) it.next();
                            Object val = jsonObject.get(name);
                            map.put(name, val);
                        }
                        field.set(clazzBean, map);
                    } else if (JSONArray.class.isAssignableFrom(fieldType)) {
                        //JSONArray
                        print("JSONArray", clazzBean.getClass(), fieldType, valueString);
                        field.set(clazzBean, toJSONArray(valueString));
                    } else if (JSONObject.class.isAssignableFrom(fieldType)) {
                        //JSONObject
                        print("JSONObject", log);
                        field.set(clazzBean, toJSONObject(valueString));
                    } else if (Object.class.isAssignableFrom(fieldType)) {
                        //Object
                        print("Object", log);
                        field.set(clazzBean, toObject(valueString, fieldType, variableTypeMap));
                    } else {
                        print("Other", log);
                        field.set(clazzBean, toObject(valueString, fieldType, variableTypeMap));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String getTypeName(Type type) {
        String typeName = type.toString();
        int startIndex = typeName.indexOf(' ') + 1;
        if (typeName.startsWith("class")) {
            startIndex += "class".length();
        } else if (typeName.startsWith("interface")) {
            startIndex += "interface".length();
        }
        return typeName.substring(startIndex);
    }

    /**
     * @param json  字符串
     * @param clazz 类
     * @return 数据对象
     */
    public <T> T toObject(String json, Class<T> clazz) {
        if (isNone(json)) {
            return null;
        }
        print("[───────────────────────────────────OBJECT───────────────────────────────────]");
        if (isJSONArray(json)) {
            return toObject(toJSONArray(json), clazz);
        }
        return toObject(toJSONObject(json), clazz, null);
    }

    /**
     * json转Object
     *
     * @param json         JSON字符串
     * @param clazz        object类
     * @param field        泛型字段名称
     * @param variableType 变量类型
     * @param <T>
     * @return
     */
    public <T> T toObject(String json, Class<T> clazz, String field, Class<?> variableType) {
        Map<String, Class<?>> variable = new HashMap<>();
        variable.put(field, variableType);
        if (isNone(json)) {
            return null;
        }
        return toObject(toJSONObject(json), clazz, variable);
    }

    /**
     * JSON转Object
     *
     * @param json         JSON字符串
     * @param clazz        object类
     * @param variableType 变量类型
     * @param <T>
     * @return
     */
    public <T> T toObject(String json, Class<T> clazz, Class<?> variableType) {
        Map<String, Class<?>> variable = new HashMap<>();
        String key = variableType == null ? "" : variableType.getSimpleName().toLowerCase();
        variable.put(key, variableType);
        if (isNone(json)) {
            return null;
        }
        return toObject(toJSONObject(json), clazz, variable);
    }

    /**
     * @param json            字符串
     * @param clazz           类
     * @param variableTypeMap 变量类型Map
     * @param <T>
     * @return
     */
    public <T> T toObject(String json, Class<T> clazz, Map<String, Class<?>> variableTypeMap) {
        if (isNone(json)) {
            return null;
        }
        if (isJSONArray(json)) {
            return toObject(toJSONArray(json), clazz);
        }
        if (isJSONObject(json)) {
            return toObject(toJSONObject(json), clazz, variableTypeMap);
        }
        return (T) json;
    }

    /**
     * @param json Json字符串转
     * @return JSONArray对象
     */
    public JSONArray toJSONArray(String json) {
        if (json == null || json.length() == 0 || json.equals("null")) {
            return null;
        }
        if (!json.startsWith("[") && !json.endsWith("]")) {
            return null;
        }
        return newJSONArray(json);
    }

    /**
     * JSONArray对象
     *
     * @param json JSON
     * @return
     */
    public JSONArray newJSONArray(String json) {
        try {
            return new JSONArray(json);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param array JSONArray对象
     * @return Map对象的列表数据
     */
    public List<Map<String, Object>> toMapCollection(JSONArray array) {
        if (array == null) {
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                Object obj = array.get(i);
                if (obj instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) array.get(i);
                    list.add(toMap(jsonObject));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * @param json Json字符串
     * @return Map对象的列表数据
     */
    public List<Map<String, Object>> toMapCollection(String json) {
        if (isNone(json)) {
            return null;
        }
        if (json.equals("[]")) {
            return new ArrayList<>();
        }
        JSONArray array = toJSONArray(json);
        if (array == null) {
            return null;
        }
        return toMapCollection(array);
    }

    /**
     * @param field 列表字段
     * @param clazz 列表字段的参数对象
     * @param json  json字符串
     * @param <T>
     * @return JsonArray转List对象
     */
    public <T> List<T> toCollection(Field field, Class<T> clazz, String json) {
        return toCollection(field, clazz, null, json);
    }

    /**
     * @param field    列表字段
     * @param clazz    列表字段的参数对象
     * @param variable 变量类型
     * @param json     json字符串
     * @return JsonArray转List对象
     */
    public <T> List<T> toCollection(Field field, Class<T> clazz, Map<String, Class<?>> variable, String json) {
        List<T> list = null;
        try {
            if (field.getType() == List.class) {
                list = new ArrayList<>();
            } else {
                list = (List<T>) field.getType().newInstance();
            }
            if (isJSONArray(json)) {
                JSONArray jsonArray = toJSONArray(json);
                int size = jsonArray == null ? 0 : jsonArray.length();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    T t = toObject(jsonObject, clazz, variable);
                    list.add(t);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Json转List对象，此处只要是是实现了List接口的对象都可以
     *
     * @param array 数组
     * @param clazz 对象类
     * @param <T>
     * @return
     */
    public <T> List<T> toCollection(JSONArray array, Class<T> clazz) {
        return toCollection(array, clazz, null);
    }

    /**
     * Json转List对象，此处只要是是实现了List接口的对象都可以
     *
     * @param array    数组
     * @param clazz    对象类
     * @param variable 变量类型
     * @return 列表数据
     */
    public <T> List<T> toCollection(JSONArray array, Class<T> clazz, Map<String, Class<?>> variable) {
        List<T> list = new ArrayList<>();
        if (array == null) {
            return list;
        }
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) array.get(i);
                list.add(toObject(jsonObject, clazz, variable));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * JSON转List
     *
     * @param json  JSON
     * @param clazz 集合参数类型
     * @param <T>
     * @param <C>
     * @return
     */
    public <T, C extends List> C toList(String json, Class<T> clazz) {
        return toCollection(json, List.class, clazz, null);
    }

    /**
     * JSON转List
     *
     * @param json            JSON
     * @param clazz           集合参数类型
     * @param variableTypeMap 变量类型（泛型字段+类型）
     * @param <T>
     * @param <C>
     * @return
     */
    public <T, C extends List> C toList(String json, Class<T> clazz, Map<String, Class<?>> variableTypeMap) {
        return toCollection(json, List.class, clazz, variableTypeMap);
    }

    /**
     * @param json           字符
     * @param collectionType 集合类型
     * @param clazz          对象类
     * @param <T>
     * @return JSONArray对象字符串转换为对象
     */
    public <T, C extends Collection> C toCollection(String json, Class<?> collectionType, Class<T> clazz) {
        return toCollection(json, collectionType, clazz, null);
    }

    /**
     * JSONArray对象字符串转换为对象
     *
     * @param json            字符
     * @param clazz           对象类
     * @param variableTypeMap 变量类型
     * @return 列表数据
     */
    public <T, C extends Collection> C toCollection(String json, Class<?> collectionType, Class<T> clazz, Map<String, Class<?>> variableTypeMap) {
        Collection collection = toCollection(collectionType);
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Object obj = jsonArray.get(i);
                if (isPrimitive(obj.getClass())) {
                    collection.add((T) obj);
                }
                if (obj.getClass().isAssignableFrom(JSONObject.class)) {
                    collection.add(toObject((JSONObject) obj, clazz, variableTypeMap));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (C) collection;
    }

    /**
     * json字符串转数组
     *
     * @param json  JSON
     * @param clazz 数组类型，例如：String、Integer
     * @param <T>
     * @return
     */
    public <T> T[] toArray(String json, Class<T> clazz) {
        Object array = null;
        if (isJSONArray(json)) {
            array = toArray(clazz, newJSONArray(json));
        }
        return array == null ? null : (T[]) array;
    }

    /**
     * 是否基础数据类型
     *
     * @param type 类型
     * @return
     */
    public boolean isPrimitive(Class<?> type) {
        if (type == null) {
            return false;
        }
        if (type.isAssignableFrom(Object.class)) {
            return false;
        }
        return type.isPrimitive() || type.isAssignableFrom(String.class) || type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(Character.class) || type.isAssignableFrom(Byte.class) || type.isAssignableFrom(Short.class) || type.isAssignableFrom(Integer.class) || type.isAssignableFrom(Long.class) || type.isAssignableFrom(Float.class) || type.isAssignableFrom(Double.class) || type.isAssignableFrom(Void.class);
    }

    /**
     * @param json 字符
     * @return 是否集合|数组
     */
    public boolean isJSONArray(String json) {
        return json != null && json.startsWith("[") && json.endsWith("]");
    }

    /**
     * @param json 字符
     * @return 是否对象
     */
    public boolean isJSONObject(String json) {
        return json != null && json.startsWith("{") && json.endsWith("}");
    }

    /**
     * 添加到JSONObject
     *
     * @param jsonObject json对象
     * @param key        键
     * @param value      值
     */
    public void addJSONObjectKeyValue(JSONObject jsonObject, String key, Object value) {
        try {
            if (value != null) {
                if (isPrimitive(value.getClass())) {
                    jsonObject.put(key, value);
                } else {
                    String objValueJson = toJson(value);
                    jsonObject.put(key, isJSONObject(objValueJson) ? new JSONObject(objValueJson) : new JSONArray(objValueJson));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param clazz 类
     * @return 当前类及其父类类声明字段
     */
    public Field[] findClassDeclaredFields(Class clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (isCustomField(field)) {
                    field.setAccessible(true);
                    fields.add(field);
                }
            }
            clazz = clazz.getSuperclass();
            if (clazz != null) {
            }
        }
        return fields.toArray(new Field[fields.size()]);
    }

    /**
     * 是否自己定义的字段
     *
     * @param field 字段
     * @return
     */
    public boolean isCustomField(Field field) {
        int modifiers = field.getModifiers();
        if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
            return true;
        }
        return false;
    }

    /**
     * @param obj
     * @param format
     * @return
     */
    public String toJson(Object obj, boolean format) {
        String json = toJson(obj);
        return format ? format(json) : json;
    }

    /**
     * JSONObject插入值
     *
     * @param object JSONObject
     * @param key    键
     * @param value  值
     */
    public void put(JSONObject object, String key, Object value) {
        try {
            object.putOpt(key, value);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建JSONObject
     *
     * @param json JSON
     * @return
     */
    public JSONObject newJSONObject(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param obj 对象
     * @return json字符
     */
    public String toJson(Object obj) {
        if (obj == null) {
            return "{}";
        }
        print("[───────────────────────────────────JSON───────────────────────────────────]");
        Class<?> cls = obj.getClass();
        if (String.class.isAssignableFrom(cls)) {
            JSONObject object = new JSONObject();
            put(object, "text", obj);
            print("String", cls);
            return String.valueOf(object);
        } else if (JSONObject.class.isAssignableFrom(cls)) {
            print("JSONObject", cls);
            return String.valueOf(obj);
        } else if (JSONArray.class.isAssignableFrom(cls)) {
            print("JSONArray", cls);
            return String.valueOf(obj);
        } else if (Collection.class.isAssignableFrom(cls)) {
            //Collection
            JSONArray jsonArray = new JSONArray();
            Collection collection = (Collection) obj;
            int count = collection == null ? 0 : collection.size();
            print("Collection", cls, "count", "=", count);
            //基本类型集合
            List<Object> primitiveArray = new ArrayList<>();
            Iterator<?> iterator = collection.iterator();
            while (iterator.hasNext()) {
                Object element = iterator.next();
                if (isPrimitive(element == null ? null : element.getClass())) {
                    primitiveArray.add(element);
                } else {
                    jsonArray.put(newJSONObject(toJson(element)));
                }
            }
            if (primitiveArray.size() > 0) {
                jsonArray = new JSONArray(primitiveArray);
            }
            print("Collection", cls, jsonArray);
            return jsonArray.toString();
        } else if (Map.class.isAssignableFrom(cls)) {
            //Map
            print("Map", cls);
            JSONObject jsonObject = new JSONObject();
            Map<String, Object> objMap = (Map<String, Object>) obj;
            for (String key : objMap.keySet()) {
                Object objValue = objMap.get(key);
                addJSONObjectKeyValue(jsonObject, key, objValue);
            }
            return jsonObject.toString();
        } else if (cls.isArray()) {
            //Array
            print("Array", cls);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < Array.getLength(obj); i++) {
                jsonArray.put(Array.get(obj, i));
            }
            return jsonArray.toString();
        } else {
            //普通类
            print("Object", cls);
            JSONObject jsonObject = new JSONObject();
            Field[] fields = findClassDeclaredFields(cls);
            if (fields.length == 0) {
                return jsonObject.toString();
            }
            for (Field field : fields) {
                field.setAccessible(true);
                Class<?> type = field.getGenericType().getClass();
                String name = field.getName();
                if (type != null) {
                    try {
                        //普通类型
                        Object value = field.get(obj);
                        if (isPrimitive(type)) {
                            jsonObject.put(name, value);
                        } else {
                            addJSONObjectKeyValue(jsonObject, name, value);
                        }
                    } catch (IllegalAccessException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return jsonObject.toString();
        }
    }

    /**
     * JSON格式化
     *
     * @param json 字符
     * @return
     */
    public String format(String json) {
        if (isNone(json)) {
            return "";
        }
        if (isJSONObject(json)) {
            JSONObject object = newJSONObject(json);
            json = object.toString();
        }
        if (isJSONArray(json)) {
            JSONArray array = newJSONArray(json);
            json = array.toString();
        }
        return json;
    }

}
