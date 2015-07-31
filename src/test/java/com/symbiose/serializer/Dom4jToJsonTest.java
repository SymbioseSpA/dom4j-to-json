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
 * 
 * @author pcollaog
 */
public class Dom4jToJsonTest {

	private static final String FILENAME_CASE1 = "Dom4jToJson-testData_case1.xml";

	private static final String FILENAME_CASE2 = "Dom4jToJson-testData_case2.xml";

	private static final String FILENAME_CASE3 = "Dom4jToJson-testData_case3.xml";

	private final boolean _validateResult;

	/**
	 * Por omision se validan los test
	 */
	public Dom4jToJsonTest() {
		this(true);
	}

	/**
	 * Para pruebas de rendimiento @{code validateResult} debe ser @{code false}
	 * 
	 * @param validateResult
	 */
	protected Dom4jToJsonTest(boolean validateResult) {
		_validateResult = validateResult;
	}

	@Test
	public void shouldSerialize_case1() throws Exception {
		Document document = DocumentHelper.parseText(IOUtils
				.toString(getClass().getResourceAsStream(FILENAME_CASE1)));

		String result = Dom4jToJson.writeToString(document.getRootElement());

		assertNotNull(result);

		if (_validateResult) {
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

	@Test
	public void shouldSerialize_case2() throws Exception {
		Document document = DocumentHelper.parseText(IOUtils
				.toString(getClass().getResourceAsStream(FILENAME_CASE2)));

		Element rootElement = document.getRootElement();

		String result = Dom4jToJson.writeToString(rootElement);

		assertNotNull(result);

		if (_validateResult) {
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
			assertEquals("ING_Transferencias - ING_Internas",
					transactionJson.getString("engServiceName"));
			assertEquals("Transferencias",
					transactionJson.getString("serviceName"));
		}
	}

	@Test
	public void shouldSerialize_case3() throws Exception {
		Document document = DocumentHelper.parseText(IOUtils
				.toString(getClass().getResourceAsStream(FILENAME_CASE3)));

		Element rootElement = document.getRootElement();

		String result = Dom4jToJson.writeToString(rootElement);

		assertNotNull(result);

		JSONArray jsonArrayResult = JSONArray.fromObject(result);

		if (_validateResult) {
			assertNotNull(jsonArrayResult);
			assertEquals(1, jsonArrayResult.size());

			JSONObject trxResult = jsonArrayResult.getJSONObject(0)
					.getJSONObject("trxResult");
			assertNotNull(trxResult);

			assertEquals("-2063670396", trxResult.getString("webId"));
			assertEquals("10523", trxResult.getString("productNumber"));
			assertEquals("SDA", trxResult.getString("productType"));
			assertEquals("Cuenta Ahorros", trxResult.getString("AcctDesc"));
			assertEquals("Cu0523", trxResult.getString("AcctNickName"));
			assertEquals("*****0523", trxResult.getString("AcctID2"));

			JSONObject statusJson = trxResult.getJSONObject("Status");
			assertNotNull(statusJson);

			assertEquals("EXI", statusJson.getString("StatusCode"));
			assertEquals("0", statusJson.getString("StatusCodeHost"));
			assertEquals("0", statusJson.getString("StatusCodeError"));
			assertEquals("0", statusJson.getString("StatusCodeAlt"));
			assertEquals("Exitosa",
					statusJson.getString("StatusCodeHostSeverity"));
			assertEquals("O.K.", statusJson.getString("StatusDesc"));
			assertEquals("Transacción Exitosa",
					statusJson.getString("StatusDescAlt"));

			JSONObject responseJson = trxResult.getJSONObject("Response");
			assertNotNull(responseJson);

			assertEquals(6, responseJson.size());
			assertEquals("3005013250", responseJson.getString("RqUID"));

			// TODO: add more asserts
		}

	}
}
