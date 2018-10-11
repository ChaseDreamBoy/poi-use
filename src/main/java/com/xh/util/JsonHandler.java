package com.xh.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler {

	private final static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getInstance() {
        return objectMapper;
    }
	
	/**
	 *   
	 * @Title: objToJson 
	 *
	 * @author: xiaohe
	 *
	 * @Description: object to json.
	 *
	 * @param obj
	 * 
	 * @return: String
	 * 
	 * @throws JsonProcessingException 
	 *
	 */
    public static String objToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
    
    /**
     *   
     * @Title: objToJsonIgnoreNull 
     *
     * @author: xiaohe
     *
     * @Description: object to json, ignore null values.
     *
     * @param obj
     * 
     * @throws Exception  
     *
     * @return: String
     *
     */
    public static String objToJsonIgnoreNull(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(obj);
    }

    /**
     *   
     * @Title: jsonToObj 
     *
     * @author: xiaohe
     *
     * @Description: json to object.
     *
     * @param jsonString
     * 
     * @param clazz
     * 
     * @throws Exception  
     *
     * @return: T
     *
     */
    public static <T> T jsonToObj(String jsonString, Class<T> clazz) throws Exception {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper.readValue(jsonString, clazz);
    }

    /**
     *   
     * @Title: jsonToMap 
     *
     * @author: xiaohe
     *
     * @Description: json to map, ignore null values.
     *
     * @param jsonString
     * 
     * @throws Exception  
     *
     * @return: Map<String,Object>
     *
     */
    @SuppressWarnings("unchecked")
	public static <T> Map<String, Object> jsonToMap(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(jsonString, Map.class);
    }

    /**
     *   
     * @Title: jsonToMap 
     *
     * @author: xiaohe
     *
     * @Description: json to map.
     *
     * @param jsonString
     * @param clazz
     * 
     * @throws Exception  
     *
     * @return: Map<String,T>
     *
     */
    public static <T> Map<String, T> jsonToMap(String jsonString, Class<T> clazz) throws Exception {
        Map<String, Map<String, Object>> map = objectMapper.readValue(jsonString, new TypeReference<Map<String, T>>() {
        });
        Map<String, T> result = new HashMap<String, T>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), mapToPojo(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     *   
     * @Title: jsonToMapDeeply 
     *
     * @author: xiaohe
     *
     * @Description: Deeply json to map.
     *
     * @param json
     * 
     * @throws Exception  
     *
     * @return: Map<String,Object>
     *
     */
    public static Map<String, Object> jsonToMapDeeply(String json) throws Exception {
        return jsonToMapRecursion(json, objectMapper);
    }

    /**
     *   
     * @Title: jsonToListRecursion 
     *
     * @author: xiaohe
     *
     * @Description: Parse JSON into list, 
     *      if the elements inside list exist jsonString, 
     *      continue to parse.
     *
     * @param json
     * @param mapper
     * 
     * @throws Exception  
     *
     * @return: List<Object>
     *
     */
    private static List<Object> jsonToListRecursion(String json, ObjectMapper mapper) throws Exception {
        if (json == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
		List<Object> list = mapper.readValue(json, List.class);
        for (Object obj : list) {
            if (obj != null && obj instanceof String) {
                String str = (String) obj;
                if (str.startsWith("[")) {
                    obj = jsonToListRecursion(str, mapper);
                } else if (obj.toString().startsWith("{")) {
                    obj = jsonToMapRecursion(str, mapper);
                }
            }
        }
        return list;
    }

    /**
     * 
     * @Title: jsonToMapRecursion 
     *
     * @author: xiaohe
     *
     * @Description: Parse JSON into map, 
     *     and continue to parse if value has jsonString inside the map.
     *
     * @param json
     * @param mapper
     * 
     * @throws Exception  
     *
     * @return: Map<String,Object>
     *
     */
    private static Map<String, Object> jsonToMapRecursion(String json, ObjectMapper mapper) throws Exception {
        if (json == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
		Map<String, Object> map = mapper.readValue(json, Map.class);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object obj = entry.getValue();
            if (obj != null && obj instanceof String) {
                String str = ((String) obj);
                if (str.startsWith("[")) {
                    List<?> list = jsonToListRecursion(str, mapper);
                    map.put(entry.getKey(), list);
                } else if (str.startsWith("{")) {
                    Map<String, Object> mapRecursion = jsonToMapRecursion(str, mapper);
                    map.put(entry.getKey(), mapRecursion);
                }
            }
        }
        return map;
    }

    /**
     *   
     * @Title: jsonToList 
     *
     * @author: xiaohe
     *
     * @Description: json to list.
     *
     * @param jsonArrayStr
     * @param clazz
     * @return
     * @throws Exception  
     *
     * @return: List<T>
     *
     */
    public static <T> List<T> jsonToList(String jsonArrayStr, Class<T> clazz) throws Exception {
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        @SuppressWarnings("unchecked")
		List<T> list = (List<T>) objectMapper.readValue(jsonArrayStr, javaType);
        return list;
    }


    /**
     *   
     * @Title: getCollectionType 
     *
     * @author: xiaohe
     *
     * @Description: Obtain generics collection Type.
     *
     * @param collectionClass
     * @param elementClasses
     *
     * @return: JavaType
     *
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    /**
     *   
     * @Title: mapToPojo 
     *
     * @author: xiaohe
     *
     * @Description: TODO
     *
     * @param map
     * @param clazz
     * 
     *
     * @return: T
     *
     */
    @SuppressWarnings("rawtypes")
	public static <T> T mapToPojo(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     *   
     * @Title: mapToJson 
     *
     * @author: xiaohe
     *
     * @Description: 
     *
     * @param map
     * 
     * @throws JsonProcessingException  
     *
     * @return: String
     *
     */
    public static String mapToJson(Map<?, ?> map) throws JsonProcessingException {
    	return objectMapper.writeValueAsString(map);
    }

}
