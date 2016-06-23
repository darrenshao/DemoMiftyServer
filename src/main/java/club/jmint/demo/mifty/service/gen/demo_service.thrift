namespace java club.jmint.demo.mifty.service.gen

service UserService {
	string callProxy(1:string method, 2:string params, 3:bool isEncrypt),
   	map<string, string> passwordVerify(1:map<string, string> params),
   	map<string, string> userAdd(1:map<string, string> params),
   	map<string, string> userDelete(1:map<string, string> params),
   	map<string, string> userUpdate(1:map<string, string> params),
   	map<string, string> userInfoQuery(1:map<string, string> params),
   	map<string, string> userListQuery(1:map<string, string> params),
}

service ProductService {
	string callProxy(1:string method, 2:string params, 3:bool isEncrypt),
	map<string, string> productAdd(1:map<string, string> params),
	map<string, string> productDelete(1:map<string, string> params),
	map<string, string> productUpdate(1:map<string, string> params),
	map<string, string> productInfoQuery(1:map<string, string> params),
	map<string, string> productListQuery(1:map<string, string> params),
}

service OrderService {
	string callProxy(1:string method, 2:string params, 3:bool isEncrypt),
	map<string, string> orderCreate(1:map<string, string> params),
	map<string, string> orderDelete(1:map<string, string> params),
	map<string, string> orderUpdate(1:map<string, string> params),
	map<string, string> orderInfoQuery(1:map<string, string> params),
	map<string, string> orderListQuery(1:map<string, string> params),
}

service PaymentService {
	string callProxy(1:string method, 2:string params, 3:bool isEncrypt),
	map<string, string> payCreate(1:map<string, string> params),
	map<string, string> payDelete(1:map<string, string> params),
	map<string, string> payUpdate(1:map<string, string> params),
	map<string, string> payInfoQuery(1:map<string, string> params),
	map<string, string> payListQuery(1:map<string, string> params),
}