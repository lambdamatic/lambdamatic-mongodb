/*******************************************************************************
 * Copyright (c) 2015 Red Hat. All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Red Hat - Initial Contribution
 *******************************************************************************/

package org.lambdamatic.mongodb.metadata;

import java.util.Collection;

import org.lambdamatic.mongodb.FilterExpression;

/**
 * MongoDB operations available on a given Document field when specifying a query filter.
 * 
 * @param <DomainType> the actual domain type
 */
public interface QueryField<DomainType> {


  /**
   * Specifies equality condition. The {@link QueryField#equals(Object)} ({@code $eq}) operator
   * matches documents where the value of a field equals the specified value.
   * 
   * @param value the specified value to match.
   * @return a boolean so that this method can be used in a {@link FilterExpression}
   * @see <a href="http://docs.mongodb.org/manual/reference/operator/query/eq/#op._S_eq">MongoDB
   *      documentation</a>
   */
  @Override
  @MongoOperation(MongoOperator.EQUALS)
  public boolean equals(Object value);

  /**
   * Selects the documents where the value of the field is not equal (i.e. !=) to the specified
   * value. <strong>Note:</strong> This includes documents that do not contain the field.
   * 
   * @param value the specified value to match.
   * @return a boolean so that this method can be used in a {@link FilterExpression}
   * @see <a href="http://docs.mongodb.org/manual/reference/operator/query/ne/#op._S_ne">MongoDB
   *      documentation</a>
   */
  @MongoOperation(MongoOperator.NOT_EQUALS)
  public boolean notEquals(DomainType value);

  /**
   * Selects the documents where the value of the field is greater than (i.e. "&gt;") the specified
   * value.
   * 
   * @param value the specified value to compare with.
   * @return a boolean so that this method can be used in a {@link FilterExpression}
   * @see <a href="http://docs.mongodb.org/manual/reference/operator/query/gt/#op._S_gt">MongoDB
   *      documentation</a>
   */
  @MongoOperation(MongoOperator.GREATER)
  public boolean greaterThan(DomainType value);

  /**
   * Selects the documents where the value of the field is greater than or equal to (i.e. "&gt;=") a
   * specified value (e.g. value.)
   * 
   * @param value the specified value to compare with.
   * @return a boolean so that this method can be used in a {@link FilterExpression}
   * 
   * @see <a href="http://docs.mongodb.org/manual/reference/operator/query/gte/#op._S_gte">MongoDB
   *      documentation</a>
   */
  @MongoOperation(MongoOperator.GREATER_EQUALS)
  public boolean greaterOrEquals(DomainType value);

  /**
   * Selects the documents where the value of the field is less than (i.e. "&lt;") the specified
   * value.
   * 
   * @param value the specified value to compare with.
   * @return a boolean so that this method can be used in a {@link FilterExpression}
   * 
   * @see <a href="http://docs.mongodb.org/manual/reference/operator/query/lt/#op._S_lt">MongoDB
   *      documentation</a>
   */
  @MongoOperation(MongoOperator.LESS)
  public boolean lessThan(DomainType value);

  /**
   * Selects the documents where the value of the field is less than or equal to (i.e. "&lt;=") the
   * specified value.
   * 
   * @param value the specified value to compare with.
   * @return a boolean so that this method can be used in a {@link FilterExpression}
   * 
   * @see <a href="http://docs.mongodb.org/manual/reference/operator/query/lte/#op._S_lte">MongoDB
   *      documentation</a>
   */
  @MongoOperation(MongoOperator.LESS_EQUALS)
  public boolean lessOrEquals(DomainType value);

  /**
   * The {@link QueryField#in(Object...)} ({@code $in}) operator selects the documents where the
   * value of a field equals any value in the specified array. If the current field holds an array,
   * then the $in operator selects the documents whose field holds an array that contains at least
   * one element that matches a value in the specified array (e.g. {@code value1}, {@code value2},
   * etc.)
   * 
   * @return a boolean so that this method can be used in a {@link FilterExpression}
   * @param values the specified values to compare with.
   * @see <a href="http://docs.mongodb.org/manual/reference/operator/query/in/#op._S_in">MongoDB
   *      documentation</a>
   */
  @MongoOperation(MongoOperator.IN)
  public boolean in(@SuppressWarnings("unchecked") DomainType... values);

  /**
   * The {@link QueryField#in(Collection)} ({@code $in}) operator selects the documents where the
   * value of a field equals any value in the specified array. If the current field holds an array,
   * then the $in operator selects the documents whose field holds an array that contains at least
   * one element that matches a value in the specified array (e.g. {@code value1}, {@code value2},
   * etc.)
   * 
   * @return a boolean so that this method can be used in a {@link FilterExpression}
   * @param values the specified value to match.
   * @see <a href="http://docs.mongodb.org/manual/reference/operator/query/in/#op._S_in">MongoDB
   *      documentation</a>
   */
  @MongoOperation(MongoOperator.IN)
  public boolean in(Collection<DomainType> values);

  /**
   * selects the documents where:
   * <ul>
   * <li>the field value is not in the specified array or</li>
   * <li>the field does not exist.</li>
   * </ul>
   * 
   * @param values the specified values to compare with.
   * @return a boolean so that this method can be used in a {@link FilterExpression}
   * @see <a href="http://docs.mongodb.org/manual/reference/operator/query/nin/#op._S_nin">MongoDB
   *      documentation</a>
   */
  @MongoOperation(MongoOperator.NOT_IN)
  public boolean notIn(@SuppressWarnings("unchecked") DomainType... values);

  /**
   * selects the documents where:
   * <ul>
   * <li>the field value is not in the specified array or</li>
   * <li>the field does not exist.</li>
   * </ul>
   * 
   * @param values the specified values to compare with.
   * @return a boolean so that this method can be used in a {@link FilterExpression}
   * @see <a href="http://docs.mongodb.org/manual/reference/operator/query/nin/#op._S_nin">MongoDB
   *      documentation</a>
   */
  @MongoOperation(MongoOperator.NOT_IN)
  public boolean notIn(Collection<DomainType> values);

}
