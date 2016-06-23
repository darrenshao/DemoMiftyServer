package club.jmint.demo.mifty.server;

import club.jmint.demo.mifty.service.OrderServiceImpl;
import club.jmint.demo.mifty.service.PaymentServiceImpl;
import club.jmint.demo.mifty.service.ProductServiceImpl;
import club.jmint.demo.mifty.service.UserServiceImpl;
import club.jmint.demo.mifty.service.gen.OrderService;
import club.jmint.demo.mifty.service.gen.PaymentService;
import club.jmint.demo.mifty.service.gen.ProductService;
import club.jmint.demo.mifty.service.gen.UserService;
import club.jmint.mifty.server.MiftyServer;


public class DemoMiftyServer {

	public static void main(String [] args) {
		
		MiftyServer ms = new MiftyServer();
		ms.setName("DemoMiftyServer");
		
		//register service interface
		ms.add("com.twohalf.mifty.service.gen.UserService", 
				new UserService.Processor<UserService.Iface>(new UserServiceImpl()));
		ms.add("com.twohalf.mifty.service.gen.ProductService", 
				new ProductService.Processor<ProductService.Iface>(new ProductServiceImpl()));
		ms.add("com.twohalf.mifty.service.gen.OrderService", 
				new OrderService.Processor<OrderService.Iface>(new OrderServiceImpl()));
		ms.add("com.twohalf.mifty.service.gen.PaymentService", 
				new PaymentService.Processor<PaymentService.Iface>(new PaymentServiceImpl()));

		//start
		ms.startup();
	}
}
