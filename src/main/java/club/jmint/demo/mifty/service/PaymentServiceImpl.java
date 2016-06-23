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

import club.jmint.demo.mifty.service.gen.PaymentService;
import club.jmint.demo.mifty.service.gen.PaymentService.Iface;
import club.jmint.mifty.dao.Sql;
import club.jmint.mifty.log.MyLog;
import club.jmint.mifty.service.MiftyService;

public class PaymentServiceImpl extends MiftyService implements Iface {
	private IIdBuilder ib = new IndexBasedIdBuilder();
	private String dbName = "db_twohalf";
	private String transTable = dbName+".t_transaction";
	private Sql ddl = Sql.getInstance();
	
	@Override
	public Map<String, String> payCreate(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		//create a unique transanction id
		String id = Integer.toString(ib.generateTransactionIdx());
		params.put("id", id);
		
		int erows = ddl.sqlCreate(transTable, params);
		if (erows<0){
			MyLog.logger.error("transanction add failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		}
		else if (erows==0){
		}
		else {
			MyLog.logger.info("added transaction: " + id);
		}
		
		resultMap.put("id", id);
		return resultMap;

	}

	@Override
	public Map<String, String> payDelete(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String condition = "id='"+id+"'";
		
		int erows = ddl.sqlDelete(transTable, condition);
		if (erows<0){
			MyLog.logger.error("transaction delete failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		}
		else if (erows==0){
			MyLog.logger.warn("transaction not exists: " + id);
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;
		}
		else{
			MyLog.logger.info("deleted transaction: " + id);
		}
		
		return resultMap;

	}

	@Override
	public Map<String, String> payUpdate(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String condition = "id='"+id+"'";
		
		int erows = ddl.sqlUpdate(transTable, params, condition);
		if (erows<0){
			MyLog.logger.error("transaction update failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		} else if (erows==0){
			MyLog.logger.warn("transaction not exists: " + id);
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;			
		} else {
			MyLog.logger.info("updated transaction: " + id);
		}
		
		resultMap.put("id", id);
		return resultMap;
	}

	@Override
	public Map<String, String> payInfoQuery(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String condition = "id='"+id+"'";
		
		String fields = params.get("fields");
		String[] fieldlist = fields.split(",");
		
		List<Object[]> list = ddl.sqlSelect(transTable, fields, condition);
		if (list.isEmpty()){
			MyLog.logger.warn("transaction not exists: " + id);
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;			
		}
		MyLog.logger.info("query transaction: " + id);
		
		Object[] obj = list.get(0);
		for (int i=0;i<fieldlist.length;i++){
			resultMap.put(fieldlist[i], Sql.getSqlResultFieldAsString(obj[i]));
		}
		return resultMap;

	}

	@Override
	public Map<String, String> payListQuery(Map<String, String> params) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

}

