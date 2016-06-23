package club.jmint.demo.mifty.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.thrift.TException;

import com.google.gson.*;
import club.jmint.mifty.dao.Sql;
import club.jmint.mifty.log.MyLog;
import club.jmint.mifty.service.MiftyService;
import club.jmint.crossing.utils.Security;
import club.jmint.demo.mifty.service.gen.UserService;
import club.jmint.demo.mifty.service.gen.UserService.Iface;
import club.jmint.demo.mifty.utils.Tools;

public class UserServiceImpl extends MiftyService implements Iface {
	private IIdBuilder ib = new IndexBasedIdBuilder();
	private String dbName = "db_twohalf";
	private String userTable = dbName+".t_user";
	Sql ddl = Sql.getInstance();
	
	/**
	 * Check the given user's existence by given tag 
	 * @param tag: id or mobile
	 * @return boolean
	 */
	public boolean isUserExist(String tag, String value){
		String querysql = "select "+tag+" from " + userTable + " where "+tag+"='"+value+"'";
		List<Object[]> list = ddl.doSelect(querysql);
		if (list==null || list.size()<1){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @param id or mobile
	 * @param password
	 * @return errorCode
	 */
	@Override
	public Map<String, String> passwordVerify(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String mobile = params.get("mobile");
		String pwdinput = Security.crossingSign(params.get("password"), "", "MD5");
		
		//get user info
		String sql = "select id,mobile,password from " + userTable + " where id="+id+" or mobile='"+mobile+"'";
		List<Object[]> list = ddl.doSelect(sql);
		if (list.isEmpty()){
			MyLog.logger.warn("user not exists: " + id +"/"+Tools.getMaskedMobile(mobile));
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;			
		}
		Object[] obj = list.get(0);
		String pwd = ddl.getSqlResultFieldAsString(obj[2]);
		if ((pwd!=null) && pwd.equals(pwdinput)){
			//Verify ok.
			MyLog.logger.info("password verify OK: " + id +"/"+Tools.getMaskedMobile(mobile));
		} else {
			//Verify failed.
			MyLog.logger.error("password verify failed: " + id +"/"+Tools.getMaskedMobile(mobile));
			resultMap.put("errorCode", Integer.toString(ErrorCode.USER_PASSWORD_INCORRECT.getCode()));
			resultMap.put("errorInfo", ErrorCode.USER_PASSWORD_INCORRECT.getInfo());
		}
		
		return resultMap;
	}

	/**
	 * @param user info must fields: mobile,password
	 * @return id
	 */
	@Override
	public Map<String, String> userAdd(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		//check if user exists
		String mobile = params.get("mobile");
		if (isUserExist("mobile",mobile)){
			MyLog.logger.warn("user already exists with mobile: " + Tools.getMaskedMobile(mobile));
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_ALREADY_EXISTS.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_ALREADY_EXISTS.getInfo());
			return resultMap;
		}
		
		//insert a user record
		String id = Integer.toString(ib.generateUserIdx());
		String insertsql = "insert into "+userTable+" (id,password,mobile,create_time) values ("
				+ ""+id+","
				+ "'"+Tools.getEncryptPassword(params.get("password"))+"',"
				+ "'"+params.get("mobile")+"',"
				+ "now()" 
				+ ")";
		int erows = ddl.doInsert(insertsql);
		if (erows<0){
			MyLog.logger.error("user add failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		}
		else if (erows==0){
			
		}
		else{
			MyLog.logger.info("added user: " + id +"/"+Tools.getMaskedMobile(mobile));
		}
		
		resultMap.put("id", id);
		return resultMap;
	}

	/**
	 * @param id or mobile
	 * @return errorCode
	 */
	@Override
	public Map<String, String> userDelete(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String mobile = params.get("mobile");
		String id = params.get("id");
		
		//delete a user record
		String deletesql = "delete from "+userTable+" where id="+id+" or mobile='"+mobile+"'";
		int erows = ddl.doDelete(deletesql);
		if (erows==-1){
			MyLog.logger.error("user delete failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		}
		else if (erows==0){
			MyLog.logger.warn("user not exists: " + id +"/"+Tools.getMaskedMobile(mobile));
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;
		}
		else if (erows>=1){
			MyLog.logger.info("deleted user: " + id +"/"+Tools.getMaskedMobile(mobile));
		}
		else{
			//unexpected
		}
		
		return resultMap;
	}

	/**
	 * @param id
	 * @param user info fields: 
	 * @return errorCode
	 */
	@Override
	public Map<String, String> userUpdate(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String mobile = params.get("mobile");
		
		StringBuffer updatesql = new StringBuffer();
		updatesql.append("update " + userTable + " set ");
		updatesql.append("update_time=now()");
		
		Iterator<Entry<String,String>> it = params.entrySet().iterator();
		Entry<String,String> en;
		while(it.hasNext()){
			//there maybe errors for different database fields' type, let's see later or some fixes.
			updatesql.append(",");
			en = it.next();
			updatesql.append(en.getKey() + "='" + en.getValue() + "'"); 
		}
		updatesql.append(" where id="+id + " or mobile='" + mobile +"'");
		MyLog.logger.debug("SQL: " + updatesql.toString());
		
		int erows = ddl.doUpdate(updatesql.toString());
		if (erows<0){
			MyLog.logger.error("user update failed.");
			resultMap.put("errorCode", Integer.toString(ErrorCode.DB_OPERATION_FAILED.getCode()));
			resultMap.put("errorInfo", ErrorCode.DB_OPERATION_FAILED.getInfo());
			return resultMap;			
		} else if (erows==0){
			MyLog.logger.warn("user not exists: " + id +"/"+Tools.getMaskedMobile(mobile));
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;			
		} else {
			MyLog.logger.info("updated user: " + id +"/"+Tools.getMaskedMobile(mobile));
		}
		
		resultMap.put("id", id);
		return resultMap;
	}

	/**
	 * @param id or mobile
	 * @return user info fields:
	 */
	@Override
	public Map<String, String> userInfoQuery(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String id = params.get("id");
		String mobile = params.get("mobile");
		String fields = params.get("fields");
		String[] fieldlist = fields.split(",");
		String condition = id="id="+id+" or mobile='"+mobile+"'";
		
		List<Object[]> list = ddl.sqlSelect(userTable, fields, condition);
		//if (list==null || list.isEmpty()){
		if (list.isEmpty()){
			MyLog.logger.warn("user not exists: " + id +"/"+Tools.getMaskedMobile(mobile));
			resultMap.put("errorCode", Integer.toString(ErrorCode.OBJECT_NOT_EXIST.getCode()));
			resultMap.put("errorInfo", ErrorCode.OBJECT_NOT_EXIST.getInfo());
			return resultMap;			
		}
		MyLog.logger.info("query user: " + id +"/"+Tools.getMaskedMobile(mobile));
		
		Object[] obj = list.get(0);
		for(int i=0;i<fieldlist.length;i++){
			resultMap.put(fieldlist[i], ddl.getSqlResultFieldAsString(obj[i]));
		}
		return resultMap;
	}

	@Override
	public Map<String, String> userListQuery(Map<String, String> params) throws TException {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		
		
		return resultMap;
	}


}
