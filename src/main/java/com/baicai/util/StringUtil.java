package com.baicai.util;

import java.util.ArrayList;

public class StringUtil {

	/**
	 * 剔除字符串两侧空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return ltrim(rtrim(str));
	}

	/**
	 * 剔除字符串两侧字符
	 * 
	 * @param str
	 * @param trimChars
	 *            需要剔除的字符
	 * @return
	 */
	public static String trim(String str, char[] trimChars) {
		return ltrim(rtrim(str, trimChars), trimChars);
	}

	public static String[] split(String s, char c, int limit) {
		if (s == null)
			return null;
		ArrayList<Integer> pos = new ArrayList<Integer>();
		int i = -1;
		while ((i = s.indexOf((int) c, i + 1)) > 0) {
			pos.add(Integer.valueOf(i));
		}
		int n = pos.size();
		int[] p = new int[n];
		i = -1;
		for (int x : pos) {
			p[++i] = x;
		}
		if ((limit == 0) || (limit > n)) {
			limit = n + 1;
		}
		String[] result = new String[limit];
		if (n > 0) {
			result[0] = s.substring(0, p[0]);
		} else {
			result[0] = s;
		}
		for (i = 1; i < limit - 1; ++i) {
			result[i] = s.substring(p[i - 1] + 1, p[i]);
		}
		if (limit > 1) {
			result[limit - 1] = s.substring(p[limit - 2] + 1);
		}
		return result;
	}

	/**
	 * 剔除字符串右侧空格
	 * 
	 * @param str
	 * @return
	 */
	public static String rtrim(String str) {
		if (str != null) {
			char[] chars = str.toCharArray();
			StringBuilder strBuilder = new StringBuilder();
			char ch;
			boolean isTrimed = false;
			for (int i = chars.length - 1; i >= 0; i--) {
				ch = chars[i];
				if (!isTrimed) {
					if (Character.isWhitespace(ch)) {
						continue;
					} else {
						isTrimed = true;
					}
				}
				strBuilder.append(ch);
			}
			strBuilder.reverse();
			str = strBuilder.toString();
		}
		return str;
	}

	/**
	 * 剔除字符串右侧字符
	 * 
	 * @param str
	 * @param trimChars
	 *            需要剔除的字符
	 * @return
	 */
	public static String rtrim(String str, char[] trimChars) {
		if (str != null && trimChars != null && trimChars.length > 0) {
			char[] chars = str.toCharArray();
			StringBuilder strBuilder = new StringBuilder();
			char ch;
			boolean isTrimed = false;
			for (int i = chars.length - 1; i >= 0; i--) {
				ch = chars[i];
				if (!isTrimed) {
					if (ArrayHelper.inArray(trimChars, ch)) {
						continue;
					} else {
						isTrimed = true;
					}
				}
				strBuilder.append(ch);
			}
			strBuilder.reverse();
			str = strBuilder.toString();
		}
		return str;
	}

	/**
	 * 剔除字符串左侧空格
	 * 
	 * @param str
	 * @return
	 */
	public static String ltrim(String str) {
		if (str != null) {
			char[] chars = str.toCharArray();
			StringBuilder strBuilder = new StringBuilder();
			char ch;
			boolean isTrimed = false;
			for (int i = 0; i < chars.length; i++) {
				ch = chars[i];
				if (!isTrimed) {
					if (Character.isWhitespace(ch)) {
						continue;
					} else {
						isTrimed = true;
					}
				}
				strBuilder.append(ch);
			}
			str = strBuilder.toString();
		}
		return str;
	}

	/**
	 * 剔除字符串左侧字符
	 * 
	 * @param str
	 * @param trimChars
	 *            需要剔除的字符
	 * @return
	 */
	public static String ltrim(String str, char[] trimChars) {
		if (str != null && trimChars != null && trimChars.length > 0) {
			char[] chars = str.toCharArray();
			StringBuilder strBuilder = new StringBuilder();
			char ch;
			boolean isTrimed = false;
			for (int i = 0; i < chars.length; i++) {
				ch = chars[i];
				if (!isTrimed) {
					if (ArrayHelper.inArray(trimChars, ch)) {
						continue;
					} else {
						isTrimed = true;
					}
				}
				strBuilder.append(ch);
			}
			str = strBuilder.toString();
		}
		return str;
	}

