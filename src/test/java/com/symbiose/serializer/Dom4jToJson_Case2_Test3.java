package com.symbiose.serializer;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

/**
 * @author pcollaog
 *
 */
public class Dom4jToJson_Case2_Test3 {

	private static final String FILENAME = "Dom4jToJson-testData_case3.xml";

	private boolean _validateAsserts;

	/**
	 * 
	 */
	public Dom4jToJson_Case2_Test3() {
		_validateAsserts = true;
	}

	/**
	 * @param validateAsserts
	 */
	protected Dom4jToJson_Case2_Test3(boolean validateAsserts) {
		_validateAsserts = validateAsserts;
	}

	@Test
	public void shouldSerializeXmlToJson() throws Exception {
		Document document = DocumentHelper.parseText(
				IOUtils.toString(getClass().getResourceAsStream(FILENAME)));

		Element rootElement = document.getRootElement();

		String result = Dom4jToJson.writeToString(rootElement);

		assertNotNull(result);

	}

}
