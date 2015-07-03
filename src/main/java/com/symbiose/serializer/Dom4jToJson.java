package com.symbiose.serializer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author pcollaog
 *
 */
public final class Dom4jToJson {

	private static final String LIST = "list";

	private static final String LEVELS = "levels";

	private static final String CLASS = "class";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Dom4jToJson.class);

	/**
	 * 
	 */
	private Dom4jToJson() {
	}

	/**
	 * @param parentElement
	 * @return
	 */
	public static String writeToString(Element parentElement) {
		JSONObject jsonObject = new JSONObject();
		recursive(parentElement, jsonObject);

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(jsonObject);
		sb.append("]");

		return sb.toString();
	}

	private static void recursive(Element parentElement,
			JSONObject parentJsonObject) {
		if (!parentElement.elements().isEmpty()) {

			for (String elementName : uniqueElementNames(
					parentElement.elements())) {
				List<Element> childs = parentElement.elements(elementName);

				String attributeList = parentElement.attributeValue(CLASS);
				if (LEVELS.equals(attributeList)
						|| LIST.equals(attributeList)) {
					JSONArray jsonArray = new JSONArray();
					for (Element child : childs) {
						JSONObject jsonChild = new JSONObject();
						recursive(child, jsonChild);
						jsonArray.element(jsonChild);
					}
					parentJsonObject.element(parentElement.getName(),
							jsonArray);
				} else {
					recursive(childs.get(0), parentJsonObject);
				}
			}
		} else {
			parentJsonObject.accumulate(parentElement.getName(),
					parentElement.getTextTrim());
		}
	}

	/**
	 * @param elements
	 * @return
	 */
	private static Set<String> uniqueElementNames(List<Element> elements) {
		Set<String> elementNames = new HashSet<String>();
		for (Element element : elements) {
			elementNames.add(element.getName());
		}
		return elementNames;
	}

}
