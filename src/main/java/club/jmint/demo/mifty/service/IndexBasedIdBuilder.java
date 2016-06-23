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

import java.util.List;

import club.jmint.mifty.dao.Sql;
import club.jmint.mifty.service.MiftyService;
import club.jmint.mifty.wizard.WizardManager;

import club.jmint.crossing.log.MyLog;

public class IndexBasedIdBuilder implements IIdBuilder{
	public final static int ID_TYPE_ORDER = 0;
	public final static int ID_TYPE_PRODUCT = 1;
	public final static int ID_TYPE_REVIEW = 2;
	public final static int ID_TYPE_SPONSOR = 3;
	public final static int ID_TYPE_USER = 4;
	public final static int ID_TYPE_TRANSACTION = 5;
	
	public final static String idtable = "db_twohalf.t_ids";
	Sql ddl = Sql.getInstance();
	
	public synchronized int getIds(int type){
		String querysql = "select type,name,base_idx,max_idx from " + idtable + " where type=" + type;
		String updatesql = "update " + idtable + " set max_idx=max_idx+1 where type=" + type;
		
		int ures = ddl.doUpdate(updatesql);
		if (ures<1){
			MyLog.logger.warn("update idx failed.");
		}
		
		List<Object[]> list = ddl.doSelect(querysql);
		Object[] obj = list.get(0);
		int idx = Integer.parseInt(obj[3].toString());
		return idx;
	}
	
	public synchronized int generateOrderIdx(){
		String querysql = "select type,name,base_idx,max_idx from " + idtable + " where type=" + ID_TYPE_ORDER;
		String updatesql = "update " + idtable + " set max_idx=max_idx+1 where type=" + ID_TYPE_ORDER;
		
		int ures = ddl.doUpdate(updatesql);
		if (ures<1){
			MyLog.logger.warn("update order idx failed.");
		}
		
		List<Object[]> list = ddl.doSelect(querysql);
		Object[] obj = list.get(0);
		int idx = Integer.parseInt(obj[3].toString());
		return idx;
	}
	
	public synchronized int generateProductIdx(){
		String querysql = "select type,name,base_idx,max_idx from " + idtable + " where type=" + ID_TYPE_PRODUCT;
		String updatesql = "update " + idtable + " set max_idx=max_idx+1 where type=" + ID_TYPE_PRODUCT;
		
		int ures = ddl.doUpdate(updatesql);
		if (ures<1){
			MyLog.logger.warn("update user idx failed.");
		}
		
		List<Object[]> list = ddl.doSelect(querysql);
		Object[] obj = list.get(0);
		int idx = Integer.parseInt(obj[3].toString());
		return idx;
	}
	
	public synchronized int generateReviewIdx(){
		String querysql = "select type,name,base_idx,max_idx from " + idtable + " where type=" + ID_TYPE_REVIEW;
		String updatesql = "update " + idtable + " set max_idx=max_idx+1 where type=" + ID_TYPE_REVIEW;
		
		int ures = ddl.doUpdate(updatesql);
		if (ures<1){
			MyLog.logger.warn("update review idx failed.");
		}
		
		List<Object[]> list = ddl.doSelect(querysql);
		Object[] obj = list.get(0);
		int idx = Integer.parseInt(obj[3].toString());
		return idx;
	}
	
	public synchronized int generateSponsorIdx(){
		String querysql = "select type,name,base_idx,max_idx from " + idtable + " where type=" + ID_TYPE_SPONSOR;
		String updatesql = "update " + idtable + " set max_idx=max_idx+1 where type=" + ID_TYPE_SPONSOR;
		
		int ures = ddl.doUpdate(updatesql);
		if (ures<1){
			MyLog.logger.warn("update sponsor idx failed.");
		}
		
		List<Object[]> list = ddl.doSelect(querysql);
		Object[] obj = list.get(0);
		int idx = Integer.parseInt(obj[3].toString());
		return idx;
	}
	
	public synchronized int generateUserIdx(){
		String querysql = "select type,name,base_idx,max_idx from " + idtable + " where type=" + ID_TYPE_USER;
		String updatesql = "update " + idtable + " set max_idx=max_idx+1 where type=" + ID_TYPE_USER;
		
		int ures = ddl.doUpdate(updatesql);
		if (ures<1){
			MyLog.logger.warn("update user idx failed.");
		}
		
		List<Object[]> list = ddl.doSelect(querysql);
		Object[] obj = list.get(0);
		int idx = Integer.parseInt(obj[3].toString());
		return idx;
	}
	
	@Override
	public int generateTransactionIdx() {
		String querysql = "select type,name,base_idx,max_idx from " + idtable + " where type=" + ID_TYPE_TRANSACTION;
		String updatesql = "update " + idtable + " set max_idx=max_idx+1 where type=" + ID_TYPE_TRANSACTION;
		
		int ures = ddl.doUpdate(updatesql);
		if (ures<1){
			MyLog.logger.warn("update transaction idx failed.");
		}
		
		List<Object[]> list = ddl.doSelect(querysql);
		Object[] obj = list.get(0);
		int idx = Integer.parseInt(obj[3].toString());
		return idx;
	}
	
	public static void main(String[] args){
		//Load configurations
		WizardManager.initWizard();
		
		IndexBasedIdBuilder ib = new IndexBasedIdBuilder();
		System.out.println(ib.generateUserIdx());
		
		WizardManager.shutdownWizard();
		
		
	}


}
