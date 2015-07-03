package com.symbiose.serializer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.symbiose.serializer.json.JSONObjectWrapper;
import com.symbiose.serializer.json.JSONObjectWrapperImpl;

/**
 * @author pcollaog
 *
 */
public class Dom4jToJson {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(Dom4jToJson.class);

	/**
	 * @param parentElement
	 * @return
	 */
	public String writeToString(Element parentElement) {
		JSONObjectWrapper jsonWrapper = recursive(parentElement);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(jsonWrapper.wrapperToString());
		sb.append("]");
		return sb.toString();
	}

	private JSONObjectWrapper recursive(Element parentElement) {
		if (!parentElement.elements().isEmpty()) {
			List<Element> elements = parentElement.elements();

			JSONObjectWrapper jsonObject = new JSONObjectWrapperImpl();
			for (String nodeName : uniqueElementNames(elements)) {
				List<Element> childs = parentElement.elements(nodeName);

				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Unique Node Name [{}] childElements [{}]",
							nodeName, childs.size());
				}

				String attributeList = parentElement.attributeValue("class");
				if ("levels".equals(attributeList)
						|| "list".equals(attributeList)) {
					List<JSONObjectWrapper> jsonArray = new ArrayList<JSONObjectWrapper>();
					for (Element child : childs) {
						JSONObjectWrapper recursive = recursive(child);
						jsonArray.add(recursive);
					}
					JSONObjectWrapper jsonArrayObject = JSONObjectWrapperImpl
							.createInstance(parentElement.getName(), jsonArray);

					return jsonArrayObject;
				}

				JSONObjectWrapper recursive = recursive(childs.get(0));

				LOGGER.debug("JSONObject [{}]", recursive.toString());
				jsonObject.accumulate(recursive);
			}
			return jsonObject;
		}

		return JSONObjectWrapperImpl.createInstance(parentElement.getName(),
				parentElement.getTextTrim());

	}

	/**
	 * @param elements
	 * @return
	 */
	private Set<String> uniqueElementNames(List<Element> elements) {
		Set<String> elementNames = new HashSet<String>();
		for (Element element : elements) {
			elementNames.add(element.getName());
		}
		return elementNames;
	}

}
