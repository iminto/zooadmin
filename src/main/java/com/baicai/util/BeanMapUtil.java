package com.baicai.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class BeanMapUtil {
	public static Object convertMap2Bean(Class type, Map map)
			throws IntrospectionException, IllegalAccessException,
			InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (PropertyDescriptor pro : propertyDescriptors) {
			String propertyName = pro.getName();
			if (pro.getPropertyType().getName().equals("java.lang.Class")) {
				continue;
			}
			if (map.containsKey(propertyName)) {
				Object value = map.get(propertyName);
				Method setter = pro.getWriteMethod();
				setter.invoke(obj, value);
			}
		}
		return obj;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map bean2Map(Object bean) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				// System.out.println(descriptor.getName()+"---"+descriptor.getPropertyType().getSimpleName());
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					switch (descriptor.getPropertyType().getSimpleName()) {
					case "String":
						returnMap.put(propertyName, "");
						break;
					case "Integer":
						returnMap.put(propertyName, 0);
						break;
					case "Long":
						returnMap.put(propertyName, 0l);
						break;
					case "BigDecimal":
						returnMap.put(propertyName, 0);
						break;
					case "int":
						returnMap.put(propertyName, 0);
						break;
					case "float":
						returnMap.put(propertyName, 0f);
						break;
					case "double":
						returnMap.put(propertyName, 0d);
						break;
					default:
						returnMap.put(propertyName, null);
					}

				}
			}
		}
		return returnMap;
	}
	
	/**
	 * 将对象转为Map，为null的字段跳过，同时保持有序
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map bean2MapNull(Object bean) throws IntrospectionException,
			IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new TreeMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					//没有值的字段直接跳过
				}
			}
		}
		return returnMap;
	}

	public static String propertyToField(String property) {
		if (property == null) {
			return "";
		}
		char[] chars = property.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			if (StringUtil.isUppercaseLetter(c))
				sb.append("_" + StringUtil.toLowerAscii(c));
			else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String fieldToProperty(String field) {
		if (field == null) {
			return "";
		}
		char[] chars = field.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '_') {
				int j = i + 1;
				if (j < chars.length) {
					sb.append(StringUtil.toUpperAscii(chars[j]));
					i++;
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
