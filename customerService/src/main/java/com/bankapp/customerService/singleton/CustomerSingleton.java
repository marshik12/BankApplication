package com.bankapp.customerService.singleton;

public class CustomerSingleton {
    private static CustomerSingleton instance;

    private CustomerSingleton(){}

    public static synchronized CustomerSingleton getInstance() {
        if(instance == null){
            instance = new CustomerSingleton();
        }
        return instance;
    }
}
