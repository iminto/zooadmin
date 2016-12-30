package com.baicai.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 数组操作辅助类,支持int,char,boject,String类型
 */
public class ArrayHelper {

	/**
	 * 锁定创建
	 */
	private ArrayHelper() {
	}

	/**
	 * 
	 * 计算数组中的单元数目或对象中的属性个数 当 arr 为 null 时返回 0。
	 * 
	 * @param arr
	 * @return
	 */
	public static int sizeOf(int[] arr) {
		return arr == null ? 0 : arr.length;
	}

	/**
	 * 
	 * 合并一个或多个数组 当 arr 为 null 时返回 new int[0] 。
	 * 
	 * @param arrs
	 * @return
	 */
	public static int[] merge(int[]... arrs) {
		int[] result = new int[0];
		if (arrs != null) {
			int count = 0;
			for (int i = 0; i < arrs.length; i++) {
				count += arrs[i].length;
			}
			result = new int[count];
			int arg;
			int k = 0;
			for (int i = 0; i < arrs.length; i++) {
				if (arrs[i] == null) {
					continue;
				}
				for (int j = 0; j < arrs[i].length; j++) {
					arg = arrs[i][j];
					result[k] = arg;
					k++;
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * 计算数组的差集 注意： aArr 为null时 返回 bArr(可能为 null)。 bArr 为null时 返回 aArr。 无差集时返回
	 * new int[0];
	 * 
	 * @param aArr
	 * @param bArr
	 * @return
	 */
	public static int[] diff(int[] aArr, int[] bArr) {
		{
			if (aArr == null) {
				return bArr;
			}
			if (bArr == null) {
				return aArr;
			}
		}
		int[] cArr = new int[aArr.length + bArr.length];
		int idx = 0;
		/**
		 * 检查 a 中那些元 在 b 中不存在
		 */
		for (int i = 0; i < aArr.length; i++) {
			if (!inArray(bArr, aArr[i])) {
				cArr[idx++] = aArr[i];
			}
		}
		/**
		 * 检查 b 中那些元 在 a 中不存在
		 */
		for (int i = 0; i < bArr.length; i++) {
			if (!inArray(aArr, bArr[i])) {
				cArr[idx++] = bArr[i];
			}
		}
		//
		int[] dArr = new int[idx];
		System.arraycopy(cArr, 0, dArr, 0, dArr.length);
		return dArr;
	}

	/**
	 * 
	 * 返回一个数组的全复制 当 arr 为 null 时返回 new int[0] 。
	 * 
	 * @param arr
	 * @return
	 */
	public static int[] copyOf(int[] arr) {
		int[] carr = new int[0];
		if (arr != null) {
			carr = new int[arr.length];
			System.arraycopy(arr, 0, carr, 0, arr.length);
		}
		return carr;
	}

	/**
	 * 
	 * 返回一个数组的复制 当 arr 为 null 时返回 new int[0] 。
	 * 
	 * @param arr
	 * @param i
	 * @return
	 */
	public static int[] copyOf(int[] arr, int i) {
		int[] carr = new int[0];
		if (arr != null) {
			if (i > arr.length) {
				i = arr.length;
			}
			carr = new int[i];
			System.arraycopy(arr, 0, carr, 0, i);
		}
		return carr;
	}

	/**
	 * 
	 * 创建一个Map，用一个数组的值作为其键名，另一个数组的值作为其值
	 * 当键为空或无单元时返回空Map，否则以键数组为主导填充Map。值不足时填充null。 注意：Map中键顺序并不一定与参数keys顺序相同。
	 * 
	 * @param <T>
	 * @param <V>
	 * @param keys
	 * @param values
	 * @return
	 */
	public static <T, V> Map<T, V> combine(T[] keys, V[] values) {
		Map<T, V> map = new HashMap<T, V>();
		if (keys != null && keys.length > 0) {
			int vsize = values == null ? 0 : values.length;
			for (int i = 0; i < keys.length; i++) {
				if (i >= vsize) {
					map.put(keys[i], null);
				} else {
					map.put(keys[i], values[i]);
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * 从数组中随机取出一个或多个单元 当 arr 为 null 时返回 new int[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static int[] random(int[] arr) {
		if (arr == null) {
			return new int[0];
		}
		int count = new Random().nextInt(arr.length);
		count = count > 0 ? count : 1;
		return random(arr, count);
	}

	/**
	 * 
	 * 将数组打乱 当 arr 为 null 时返回 new int[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static int[] shuffle(int[] arr) {
		if (arr == null) {
			return new int[0];
		}
		return random(arr, arr.length);
	}

	/**
	 * 
	 * 随机取出固定数量的元素 如取出数量大于数组元素数则返回打乱的数组，等同于 {@link #shuffle} 当 arr 为 null 时返回
	 * new int[0]。
	 * 
	 * @param arr
	 * @param count
	 * @return
	 */
	public static int[] random(int[] arr, int count) {
		int[] rarr = new int[0];
		if (arr != null) {
			if (arr.length < count) {
				count = arr.length;
			}
			Random random = new Random();
			int i = 0;
			int num = 0;
			rarr = new int[count];
			int[] keys = new int[count];
			for (int j = 0; j < keys.length; j++) {
				keys[j] = -1;
			}
			do {
				num = random.nextInt(arr.length);
				if (!inArray(keys, num)) {
					keys[i] = num;
					rarr[i] = arr[num];
					i++;
				}
			} while (i < count);
		}
		return rarr;
	}

	/**
	 * 
	 * 检查数组中是否存在某个值
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static boolean inArray(int[] arr, int search) {
		if (arr != null) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == search) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * 检查数组中是否存在某几个值
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static boolean inArray(int[] arr, int[] search) {
		if (search != null) {
			for (int i = 0; i < search.length; i++) {
				if (!inArray(arr, search[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 检查数组中是否存在某个值,并返回其索引键，不存在则返回 -1 。
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static int search(int[] arr, int search) {
		if (arr != null) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == search) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 
	 * 返回一个单元顺序相反的数组 当 arr 为 null 时返回 new int[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static int[] reverse(int[] arr) {
		int[] rarr = new int[0];
		if (arr != null) {
			rarr = new int[arr.length];
			int j = 0;
			for (int i = arr.length - 1; i >= 0; i--) {
				rarr[j++] = arr[i];
			}
		}
		return rarr;
	}

	/**
	 * 使用间隔符连接
	 * 
	 * @param ints
	 * @param sep
	 * @return
	 */
	public static String join(int[] ints, String sep) {
		StringBuilder strBuilder = new StringBuilder();
		if (ints != null) {
			for (int i = 0; i < ints.length; i++) {
				strBuilder.append(ints[i]);
				if (i < ints.length - 1) {
					strBuilder.append(sep);
				}
			}
		}
		return strBuilder.toString();
	}

	/**
	 * 计算所有单元的合
	 * 
	 * @param ints
	 * @param sep
	 * @return
	 */
	public static int sum(int[] ints) {
		int sum = 0;
		if (ints != null) {
			for (int i = 0; i < ints.length; i++) {
				sum += ints[i];
			}
		}
		return sum;
	}

	/**
	 * char array tools =====================
	 */
	/**
	 * 
	 * 计算数组中的单元数目或对象中的属性个数 当 arr 为 null 时返回 0。
	 * 
	 * @param arr
	 * @return
	 */
	public static int sizeOf(char[] arr) {
		return arr == null ? 0 : arr.length;
	}

	/**
	 * 
	 * 合并一个或多个数组 当 arr 为 null 时返回 new char[0] 。
	 * 
	 * @param arrs
	 * @return
	 */
	public static char[] merge(char[]... arrs) {
		char[] result = new char[0];
		if (arrs != null) {
			char count = 0;
			for (char i = 0; i < arrs.length; i++) {
				count += arrs[i].length;
			}
			result = new char[count];
			char arg;
			char k = 0;
			for (char i = 0; i < arrs.length; i++) {
				if (arrs[i] == null) {
					continue;
				}
				for (char j = 0; j < arrs[i].length; j++) {
					arg = arrs[i][j];
					result[k] = arg;
					k++;
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * 计算数组的差集 注意： aArr 为null时 返回 bArr(可能为 null)。 bArr 为null时 返回 aArr。 无差集时返回
	 * new char[0];
	 * 
	 * @param aArr
	 * @param bArr
	 * @return
	 */
	public static char[] diff(char[] aArr, char[] bArr) {
		{
			if (aArr == null) {
				return bArr;
			}
			if (bArr == null) {
				return aArr;
			}
		}
		char[] cArr = new char[aArr.length + bArr.length];
		char idx = 0;
		/**
		 * 检查 a 中那些元 在 b 中不存在
		 */
		for (int i = 0; i < aArr.length; i++) {
			if (!inArray(bArr, aArr[i])) {
				cArr[idx++] = aArr[i];
			}
		}
		/**
		 * 检查 b 中那些元 在 a 中不存在
		 */
		for (int i = 0; i < bArr.length; i++) {
			if (!inArray(aArr, bArr[i])) {
				cArr[idx++] = bArr[i];
			}
		}
		//
		char[] dArr = new char[idx];
		System.arraycopy(cArr, 0, dArr, 0, dArr.length);
		return dArr;
	}

	/**
	 * 
	 * 返回一个数组的全复制 当 arr 为 null 时返回 new char[0] 。
	 * 
	 * @param arr
	 * @return
	 */
	public static char[] copyOf(char[] arr) {
		char[] carr = new char[0];
		if (arr != null) {
			carr = new char[arr.length];
			System.arraycopy(arr, 0, carr, 0, arr.length);
		}
		return carr;
	}

	/**
	 * 
	 * 返回一个数组的复制 当 arr 为 null 时返回 new char[0] 。
	 * 
	 * @param arr
	 * @param i
	 * @return
	 */
	public static char[] copyOf(char[] arr, int i) {
		char[] carr = new char[0];
		if (arr != null) {
			if (i > arr.length) {
				i = arr.length;
			}
			carr = new char[i];
			System.arraycopy(arr, 0, carr, 0, i);
		}
		return carr;
	}

	/**
	 * 
	 * 从数组中随机取出一个或多个单元 当 arr 为 null 时返回 new char[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static char[] random(char[] arr) {
		if (arr == null) {
			return new char[0];
		}
		int count = new Random().nextInt(arr.length);
		count = count > 0 ? count : 1;
		return random(arr, count);
	}

	/**
	 * 
	 * 将数组打乱 当 arr 为 null 时返回 new char[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static char[] shuffle(char[] arr) {
		if (arr == null) {
			return new char[0];
		}
		return random(arr, arr.length);
	}

	/**
	 * 
	 * 随机取出固定数量的元素 如取出数量大于数组元素数则返回打乱的数组，等同于 {@link #shuffle} 当 arr 为 null 时返回
	 * new char[0]。
	 * 
	 * @param arr
	 * @param count
	 * @return
	 */
	public static char[] random(char[] arr, int count) {
		char[] rarr = new char[0];
		if (arr != null) {
			if (arr.length < count) {
				count = arr.length;
			}
			Random random = new Random();
			int i = 0;
			int num = 0;
			rarr = new char[count];
			int[] keys = new int[count];
			for (int j = 0; j < keys.length; j++) {
				keys[j] = 0;
			}
			do {
				num = random.nextInt(arr.length);
				if (!inArray(keys, num)) {
					keys[i] = num;
					rarr[i] = arr[num];
					i++;
				}
			} while (i < count);
		}
		return rarr;
	}

	/**
	 * 
	 * 检查数组中是否存在某个值
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static boolean inArray(char[] arr, char search) {
		if (arr != null) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == search) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * 检查数组中是否存在某几个值
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static boolean inArray(char[] arr, char[] search) {
		if (search != null) {
			for (int i = 0; i < search.length; i++) {
				if (!inArray(arr, search[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 检查数组中是否存在某个值,并返回其索引键，不存在则返回 -1 。
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static int search(char[] arr, char search) {
		if (arr != null) {
			for (char i = 0; i < arr.length; i++) {
				if (arr[i] == search) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 
	 * 返回一个单元顺序相反的数组 当 arr 为 null 时返回 new char[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static char[] reverse(char[] arr) {
		char[] rarr = new char[0];
		if (arr != null) {
			rarr = new char[arr.length];
			char j = 0;
			for (int i = arr.length - 1; i >= 0; i--) {
				rarr[j++] = arr[i];
			}
		}
		return rarr;
	}

	/**
	 * byte array tools =====================
	 */
	/**
	 * 
	 * 计算数组中的单元数目或对象中的属性个数 当 arr 为 null 时返回 0。
	 * 
	 * @param arr
	 * @return
	 */
	public static int sizeOf(byte[] arr) {
		return arr == null ? 0 : arr.length;
	}

	/**
	 * 
	 * 合并一个或多个数组 当 arr 为 null 时返回 new byte[0] 。
	 * 
	 * @param arrs
	 * @return
	 */
	public static byte[] merge(byte[]... arrs) {
		byte[] result = new byte[0];
		if (arrs != null) {
			byte count = 0;
			for (byte i = 0; i < arrs.length; i++) {
				count += arrs[i].length;
			}
			result = new byte[count];
			byte arg;
			byte k = 0;
			for (byte i = 0; i < arrs.length; i++) {
				if (arrs[i] == null) {
					continue;
				}
				for (byte j = 0; j < arrs[i].length; j++) {
					arg = arrs[i][j];
					result[k] = arg;
					k++;
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * 计算数组的差集 注意： aArr 为null时 返回 bArr(可能为 null)。 bArr 为null时 返回 aArr。 无差集时返回
	 * new byte[0];
	 * 
	 * @param aArr
	 * @param bArr
	 * @return
	 */
	public static byte[] diff(byte[] aArr, byte[] bArr) {
		{
			if (aArr == null) {
				return bArr;
			}
			if (bArr == null) {
				return aArr;
			}
		}
		byte[] cArr = new byte[aArr.length + bArr.length];
		byte idx = 0;
		/**
		 * 检查 a 中那些元 在 b 中不存在
		 */
		for (int i = 0; i < aArr.length; i++) {
			if (!inArray(bArr, aArr[i])) {
				cArr[idx++] = aArr[i];
			}
		}
		/**
		 * 检查 b 中那些元 在 a 中不存在
		 */
		for (int i = 0; i < bArr.length; i++) {
			if (!inArray(aArr, bArr[i])) {
				cArr[idx++] = bArr[i];
			}
		}
		//
		byte[] dArr = new byte[idx];
		System.arraycopy(cArr, 0, dArr, 0, dArr.length);
		return dArr;
	}

	/**
	 * 
	 * 返回一个数组的全复制 当 arr 为 null 时返回 new byte[0] 。
	 * 
	 * @param arr
	 * @return
	 */
	public static byte[] copyOf(byte[] arr) {
		byte[] carr = new byte[0];
		if (arr != null) {
			carr = new byte[arr.length];
			System.arraycopy(arr, 0, carr, 0, arr.length);
		}
		return carr;
	}

	/**
	 * 
	 * 返回一个数组的复制 当 arr 为 null 时返回 new byte[0] 。
	 * 
	 * @param arr
	 * @param i
	 * @return
	 */
	public static byte[] copyOf(byte[] arr, int i) {
		byte[] carr = new byte[0];
		if (arr != null) {
			if (i > arr.length) {
				i = arr.length;
			}
			carr = new byte[i];
			System.arraycopy(arr, 0, carr, 0, i);
		}
		return carr;
	}

	/**
	 * 
	 * 从数组中随机取出一个或多个单元 当 arr 为 null 时返回 new byte[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static byte[] random(byte[] arr) {
		if (arr == null) {
			return new byte[0];
		}
		int count = new Random().nextInt(arr.length);
		count = count > 0 ? count : 1;
		return random(arr, count);
	}

	/**
	 * 
	 * 将数组打乱 当 arr 为 null 时返回 new byte[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static byte[] shuffle(byte[] arr) {
		if (arr == null) {
			return new byte[0];
		}
		return random(arr, arr.length);
	}

	/**
	 * 
	 * 随机取出固定数量的元素 如取出数量大于数组元素数则返回打乱的数组，等同于 {@link #shuffle} 当 arr 为 null 时返回
	 * new byte[0]。
	 * 
	 * @param arr
	 * @param count
	 * @return
	 */
	public static byte[] random(byte[] arr, int count) {
		byte[] rarr = new byte[0];
		if (arr != null) {
			if (arr.length < count) {
				count = arr.length;
			}
			Random random = new Random();
			int i = 0;
			int num = 0;
			rarr = new byte[count];
			int[] keys = new int[count];
			for (int j = 0; j < keys.length; j++) {
				keys[j] = 0;
			}
			do {
				num = random.nextInt(arr.length);
				if (!inArray(keys, num)) {
					keys[i] = num;
					rarr[i] = arr[num];
					i++;
				}
			} while (i < count);
		}
		return rarr;
	}

	/**
	 * 
	 * 检查数组中是否存在某个值
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static boolean inArray(byte[] arr, byte search) {
		if (arr != null) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == search) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * 检查数组中是否存在某几个值
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static boolean inArray(byte[] arr, byte[] search) {
		if (search != null) {
			for (int i = 0; i < search.length; i++) {
				if (!inArray(arr, search[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 检查数组中是否存在某个值,并返回其索引键，不存在则返回 -1 。
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static int search(byte[] arr, byte search) {
		if (arr != null) {
			for (byte i = 0; i < arr.length; i++) {
				if (arr[i] == search) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 返回一个单元顺序相反的数组<br />
	 * 当 arr 为 null 时返回 new byte[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static byte[] reverse(byte[] arr) {
		byte[] rarr = new byte[0];
		if (arr != null) {
			rarr = new byte[arr.length];
			byte j = 0;
			for (int i = arr.length - 1; i >= 0; i--) {
				rarr[j++] = arr[i];
			}
		}
		return rarr;
	}

	/**
	 * 使用 Byte List填充一个byte array。（转换Byte List为byte array）
	 * 
	 * @param list
	 * @return 当 list 为 null 时返回 new byte[0]。
	 */
	public static byte[] fill(List<Byte> list) {
		if (list == null) {
			return new byte[0];
		}
		int size = list.size();
		byte[] arr = new byte[size];

		for (int i = 0; i < size; i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}

	/**
	 * 使用 byte array填充一个 Byte List。（转换byte array为Byte List）
	 * 
	 * @param arr
	 * @return 当 arr 为 null 时返回 new ArrayList<Byte>(size)。
	 */
	public static List<Byte> fill(byte[] arr) {
		List<Byte> list = null;
		if (arr == null) {
			return new ArrayList<Byte>(0);
		}
		int size = arr.length;
		list = new ArrayList<Byte>(size);
		for (byte by : arr) {
			list.add(by);
		}
		return list;
	}

	/**
	 * Object array tools =====================
	 */
	/**
	 * 
	 * 计算数组中的单元数目或对象中的属性个数 当 arr 为 null 时返回 0。
	 * 
	 * @param arr
	 * @return
	 */
	public static int sizeOf(Object[] arr) {
		return arr == null ? 0 : arr.length;
	}

	/**
	 * 
	 * 合并一个或多个数组 当 arr 为 null 时返回 new Object[0]。
	 * 
	 * @param arrs
	 * @return
	 */
	public static Object[] merge(Object[]... arrs) {
		Object[] result = new Object[0];
		if (arrs != null) {
			int count = 0;
			for (int i = 0; i < arrs.length; i++) {
				count += arrs[i].length;
			}
			result = new Object[count];
			Object arg;
			int k = 0;
			for (int i = 0; i < arrs.length; i++) {
				if (arrs[i] == null) {
					continue;
				}
				for (int j = 0; j < arrs[i].length; j++) {
					arg = arrs[i][j];
					result[k] = arg;
					k++;
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * 计算数组的差集 注意： aArr 为null时 返回 bArr(可能为 null)。 bArr 为null时 返回 aArr。 无差集时返回
	 * new Object[0];
	 * 
	 * @param aArr
	 * @param bArr
	 * @return
	 */
	public static Object[] diff(Object[] aArr, Object[] bArr) {
		{
			if (aArr == null) {
				return bArr;
			}
			if (bArr == null) {
				return aArr;
			}
		}
		Object[] cArr = new Object[aArr.length + bArr.length];
		int idx = 0;
		/**
		 * 检查 a 中那些元 在 b 中不存在
		 */
		for (int i = 0; i < aArr.length; i++) {
			if (!inArray(bArr, aArr[i])) {
				cArr[idx++] = aArr[i];
			}
		}
		/**
		 * 检查 b 中那些元 在 a 中不存在
		 */
		for (int i = 0; i < bArr.length; i++) {
			if (!inArray(aArr, bArr[i])) {
				cArr[idx++] = bArr[i];
			}
		}
		//
		Object[] dArr = new Object[idx];
		System.arraycopy(cArr, 0, dArr, 0, dArr.length);
		return dArr;
	}

	/**
	 * 
	 * 返回一个数组的全复制 当 arr 为 null 时返回 new int[0] 。
	 * 
	 * @param arr
	 * @return
	 */
	public static Object[] copyOf(Object[] arr) {
		Object[] carr = new Object[0];
		if (arr != null) {
			carr = new Object[arr.length];
			System.arraycopy(arr, 0, carr, 0, arr.length);
		}
		return carr;
	}

	/**
	 * 
	 * 返回一个数组的复制 当 arr 为 null 时返回 new Object[0] 。
	 * 
	 * @param arr
	 * @param i
	 * @return
	 */
	public static Object[] copyOf(Object[] arr, int i) {
		Object[] carr = new Object[0];
		if (arr != null) {
			if (i > arr.length) {
				i = arr.length;
			}
			carr = new Object[i];
			System.arraycopy(arr, 0, carr, 0, i);
		}
		return carr;
	}

	/**
	 * 
	 * 从数组中随机取出一个或多个单元 当 arr 为 null 时返回 new Object[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static Object[] random(Object[] arr) {
		if (arr == null) {
			return new Object[0];
		}
		int count = new Random().nextInt(arr.length);
		count = count > 0 ? count : 1;
		return random(arr, count);
	}

	/**
	 * 
	 * 将数组打乱 当 arr 为 null 时返回 new Object[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static Object[] shuffle(Object[] arr) {
		if (arr == null) {
			return new Object[0];
		}
		return random(arr, arr.length);
	}

	/**
	 * 
	 * 随机取出固定数量的元素 如取出数量大于数组元素数则返回打乱的数组，等同于 {@link #shuffle} 当 arr 为 null 时返回
	 * new Object[0]。
	 * 
	 * @param arr
	 * @param count
	 * @return
	 */
	public static Object[] random(Object[] arr, int count) {
		Object[] rarr = new Object[0];
		if (arr != null) {
			if (arr.length < count) {
				count = arr.length;
			}
			Random random = new Random();
			int i = 0;
			int num = 0;
			rarr = new Object[count];
			int[] keys = new int[count];
			for (int j = 0; j < keys.length; j++) {
				keys[j] = -1;
			}
			do {
				num = random.nextInt(arr.length);
				if (!inArray(keys, num)) {
					keys[i] = num;
					rarr[i] = arr[num];
					i++;
				}
			} while (i < count);
		}
		return rarr;
	}

	/**
	 * 
	 * 检查数组中是否存在某个值
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static boolean inArray(Object[] arr, Object search) {
		if (arr != null && search != null) {
			for (int i = 0; i < arr.length; i++) {
				if (search.equals(arr[i])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * 检查数组中是否存在某几个值
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static boolean inArray(Object[] arr, Object[] search) {
		if (search != null) {
			for (int i = 0; i < search.length; i++) {
				if (!inArray(arr, search[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 检查数组中是否存在某个值,并返回其索引键，不存在则返回 -1 。
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static int search(Object[] arr, Object search) {
		if (arr != null && search != null) {
			for (int i = 0; i < arr.length; i++) {
				if (search.equals(arr[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 
	 * 返回一个单元顺序相反的数组 当 arr 为 null 时返回 new Object[0]。
	 * 
	 * @param arr
	 * @return
	 */
	public static Object[] reverse(Object[] arr) {
		Object[] rarr = new Object[0];
		if (arr != null) {
			rarr = new Object[arr.length];
			int j = 0;
			for (int i = arr.length - 1; i >= 0; i--) {
				rarr[j++] = arr[i];
			}
		}
		return rarr;
	}

	/**
	 * String array tools =====================
	 */
	/**
	 * 
	 * 计算数组中的单元数目或对象中的属性个数 当 arr 为 null 时返回 0。
	 * 
	 * @param arr
	 * @return
	 */
	public static int sizeOf(String[] arr) {
		return arr == null ? 0 : arr.length;
	}

	/**
	 * 
	 * 合并一个或多个数组 当 arr 为 null 时返回 new String[0]。
	 * 
	 * @param arrs
	 * @return
	 */
	public static String[] merge(String[]... arrs) {
		String[] result = new String[0];
		if (arrs != null) {
			int count = 0;
			for (int i = 0; i < arrs.length; i++) {
				count += arrs[i].length;
			}
			result = new String[count];
			String arg;
			int k = 0;
			for (int i = 0; i < arrs.length; i++) {
				if (arrs[i] == null) {
					continue;
				}
				for (int j = 0; j < arrs[i].length; j++) {
					arg = arrs[i][j];
					result[k] = arg;
					k++;
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * 计算数组的差集 注意： aArr 为null时 返回 bArr(可能为 null)。 bArr 为null时 返回 aArr。 无差集时返回
	 * new String[0];
	 * 
	 * @param aArr
	 * @param bArr
	 * @return
	 */
	public static String[] diff(String[] aArr, String[] bArr) {
		{
			if (aArr == null) {
				return bArr;
			}
			if (bArr == null) {
				return aArr;
			}
		}
		String[] cArr = new String[aArr.length + bArr.length];
		int idx = 0;
		/**
		 * 检查 a 中那些元 在 b 中不存在
		 */
		for (int i = 0; i < aArr.length; i++) {
			if (!inArray(bArr, aArr[i])) {
				cArr[idx++] = aArr[i];
			}
		}
		/**
		 * 检查 b 中那些元 在 a 中不存在
		 */
		for (int i = 0; i < bArr.length; i++) {
			if (!inArray(aArr, bArr[i])) {
				cArr[idx++] = bArr[i];
			}
		}
		//
		String[] dArr = new String[idx];
		System.arraycopy(cArr, 0, dArr, 0, dArr.length);
		return dArr;
	}

	/**
	 * 
	 * 返回一个数组的全复制 当 arr 为 null 时返回 new String[0] 。
	 * 
	 * @param arr
	 * @return
	 */
	public static String[] copyOf(String[] arr) {
		String[] carr = new String[0];
		if (arr != null) {
			carr = new String[arr.length];
			System.arraycopy(arr, 0, carr, 0, arr.length);
		}
		return carr;
	}

	/**
	 * 
	 * 返回一个数组的复制 当 arr 为 null 时返回 new String[0] 。
	 * 
	 * @param arr
	 * @param i
	 * @return
	 */
	public static String[] copyOf(String[] arr, int i) {
		String[] carr = new String[0];
		if (arr != null) {
			if (i > arr.length) {
				i = arr.length;
			}
			carr = new String[i];
			System.arraycopy(arr, 0, carr, 0, i);
		}
		return carr;
	}

	/**
	 * 
	 * 从数组中随机取出一个或多个单元 当 arr 为 null 时返回 new String[0] 。
	 * 
	 * @param arr
	 * @return
	 */
	public static String[] random(String[] arr) {
		if (arr == null) {
			return new String[0];
		}
		int count = new Random().nextInt(arr.length);
		count = count > 0 ? count : 1;
		return random(arr, count);
	}

	/**
	 * 
	 * 将数组打乱 当 arr 为 null 时返回 new String[0] 。
	 * 
	 * @param arr
	 * @return
	 */
	public static String[] shuffle(String[] arr) {
		if (arr == null) {
			return new String[0];
		}
		return random(arr, arr.length);
	}

	/**
	 * 
	 * 随机取出固定数量的元素 如取出数量大于数组元素数则返回打乱的数组，等同于 {@link #shuffle} 当 arr 为 null 时返回
	 * new String[0] 。
	 * 
	 * @param arr
	 * @param count
	 * @return
	 */
	public static String[] random(String[] arr, int count) {
		String[] rarr = new String[0];
		if (arr != null) {
			if (arr.length < count) {
				count = arr.length;
			}
			Random random = new Random();
			int i = 0;
			int num = 0;
			rarr = new String[count];
			int[] keys = new int[count];
			for (int j = 0; j < keys.length; j++) {
				keys[j] = -1;
			}
			do {
				num = random.nextInt(arr.length);
				if (!inArray(keys, num)) {
					keys[i] = num;
					rarr[i] = arr[num];
					i++;
				}
			} while (i < count);
		}
		return rarr;
	}

	/**
	 * 
	 * 检查数组中是否存在某个值
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static boolean inArray(String[] arr, String search) {
		if (arr != null && search != null) {
			for (int i = 0; i < arr.length; i++) {
				if (search.equals(arr[i])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * 检查数组中是否存在某几个值
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static boolean inArray(String[] arr, String[] search) {
		if (search != null) {
			for (int i = 0; i < search.length; i++) {
				if (!inArray(arr, search[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 检查数组中是否存在某个值,并返回其索引键，不存在则返回 -1 。
	 * 
	 * @param arr
	 * @param search
	 * @return
	 */
	public static int search(String[] arr, String search) {
		if (arr != null && search != null) {
			for (int i = 0; i < arr.length; i++) {
				if (search.equals(arr[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 
	 * 返回一个单元顺序相反的数组 当 arr 为 null 时返回 new String[0] 。
	 * 
	 * @param arr
	 * @return
	 */
	public static String[] reverse(String[] arr) {
		String[] rarr = new String[0];
		if (arr != null) {
			rarr = new String[arr.length];
			int j = 0;
			for (int i = arr.length - 1; i >= 0; i--) {
				rarr[j++] = arr[i];
			}
		}
		return rarr;
	}

	/**
	 * 使用间隔符连接
	 * 
	 * @param strs
	 * @param sep
	 * @return
	 */
	public static String join(String[] strs, String sep) {
		StringBuilder strBuilder = new StringBuilder();
		if (strs != null) {
			for (int i = 0; i < strs.length; i++) {
				strBuilder.append(strs[i]);
				if (i < strs.length - 1) {
					strBuilder.append(sep);
				}
			}
		}
		return strBuilder.toString();
	}

	public static boolean isEmpty(char[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(byte[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(Object[] array) {
		return (array == null) || (array.length == 0);
	}
}
