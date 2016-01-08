package com.devfd0.bitcoinrpc;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

/**
 * Provides a HttpEntity for json content
 */
class JSONEntity extends StringEntity 
{
	public JSONEntity(org.json.simple.JSONObject r) throws UnsupportedEncodingException 
	{
		super(r.toString());
	}

	@Override
	public Header getContentType() 
	{
		return new BasicHeader(HTTP.CONTENT_TYPE, "application/json");
	}	
}
