package main;

import org.tempuri.Add;
import org.tempuri.Calculator;
import org.tempuri.CalculatorSoap;

// instruction on how to generate java classes avaible on: https://www.mkyong.com/webservices/jax-ws/jax-ws-hello-world-example/   .... 2. Java Web Service Client via wsimport tool
//	example tutorial from https://www.soapui.org/soap-and-wsdl/getting-started.html
//	another useful link: http://frankhinkel.blogspot.com/2012/10/dispatching-web-service-calls-async.html
public class ToExcecute {		

	public static void main(String[] args) {
		   
		Calculator Cs = new Calculator();
		CalculatorSoap CsInterface = Cs.getCalculatorSoap();

	
		System.out.println(CsInterface.add(1, 4));
		
    }
}
