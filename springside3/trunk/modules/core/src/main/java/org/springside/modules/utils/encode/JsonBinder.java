package org.springside.modules.utils.encode;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jackson的简单封装.
 * 
 * @author calvin
 */
public class JsonBinder {

	private static Logger logger = LoggerFactory.getLogger(JsonBinder.class);

	private ObjectMapper mapper;

	public JsonBinder(Inclusion inclusion) {
		mapper = new ObjectMapper();
		mapper.getSerializationConfig().setSerializationInclusion(inclusion);
		mapper.getDeserializationConfig().set(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static JsonBinder buildNonDefaultBinder() {
		return new JsonBinder(Inclusion.NON_DEFAULT);
	}

	public static JsonBinder buildNonNullBinder() {
		return new JsonBinder(Inclusion.NON_NULL);
	}

	/**
	 * 如果JSON字符串为空,返回Null.
	 * 
	 * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:
	 * List<MyBean> stringList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 如果对象为空,返回Null.
	 */
	public String toJson(Object object) {
		if (object == null) {
			return null;
		}

		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	public ObjectMapper getMapper() {
		return mapper;
	}
}