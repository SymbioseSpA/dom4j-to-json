package com.symbiose.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

/**
 * @author pcollaog
 *
 */
public class Dom4jToJson_Case2_Test {

	private static final String FILENAME = "Dom4jToJson-testData_case2.xml";

	private boolean _validateAsserts;

	/**
	 * 
	 */
	public Dom4jToJson_Case2_Test() {
		_validateAsserts = true;
	}

	/**
	 * @param validateAsserts
	 */
	protected Dom4jToJson_Case2_Test(boolean validateAsserts) {
		_validateAsserts = validateAsserts;
	}

	@Test
	public void shouldSerializeXmlToJson() throws Exception {
		Document document = DocumentHelper.parseText(
				IOUtils.toString(getClass().getResourceAsStream(FILENAME)));

		Element rootElement = document.getRootElement();

		String result = Dom4jToJson.writeToString(rootElement);

		assertNotNull(result);

		if (_validateAsserts) {
			JSONArray resultJsonArray = JSONArray.fromObject(result);
			JSONObject jsonObject = resultJsonArray.getJSONObject(0);

			assertEquals("000000", jsonObject.getString("statusCode"));
			assertEquals("2", jsonObject.getString("pages"));
			assertEquals("", jsonObject.getString("messageError"));
			assertEquals("2", jsonObject.getString("actualPage"));

			JSONArray responseJson = jsonObject.getJSONArray("concreteReturn");
			assertEquals(1, responseJson.size());

			JSONObject transactionJson = responseJson.getJSONObject(0);
			assertEquals("358953", transactionJson.get("id"));
			assertEquals("2015-06-26 11:43:47.567",
					transactionJson.getString("paymentDate").trim());
			assertEquals("1", transactionJson.get("paymentAmt"));
			assertEquals("EXI", transactionJson.getString("state"));
			assertEquals("366761", transactionJson.getString("autNumber"));
			assertEquals("Crédito4543",
					transactionJson.getString("productName"));
			assertEquals("ING_Transferencias - ING_Internas, Aval, ACH",
					transactionJson.getString("engServiceName"));
			assertEquals("Transferencias - Internas, Aval, ACH",
					transactionJson.getString("serviceName"));
		}
	}

}
