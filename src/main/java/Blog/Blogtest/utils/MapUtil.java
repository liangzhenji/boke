package Blog.Blogtest.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Blog.Blogtest.bean.Class;
public class MapUtil {
	   public static void add(HashMap<String,Integer> map,String key,Integer value) {
		   if(map.get(key)!=null) {
			   map.put(key,map.get(key)+value);
		   }else {
			   map.put(key,value);
		   }
     }
	   
	   public static HashMap<String, Integer> translate(HashMap<String,Integer> map,List<Class> cla) {
		   HashMap<String, Integer> newmap = new HashMap<String,Integer>();
		   HashMap<Long, String> tmpmap = new HashMap<Long,String>();
		   for(Class cl:cla) {
			   tmpmap.put(cl.getClassid(), cl.getClassname());
		   }
		   for(Map.Entry<String, Integer> entry : map.entrySet()) {
			   newmap.put(tmpmap.get(Long.valueOf(entry.getKey())),entry.getValue());
		   }
		   return newmap;
	   }
	   public static LinkedHashMap<String, Integer> sortHashMap(Map<String, Integer> map) {

	        // 首先拿到map的键值对集合
	        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();

	        // 将set集合转为List集合，为什么？为了使用工具类的排序方法
	        List<Map.Entry<String, Integer>> list = new ArrayList<>(entrySet);
	        // 使用collections集合工具类对list进行排序，排序规则使用匿名内部类来实现
	        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

	            @Override
	            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
	                // 按要求根据User的age的倒序进行排
	                return o2.getValue() - o1.getValue();
	            }
	        });
	        // 创建一个新的有序的HashMap子类的集合
	        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
	        // 将list中的数据存储在LinkedHashMap中
	        int i = 1;
	        for (Map.Entry<String, Integer> entry : list) {
	            if (i > 100) {
	                break;
	            }
	            linkedHashMap.put(entry.getKey(), entry.getValue());
	            i++;
	        }
	        // 返回结果
	        return linkedHashMap;
	    }
}