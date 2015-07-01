package com.symbiose.serializer.json;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author pcollaog
 *
 */
public class JSONObjectWrapperImpl implements JSONObjectWrapper {

	private String _key;

	private Object _value;

	private JSONObject _jsonObject;

	/**
	 * @param key
	 *            nombre del atributo
	 * @param value
	 *            valor asignado
	 */
	public JSONObjectWrapperImpl(String key, Object value) {
		_key = key;
		_value = value;
	}

	/**
	 * 
	 */
	public JSONObjectWrapperImpl() {
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public static JSONObjectWrapper createInstance(String key, Object value) {
		return new JSONObjectWrapperImpl(key, value);
	}

	@Override
	public void accumulate(JSONObjectWrapper recursive) {
		if (null == _jsonObject) {
			_jsonObject = new JSONObject();
		}
		Object value = recursive.getValue();

		if (value instanceof List) {
			List<JSONObjectWrapper> values = (List<JSONObjectWrapper>) value;
			JSONArray jsonArray = new JSONArray();

			for (JSONObjectWrapper object : values) {
				jsonArray.element(object.getJsonObject());
			}

			_jsonObject.accumulate(recursive.getKey(), jsonArray);
		} else {
			_jsonObject.accumulate(recursive.getKey(), value);
		}
	}

	@Override
	public Object getValue() {
		return _value;
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public String wrapperToString() {
		return _jsonObject.toString();
	}

	/**
	 * @return the jsonObject
	 */
	@Override
	public final JSONObject getJsonObject() {
		return _jsonObject;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
