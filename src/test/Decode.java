package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Decode {
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "xxx1");
		map.put("2", "xxx2");
		map.put("3", "xxx3");
		map.put("4", "xxx4");
		Set<String> keySet = map.keySet();
		for (String string : keySet) {
			System.out.println(string + ":" + map.get(string));
		}
		System.out.println(map);
	}
}
