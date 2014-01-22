package com.aerospike.client.rest;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Record;
import com.aerospike.client.query.ResultSet;

@SuppressWarnings("serial")
public class JSONResultSet extends JSONArray{
	@SuppressWarnings("unchecked")
	public JSONResultSet(ResultSet resultSet) throws AerospikeException{
		super();
		try {
			while (resultSet.next()) {
				Record record =  (Record) resultSet.getObject();
				add(new JSONRecord(record));
			}
		} finally {
			resultSet.close();
		}
	}
}
