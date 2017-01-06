package com.nixsolutions.bondarenko.study.soap.hello;


import com.nixsolutions.bondarenko.study.soap.hello.model.Goods;

import javax.jws.WebService;

@WebService(endpointInterface = "com.nixsolutions.bondarenko.study.soap.hello.HelloSoap",
        serviceName = "HelloSoap")
public class HelloSoapImpl implements HelloSoap {

    @Override
    public String testService() {
        return "Hello from SOAP Webservice!";
    }

    @Override
    public String sayHelloTo(String text) {
        return "Hello to " + text;
    }

    @Override
    public Goods getGoods() {
        Goods goods = new Goods();
        goods.setId(1);
        goods.setName("Some goods test name");
        return goods;
    }
}