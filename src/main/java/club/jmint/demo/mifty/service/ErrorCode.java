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

class ECBuilder{
	final public static int EC_MAIN_HEADER = 0xfe;	//applied from Crossing Administrator
	final public static int EC_SUB_HEADER = 0x00;	//applied from Crossing Administrator
	public static int ERRORCODE(int ec){
		int errcode = EC_MAIN_HEADER;
		int subheader = EC_SUB_HEADER;
		errcode = errcode << 24;
		subheader = subheader << 16;
		return (errcode | subheader | ec);
	}
	public static int ERRORCODE(int sub_header, int ec){
		int errcode = EC_MAIN_HEADER;
		int subheader = sub_header;
		errcode = errcode << 24;
		subheader = subheader << 16;
		return (errcode | subheader | ec);
	}
	public static int ERRORCODE(int main_header, int sub_header, int ec){
		int errcode = main_header;
		int subheader = sub_header;
		errcode = errcode << 24;
		subheader = subheader << 16;
		return (errcode | subheader | ec);
	}	
};

public enum ErrorCode {
	//Error Codes Definition
	OBJECT_NOT_EXIST(ECBuilder.ERRORCODE(0x01, 0x0001), "Object not exist."),
	OBJECT_ALREADY_EXISTS(ECBuilder.ERRORCODE(0x01, 0x0002), "Object already exists."),
	DB_OPERATION_FAILED(ECBuilder.ERRORCODE(0x01, 0x0003), "Database operation failed."),
	
	USER_PASSWORD_INCORRECT(ECBuilder.ERRORCODE(0x01, 0x0001), "User or password incorrect."),


	
	TODO(ECBuilder.ERRORCODE(0x01, 0xffff), "To be defined.");

	
	//DO NOT CHANGE BELLOWS.
	private int code;
	private String info;

	private ErrorCode(int code, String info){
		this.code = code;
		this.info = info;
	}

	public int getCode() {
		return code;
	}

	public String getInfo() {
		return info;
	}
}

