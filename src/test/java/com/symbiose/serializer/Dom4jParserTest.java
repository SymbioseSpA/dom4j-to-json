package com.symbiose.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author pcollaog
 *
 */
public class Dom4jParserTest {

	private static final String FILENAME = "Dom4jToJson-testData_case3.xml";

	@Test
	public final void test() throws Exception {
		Document document = DocumentHelper.parseText(
				IOUtils.toString(getClass().getResourceAsStream(FILENAME)));

		Element rootElement = document.getRootElement();

		Dom4jParser parser = new Dom4jParser();

		JSONObject result = parser.toJSON(rootElement);

		assertNotNull(result);

	}

	@Test
	public void testName() throws Exception {
		Document document = DocumentHelper.parseText(IOUtils.toString(
				getClass().getResourceAsStream("Dom4jToJson-testData.xml")));

		Dom4jParser parser = new Dom4jParser();
		JSONObject result = parser.toJSON(document.getRootElement());

		assertNotNull(result);

		JSONArray resultJsonArray = JSONArray.fromObject(result);
		JSONObject jsonObject = resultJsonArray.getJSONObject(0);

		assertEquals("000000", jsonObject.getString("statusCode"));
		assertEquals("1", jsonObject.getString("actualPage"));
		assertEquals("1", jsonObject.getString("pages"));

		JSONArray responseJson = jsonObject.getJSONArray("concreteReturn");
		assertEquals(5, responseJson.size());

		JSONObject transactionJson = responseJson.getJSONObject(0);
		assertEquals("345700", transactionJson.get("paymentAmt"));
		assertEquals("2015-06-22 20:10:53.355",
				transactionJson.getString("paymentDate").trim());
		assertEquals("En L&iacute;nea",
				transactionJson.getString("paymentType"));
		assertEquals("AHO0100", transactionJson.getString("productOrigin"));
		assertEquals("Pago Gas", transactionJson.getString("serviceName"));
		assertEquals("EXI", transactionJson.getString("state"));
		assertEquals("4194046", transactionJson.getString("id"));

	}

}
