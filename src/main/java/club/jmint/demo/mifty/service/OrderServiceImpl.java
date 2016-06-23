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
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

import club.jmint.demo.mifty.service.gen.OrderService.Iface;
import club.jmint.mifty.dao.Sql;
import club.jmint.mifty.log.MyLog;
import club.jmint.mifty.service.MiftyService;

public class OrderServiceImpl extends MiftyService implements Iface {
	private IIdBuilder ib = new IndexBasedIdBuilder();
	private String dbName = "db_twohalf";
	private String orderTable = dbName+".t_order";
	private Sql ddl = Sql.getInstance();

	/**
	 * @param order info fields
	 * @return id
	 */
	@Override
	public Map<String, String> orderCreate(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		//before order creating, we should check product_id, user_id
		//TODO added later
		
		//create a unique order id
		String id = Integer.toString(ib.generateOrderIdx());
		params.put("id", id);
		
		int erows = ddl.sqlCreate(orderTable, params);
		if (erows<0){
			MyLog.logger.error("order create failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		}
		else if (erows==0){
		}
		else {
			MyLog.logger.info("created order: " + id);
		}
		
		resultMap.put("id", id);
		return resultMap;
	}

	/**
	 * @param id
	 * @return errorCode
	 */
	@Override
	public Map<String, String> orderDelete(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String condition = "id='"+id+"'";
		
		int erows = ddl.sqlDelete(orderTable, condition);
		if (erows<0){
			MyLog.logger.error("order delete failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		}
		else if (erows==0){
			MyLog.logger.warn("order not exists: " + id);
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;
		}
		else{
			MyLog.logger.info("deleted order: " + id);
		}
		
		return resultMap;
	}

	/**
	 * @param id and fields
	 * @return errorCode
	 */
	@Override
	public Map<String, String> orderUpdate(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String condition = "id="+id;
		
		int erows = ddl.sqlUpdate(orderTable, params, condition);
		if (erows<0){
			MyLog.logger.error("order update failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		} else if (erows==0){
			MyLog.logger.warn("order not exists: " + id);
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;			
		} else {
			MyLog.logger.info("updated order: " + id);
		}
		
		resultMap.put("id", id);
		return resultMap;
	}

	@Override
	public Map<String, String> orderInfoQuery(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String condition = "id="+id;
		
		String fields = params.get("fields");
		String[] fieldlist = fields.split(",");
		
		List<Object[]> list = ddl.sqlSelect(orderTable, fields, condition);
		if (list.isEmpty()){
			MyLog.logger.warn("order not exists: " + id);
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;			
		}
		MyLog.logger.info("query order: " + id);
		
		Object[] obj = list.get(0);
		for (int i=0;i<fieldlist.length;i++){
			resultMap.put(fieldlist[i], Sql.getSqlResultFieldAsString(obj[i]));
		}
		return resultMap;

	}

	@Override
	public Map<String, String> orderListQuery(Map<String, String> params) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

}

