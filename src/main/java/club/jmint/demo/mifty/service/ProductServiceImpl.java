/*
 * Copyright 2016 The Mifty Project
 *
 * The Mifty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package club.jmint.demo.mifty.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.thrift.TException;

import club.jmint.demo.mifty.service.gen.ProductService;
import club.jmint.demo.mifty.service.gen.ProductService.Iface;
import club.jmint.mifty.dao.Sql;
import club.jmint.mifty.log.MyLog;
import club.jmint.mifty.service.MiftyService;

public class ProductServiceImpl extends MiftyService implements Iface {
	private IIdBuilder ib = new IndexBasedIdBuilder();
	private String dbName = "db_twohalf";
	private String productTable = dbName+".t_product";
	private Sql ddl = Sql.getInstance();

	/**
	 * @param product info fields: name,title,short_intro,long_intro,address,start_time,
	 * 		end_time,ticket_price,sponsor_id,sponsor_name,note,operator_id,operator_name
	 * @return id
	 */
	@Override
	public Map<String, String> productAdd(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		//create a unique product id
		String id = Integer.toString(ib.generateProductIdx());
		params.put("id", id);
		
		int erows = ddl.sqlCreate(productTable, params);
		if (erows<0){
			MyLog.logger.error("product add failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		}
		else if (erows==0){
		}
		else {
			MyLog.logger.info("added product: " + id);
		}
		
		resultMap.put("id", id);
		return resultMap;
	}
	
	/**
	 * @param id
	 * @return errorCode
	 */
	@Override
	public Map<String, String> productDelete(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String condition = "id="+id;
		
		int erows = ddl.sqlDelete(productTable, condition);
		if (erows<0){
			MyLog.logger.error("product delete failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		}
		else if (erows==0){
			MyLog.logger.warn("product not exists: " + id);
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;
		}
		else{
			MyLog.logger.info("deleted product: " + id);
		}
		
		return resultMap;
	}
	
	/**
	 * @param id, fields to be updated, see t_product
	 * @return errorCode
	 */
	@Override
	public Map<String, String> productUpdate(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String condition = "id="+id;
		
		int erows = ddl.sqlUpdate(productTable, params, condition);
		if (erows<0){
			MyLog.logger.error("product update failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		} else if (erows==0){
			MyLog.logger.warn("product not exists: " + id);
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;			
		} else {
			MyLog.logger.info("updated product: " + id);
		}
		
		resultMap.put("id", id);
		return resultMap;
	}

	/**
	 * @param id and fields(example:"id,a,b,c,d"), see table t_product for fields' details.
	 * @return required fields and values
	 */
	@Override
	public Map<String, String> productInfoQuery(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String condition = "id="+id;
		
		String fields = params.get("fields");
		String[] fieldlist = fields.split(",");
		
		List<Object[]> list = ddl.sqlSelect(productTable, fields, condition);
		if (list.isEmpty()){
			MyLog.logger.warn("product not exists: " + id);
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;			
		}
		MyLog.logger.info("query product: " + id);
		
		Object[] obj = list.get(0);
		for (int i=0;i<fieldlist.length;i++){
			resultMap.put(fieldlist[i], Sql.getSqlResultFieldAsString(obj[i]));
		}
		return resultMap;
	}

	@Override
	public Map<String, String> productListQuery(Map<String, String> params) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

}

