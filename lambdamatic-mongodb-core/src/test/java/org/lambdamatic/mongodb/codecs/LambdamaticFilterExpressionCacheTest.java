/**
 * 
 */
package org.lambdamatic.mongodb.codecs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.bson.BsonWriter;
import org.bson.codecs.EncoderContext;
import org.bson.json.JsonWriter;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.lambdamatic.FilterExpression;
import org.lambdamatic.analyzer.LambdaExpressionAnalyzer;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.LoggerFactory;

import com.sample.EnumFoo;
import com.sample.Foo_;

/**
 * Testing that a lambda expression is analyzed once but filter query is
 * generated with proper captured argument for each call.
 * 
 * @author Xavier Coulon <xcoulon@redhat.com>
 *
 */
public class LambdamaticFilterExpressionCacheTest {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LambdamaticFilterExpressionCodecTest.class);
	
	@Test
	public void shouldAnalyzeOnce() throws UnsupportedEncodingException, IOException, JSONException {
		// given
		final LambdaExpressionAnalyzer analyzer = LambdaExpressionAnalyzer.getInstance();
		analyzer.resetHitCounters();
		// when
		performAndAssertConvertion("John", 42, EnumFoo.FOO);
		performAndAssertConvertion("Jack", 43, EnumFoo.BAR);
		// then
		Assertions.assertThat(analyzer.getCacheMisses()).isEqualTo(1);
		Assertions.assertThat(analyzer.getCacheHits()).isEqualTo(1);
	}

	private void performAndAssertConvertion(final String stringField, final int primitiveIntField, final EnumFoo enumFoo) throws UnsupportedEncodingException, IOException, JSONException {
		// given
		final FilterExpression<Foo_> expr = ((Foo_ foo) -> foo.stringField.equals(stringField) || foo.primitiveIntField == primitiveIntField || foo.enumFoo == enumFoo);
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final BsonWriter bsonWriter = new JsonWriter(new OutputStreamWriter(outputStream, "UTF-8")); 
		final EncoderContext context = EncoderContext.builder().isEncodingCollectibleDocument(true).build();
		// when
		new FilterExpressionCodec().encode(bsonWriter, expr, context);
		// then
		final String actual = IOUtils.toString(outputStream.toByteArray(), "UTF-8");
		final String expected = "{$or: [{primitiveIntField: " + primitiveIntField + "}, {enumFoo: '" + enumFoo + "'}, {stringField: '"+ stringField + "'}]}";
		LOGGER.debug("Comparing \n{} vs \n{}", expected, actual);
		JSONAssert.assertEquals(expected, actual, false);
	}
}
