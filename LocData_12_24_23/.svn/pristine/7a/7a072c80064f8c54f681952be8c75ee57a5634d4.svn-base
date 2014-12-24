package com.modou.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.modou.utils.MLog;

public class DataInvoker<T> {
	
	private final String TAG = "DataInvoker";
	
	private static final int STATUS_SUCCESS = 0x01;
    private static final int STATUS_INVOKE_FAIL = 0x02;
    private static final int STATUS_HTTP_RESPONSE_STATUS_CODE_NOT_200 = 0x04;
    private static final int STATUS_RET_DATA_INVALID = 0x08;
    private static final int STATUS_INVOKE_STOP = 0x10;
	
    private int status;
    private boolean invoking;
    private boolean connecting;
    private boolean stop;
    
    private DataParseHandler<T> parser;
    private T t;
    private String url;
    private List<NameValuePair> nvps;
    
    private HttpPost post;
    private Configuration config;
    
    public DataInvoker(Configuration config, DataParseHandler<T> parser, String url, List<NameValuePair> nvps) {
    	this.config = config;
    	if (parser == null)
    		throw new NullPointerException("parser is null");
    	if (url == null)
    		throw new NullPointerException("url is null");
    	if (nvps == null)
    		throw new NullPointerException("List<NameValuePair> is null");
    	this.parser = parser;
    	this.url = url;
    	this.nvps = nvps;
    }
    
    public boolean isFail() {
    	if (t == null)
    		return true;
    	return status == STATUS_INVOKE_FAIL
    			|| status == STATUS_RET_DATA_INVALID
    			|| status == STATUS_HTTP_RESPONSE_STATUS_CODE_NOT_200;
    }
    
    public boolean isSuccess() {
    	return status == STATUS_SUCCESS;
    }
    
    public void invoke() {
    	if (nvps == null || invoking) {
    		MLog.e(TAG, "进行回调时，参数非法: nvps=" + nvps + " ,invoking=" + invoking);
    		return;
    	}
    	
    	try {
	    	invoking = true;
	    	post = new HttpPost(url);
	    	try {
				post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				MLog.d(TAG, "url:" + url + ", nvps>>>>" + nvps.toString());
			} catch (UnsupportedEncodingException e) {
				status = STATUS_INVOKE_FAIL;
				return;
			}
	    	
	    	HttpResponse resp = null;
	    	HttpClient http = createHttpClient();
	    	stop = false;
	    	connecting = true;
	    	try {
				resp = http.execute(post);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				if (post.isAborted()) {
					status = STATUS_INVOKE_STOP;
					return;
				}
				status = STATUS_INVOKE_FAIL;
				post.abort();
				return;
			} catch (IOException e) {
				e.printStackTrace();
				if (post.isAborted()) {
					status = STATUS_INVOKE_STOP;
					return;
				}
				status = STATUS_INVOKE_FAIL;
				post.abort();
				return;
			} catch (Exception e) {
				e.printStackTrace();
				if (post.isAborted()) {
					status = STATUS_INVOKE_STOP;
	                return;
				}
				status = STATUS_INVOKE_FAIL;
	            post.abort();
	            return;
			}
	    	
	    	final int statusCode = resp.getStatusLine().getStatusCode();
	    	if (statusCode != HttpStatus.SC_OK) {
	    		status = STATUS_HTTP_RESPONSE_STATUS_CODE_NOT_200;
	    		return;
	    	}
	    	
	    	String respData = null;
	    	HttpEntity entity = resp.getEntity();
	    	if (entity == null) {
	    		status = STATUS_INVOKE_FAIL;
	    		return;
	    	}
	    	try {
				respData = EntityUtils.toString(entity);
				connecting = false;
			} catch (ParseException e) {
				if (post.isAborted()) {
	                status = STATUS_INVOKE_STOP;
	                return;
	            }
	            status = STATUS_INVOKE_FAIL;
	            post.abort();
	            return;
			} catch (IOException e) {
				if (post.isAborted()) {
	                status = STATUS_INVOKE_STOP;
	                return;
	            }
	            status = STATUS_INVOKE_FAIL;
	            post.abort();
	            return;
			} catch (Exception e) {
				if (post.isAborted()) {
	                status = STATUS_INVOKE_STOP;
	                return;
	            }
	            status = STATUS_INVOKE_FAIL;
	            post.abort();
	            return;
			} finally {
				if (entity != null)
					try {
						entity.consumeContent();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
	    	
	    	try {
		    	t = parser.parseRet(respData);
		    	MLog.d("mylog", "url=" + url + " ,respData>>>:" + respData);
		    	if (t == null) {
		    		status = STATUS_RET_DATA_INVALID;
		            return;
		    	}
	    	} catch (Exception e) {
	    		status = STATUS_RET_DATA_INVALID;
	    		return;
	    	}
	    	status = STATUS_SUCCESS;
    	} catch (Throwable e) {
            status = STATUS_INVOKE_FAIL;
        } finally {
            connecting = false;
            invoking = false;
            parser = null;
            post = null;
            nvps = null;
        }
    }
    
    private HttpClient createHttpClient() {
    	if (url.indexOf("https://") == 0) {
    		SchemeRegistry schemeRegistry = new SchemeRegistry(); // http scheme
    		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), config.getHttpPort()));
    		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), config.getHttpsPort()));
    	
    		HttpParams params = new BasicHttpParams();
    		HttpConnectionParams.setConnectionTimeout(params, config.getTimeOut());
    		HttpConnectionParams.setSoTimeout(params, config.getTimeOut());
    		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
    		ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
    		
    		HttpClient httpClient = new DefaultHttpClient(cm, params);
    		return httpClient;
    	}
    	
    	DefaultHttpClient http = new DefaultHttpClient();
    	HttpParams my_httpParams = http.getParams();
    	HttpConnectionParams.setConnectionTimeout(my_httpParams, config.getTimeOut());
    	HttpConnectionParams.setSoTimeout(my_httpParams, config.getTimeOut());
    	return http;
    }
    
    public T getRet() {
    	return t;
    }
    
    public boolean isStop() {
    	return stop;
    }
    
    public boolean stop() {
    	if (connecting && post != null) {
    		post.abort();
    		stop = true;
    		status = STATUS_INVOKE_STOP;
    		return true;
    	}
    	return false;
    }
}
