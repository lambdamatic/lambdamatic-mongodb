/**
 * 
 */
package org.lambdamatic.mongodb.apt;

import java.io.IOException;
import java.util.Map;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import org.lambdamatic.mongodb.metadata.ProjectionMetadata;
import org.lambdamatic.mongodb.metadata.QueryMetadata;

/**
 * Processor for classes annotated with {@code EmbeddedDocument}. Generates their associated {@link QueryMetadata} and
 * {@link ProjectionMetadata} implementation classes in the target folder given in the constructor.
 * 
 * @author Xavier Coulon <xcoulon@redhat.com>
 *
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({ "org.lambdamatic.mongodb.annotations.EmbeddedDocument" })
//FIXME: the EmbeddedDocumentAnnotationProcessor should actually prevent having @DocumentId, instead of accepting it 
public class EmbeddedDocumentAnnotationProcessor extends BaseAnnotationProcessor {

	/**
	 * Constructor
	 * 
	 * @throws IOException
	 *             if templates could not be loaded.
	 */
	public EmbeddedDocumentAnnotationProcessor() throws IOException {
		super();
	}

	@Override
	protected Map<String, Object> initializeTemplateContextProperties(final TypeElement domainElement) {
		final Map<String, Object> templateContextProperties = super.initializeTemplateContextProperties(domainElement);
		templateContextProperties.put(Constants.QUERY_FIELDS, getQueryFields(domainElement));
		templateContextProperties.put(Constants.PROJECTION_FIELDS, getProjectionFields(domainElement));
		templateContextProperties.put(Constants.QUERY_METADATA_CLASS_NAME, generateQueryMetadataSimpleClassName(domainElement));
		templateContextProperties.put(Constants.QUERY_ARRAY_METADATA_CLASS_NAME, generateQueryArrayMetadataSimpleClassName(domainElement));
		templateContextProperties.put(Constants.PROJECTION_METADATA_CLASS_NAME, generateProjectionSimpleClassName(domainElement));
		return templateContextProperties;
	}

	/**
	 * Generates the {@code QueryMetadata} and {@link ProjectionMetadata} implementation source code for the annotated
	 * class currently being processed.
	 * 
	 * @param allContextProperties
	 *            all properties to use when running the engine to generate the source code.
	 * 
	 * @throws IOException
	 */
	@Override
	protected void doProcess(final Map<String, Object> templateContextProperties) throws IOException {
		generateQueryMetadataSourceCode(getTemplate(Constants.QUERY_METADATA_TEMPLATE), templateContextProperties);
		generateQueryArrayMetadataSourceCode(getTemplate(Constants.QUERY_ARRAY_METADATA_TEMPLATE), templateContextProperties);
		generateProjectionMetadataSourceCode(getTemplate(Constants.EMBEDDED_PROJECTION_METADATA_TEMPLATE), templateContextProperties);
	}

}
