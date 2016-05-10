package com.allinfinance.dao.common;

import java.io.Serializable;
import java.util.Map;

/**
 * This class has been automatically generated by Hibernate Synchronizer.
 * For more information or documentation, visit The Hibernate Synchronizer page
 * at http://www.binamics.com/hibernatesync or contact Joe Hudson at joe@binamics.com.
 */
public interface IQueryObj {

    /**
     * Execute a query.
     * @param query a query expressed in Hibernate's query language
     * @return a distinct list of instances (or arrays of instances)
     */
    @SuppressWarnings("unchecked")
	public abstract java.util.List find(String query);

    /**
     * Return all objects related to the implementation of this DAO with no filter.
     */
    @SuppressWarnings("unchecked")
	public abstract java.util.List findAll();

    /**
     * Obtain an instance of Query for a named query string defined in the mapping file.
     * @param name the name of a query defined externally
     * @return Query
     */
    @SuppressWarnings("unchecked")
	public abstract java.util.List findByNamedQuery(String name);

    @SuppressWarnings("unchecked")
	public abstract java.util.List findByNamedQuery(final String name, final int begin, final int count);

    /**
     * Obtain an instance of Query for a named query string defined in the mapping file.
     * Use the parameters given.
     * @param name the name of a query defined externally
     * @param params the parameter Map
     * @return Query
     */
    @SuppressWarnings("unchecked")
	public abstract java.util.List findByNamedQuery(final String name, final Map params);

    @SuppressWarnings("unchecked")
	public abstract java.util.List findByNamedQuery(final String name, final Map params, final int begin, final int count);


    /**
     * Obtain an instance of Query for a named query string defined in the mapping file.
     * Use the parameters given.
     * @param name the name of a query defined externally
     * @param params the parameter array
     * @return Query
     */
    @SuppressWarnings("unchecked")
	public abstract java.util.List findByNamedQuery(final String name, final Serializable[] params);

    @SuppressWarnings("unchecked")
	public abstract java.util.List findByNamedQuery(final String name, final Serializable[] params, final int begin, final int count);
}