	public static String subString(String str, int start, int end) {
		int strLen = str.length();
		start = start > strLen ? strLen : start;
		start = start > 0 ? start : 0;
		if (end == -1) {
			end = strLen;
		} else {
			end = end > strLen ? strLen : end;
		}
		return str.substring(start, end);
	}

	public static int indexOfChars(String string, String chars) {
		return indexOfChars(string, chars, 0);
	}

	/**
	 * Returns the very first index of any char from provided string, starting
	 * from specified index offset. Returns index of founded char, or
	 * <code>-1</code> if nothing found.
	 */
	public static int indexOfChars(String string, String chars, int startindex) {
		int stringLen = string.length();
		int charsLen = chars.length();
		if (startindex < 0) {
			startindex = 0;
		}
		for (int i = startindex; i < stringLen; i++) {
			char c = string.charAt(i);
			for (int j = 0; j < charsLen; j++) {
				if (c == chars.charAt(j)) {
					return i;
				}
			}
		}
		return -1;
	}

	public static int indexOfChars(String string, char[] chars) {
		return indexOfChars(string, chars, 0);
	}

	/**
	 * Returns the very first index of any char from provided string, starting
	 * from specified index offset. Returns index of founded char, or
	 * <code>-1</code> if nothing found.
	 */
	public static int indexOfChars(String string, char[] chars, int startindex) {
		int stringLen = string.length();
		int charsLen = chars.length;
		for (int i = startindex; i < stringLen; i++) {
			char c = string.charAt(i);
			for (int j = 0; j < charsLen; j++) {
				if (c == chars[j]) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Joins an array of strings into one string.
	 */
	public static String join(String... parts) {
		StringBuilder sb = new StringBuilder();
		for (String part : parts) {
			sb.append(part);
		}
		return sb.toString();
	}

	/**
	 * Joins list of iterable elements. Separator string may be
	 * <code>null</code>.
	 */
	public static String join(Iterable<?> elements, String separator) {
		if (elements == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object o : elements) {
			if (sb.length() > 0) {
				if (separator != null) {
					sb.append(separator);
				}
			}
			sb.append(o);
		}
		return sb.toString();
	}

	/**
	 * Splits a string in several parts (tokens) that are separated by
	 * delimiter. Delimiter is <b>always</b> surrounded by two strings! If there
	 * is no content between two delimiters, empty string will be returned for
	 * that token. Therefore, the length of the returned array will always be:
	 * #delimiters + 1.
	 * <p>
	 * Method is much, much faster then regexp <code>String.split()</code>, and
	 * a bit faster then <code>StringTokenizer</code>.
	 *
	 * @param src
	 *            string to split
	 * @param delimiter
	 *            split delimiter
	 *
	 * @return array of split strings
	 */
	public static String[] split(String src, String delimiter) {
		int maxparts = (src.length() / delimiter.length()) + 2; // one more for
																// the last
		int[] positions = new int[maxparts];
		int dellen = delimiter.length();

		int i, j = 0;
		int count = 0;
		positions[0] = -dellen;
		while ((i = src.indexOf(delimiter, j)) != -1) {
			count++;
			positions[count] = i;
			j = i + dellen;
		}
		count++;
		positions[count] = src.length();

		String[] result = new String[count];

		for (i = 0; i < count; i++) {
			result[i] = src.substring(positions[i] + dellen, positions[i + 1]);
		}
		return result;
	}

	/**
	 * Splits a string in several parts (tokens) that are separated by delimiter
	 * characters. Delimiter may contains any number of character and it is
	 * always surrounded by two strings.
	 *
	 * @param src
	 *            source to examine
	 * @param d
	 *            string with delimiter characters
	 *
	 * @return array of tokens
	 */
	public static String[] splitc(String src, String d) {
		if ((d.length() == 0) || (src.length() == 0)) {
			return new String[] { src };
		}
		char[] delimiters = d.toCharArray();
		char[] srcc = src.toCharArray();

		int maxparts = srcc.length + 1;
		int[] start = new int[maxparts];
		int[] end = new int[maxparts];

		int count = 0;

		start[0] = 0;
		int s = 0, e;
		if (equalsOne(srcc[0], delimiters) == true) { // string starts
														// with
														// delimiter
			end[0] = 0;
			count++;
			s = findFirstDiff(srcc, 1, delimiters);
			if (s == -1) { // nothing after delimiters
				return new String[] { "", "" };
			}
			start[1] = s; // new start
		}
		while (true) {
			// find new end
			e = findFirstEqual(srcc, s, delimiters);
			if (e == -1) {
				end[count] = srcc.length;
				break;
			}
			end[count] = e;

			// find new start
			count++;
			s = findFirstDiff(srcc, e, delimiters);
			if (s == -1) {
				start[count] = end[count] = srcc.length;
				break;
			}
			start[count] = s;
		}
		count++;
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = src.substring(start[i], end[i]);
		}
		return result;
	}

	/**
	 * Splits a string in several parts (tokens) that are separated by single
	 * delimiter characters. Delimiter is always surrounded by two strings.
	 *
	 * @param src
	 *            source to examine
	 * @param delimiter
	 *            delimiter character
	 *
	 * @return array of tokens
	 */
	public static String[] splitc(String src, char delimiter) {
		if (src.length() == 0) {
			return new String[] { "" };
		}
		char[] srcc = src.toCharArray();

		int maxparts = srcc.length + 1;
		int[] start = new int[maxparts];
		int[] end = new int[maxparts];

		int count = 0;

		start[0] = 0;
		int s = 0, e;
		if (srcc[0] == delimiter) { // string starts with delimiter
			end[0] = 0;
			count++;
			s = findFirstDiff(srcc, 1, delimiter);
			if (s == -1) { // nothing after delimiters
				return new String[] { "", "" };
			}
			start[1] = s; // new start
		}
		while (true) {
			// find new end
			e = findFirstEqual(srcc, s, delimiter);
			if (e == -1) {
				end[count] = srcc.length;
				break;
			}
			end[count] = e;

			// find new start
			count++;
			s = findFirstDiff(srcc, e, delimiter);
			if (s == -1) {
				start[count] = end[count] = srcc.length;
				break;
			}
			start[count] = s;
		}
		count++;
		String[] result = new String[count];
		for (int i = 0; i < count; i++) {
			result[i] = src.substring(start[i], end[i]);
		}
		return result;
	}

	private static boolean equalsOne(char c, char[] match) {
		for (char aMatch : match) {
			if (c == aMatch) {
				return true;
			}
		}
		return false;
	}

	private static int findFirstDiff(char[] source, int index, char match) {
		for (int i = index; i < source.length; i++) {
			if (source[i] != match) {
				return i;
			}
		}
		return -1;
	}

	private static int findFirstDiff(char[] source, int index, char[] match) {
		for (int i = index; i < source.length; i++) {
			if (equalsOne(source[i], match) == false) {
				return i;
			}
		}
		return -1;
	}

	private static int findFirstEqual(char[] source, int index, char[] match) {
		for (int i = index; i < source.length; i++) {
			if (equalsOne(source[i], match) == true) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Finds index of the first character in given array the matches any from
	 * the given set of characters.
	 *
	 * @return index of matched character or -1
	 */
	private static int findFirstEqual(char[] source, int index, char match) {
		for (int i = index; i < source.length; i++) {
			if (source[i] == match) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns <code>true</code> if specified character is lowercase ASCII.
	 * If user uses only ASCIIs, it is much much faster.
	 */
	public static boolean isLowercaseLetter(char c) {
		return (c >= 'a') && (c <= 'z');
	}

	/**
	 * Returns <code>true</code> if specified character is uppercase ASCII.
	 * If user uses only ASCIIs, it is much much faster.
	 */
	public static boolean isUppercaseLetter(char c) {
		return (c >= 'A') && (c <= 'Z');
	}

	public static boolean isLetter(char c) {
		return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'));
	}

	public static boolean isDigit(char c) {
		return (c >= '0') && (c <= '9');
	}
	
	public static char toUpperAscii(char c) {
		if (isLowercaseLetter(c)) {
			c -= (char) 0x20;
		}
		return c;
	}

	public static char toLowerAscii(char c) {
		if (isUppercaseLetter(c)) {
			c += (char) 0x20;
		}
		return c;
	}
	
	public static boolean isBlank(String str){
		if(str==null||str.equals("")){
			return true;
		}
		if(str.length()==0){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否包含字母
	 * @param str
	 * @return
	 */
	public static boolean containLetter(String str){
		char[] strch=str.toCharArray();
		boolean flag=false;
		for (int i = 0; i < strch.length; i++) {
			if(isLetter(strch[i])){
				return true;
			}
		}
		return flag;
	}
	
	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public static String capitalize(String str){
		char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
	}

}
