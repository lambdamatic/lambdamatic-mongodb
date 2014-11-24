/*******************************************************************************
 * Copyright (c) 2014 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package org.lambdamatic.mongodb.converters;

import java.util.Arrays;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.lambdamatic.FilterExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Xavier Coulon <xcoulon@redhat.com>
 *
 */
public class LambdamaticFilterExpressionCodecProvider<M> implements CodecProvider {

	/** The usual Logger.*/
	private static final Logger LOGGER = LoggerFactory.getLogger(LambdamaticFilterExpressionCodecProvider.class);
	
	/** The generated metadata class associated with a user-defined domain class. */
	private final Class<M> metadataClass;

	public LambdamaticFilterExpressionCodecProvider(final Class<M> metadataClass) {
		this.metadataClass = metadataClass;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.bson.codecs.configuration.CodecProvider#get(java.lang.Class,
	 *      org.bson.codecs.configuration.CodecRegistry)
	 */
	@Override
	public <E> Codec<E> get(final Class<E> clazz, final CodecRegistry registry) {
		try {
			if(Arrays.stream(clazz.getInterfaces()).anyMatch(i -> i.equals(FilterExpression.class))) {
				return new LambdamaticFilterExpressionCodec<E>(clazz);
			}
		} catch (SecurityException | IllegalArgumentException e) {
			LOGGER.error("Failed to check if class '{}' is an instance of ''", e, clazz.getName(), FilterExpression.class.getName());
		}
		return null;
	}

}
