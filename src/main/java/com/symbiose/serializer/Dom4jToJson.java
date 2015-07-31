package com.symbiose.serializer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Class to serialize/transform dom4j element on JSON
 * 
 * @author pcollaog
 */
public final class Dom4jToJson {

	private static final String CLASS = "class";

	private static final Set<String> LIST_CLASS_ATTRIBUTE;

	static {
		LIST_CLASS_ATTRIBUTE = new HashSet<String>();
		LIST_CLASS_ATTRIBUTE.add("list");
		LIST_CLASS_ATTRIBUTE.add("levels");
	}

	/**
	 * 
	 */
	private Dom4jToJson() {
	}

	/**
	 * Transform dom4j element on JSON (like json-lib style)
	 * 
	 * @param parentElement
	 *            dom4j element
	 * @return serialized text
	 */
	public static String writeToString(Element parentElement) {
		JSONArray jsonResult = new JSONArray();
		JSONObject jsonChild = recursive(parentElement);
		jsonResult.add(jsonChild);
		return jsonResult.toString();
	}

	private static JSONObject recursive(Element parentElement) {
		List<Element> childElements = parentElement.elements();

		JSONObject jsonObject = new JSONObject();
		for (Element childElement : childElements) {

			String attributeValue = childElement.attributeValue(CLASS);
			if (LIST_CLASS_ATTRIBUTE.contains(attributeValue)) {
				List<Element> elements = childElement.elements();

				JSONArray jsonArray = new JSONArray();
				for (Element element : elements) {
					jsonArray.add(recursive(element));
				}
				jsonObject.element(childElement.getName(), jsonArray);
			} else {
				if (!childElement.elements().isEmpty()) {
					JSONObject recursive = recursive(childElement);
					jsonObject.accumulate(childElement.getName(), recursive);
				} else {
					jsonObject.accumulate(childElement.getName(),
							childElement.getTextTrim());
				}
			}
		}
		return jsonObject;
	}
}
