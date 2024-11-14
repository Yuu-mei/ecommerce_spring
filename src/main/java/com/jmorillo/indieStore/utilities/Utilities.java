package com.jmorillo.indieStore.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

public class Utilities {

	public static List<Map<String, Object>> processNativeQuery(Query query){
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		return nativeQuery.getResultList();
	}
	
	public static Map<String, Object> processList(String element_to_remove, List<Map<String, Object>> list_to_process){
		String element_name = (String) list_to_process.get(0).get(element_to_remove);
		
		for (Map<String, Object> map : list_to_process) {
			map.remove(element_to_remove);
		}
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put(element_to_remove, element_name);
		res.put("videogames", list_to_process);
		
		return res;
	}
}
