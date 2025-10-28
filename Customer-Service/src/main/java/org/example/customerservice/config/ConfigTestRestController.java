package org.example.customerservice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/configuration_test")
@RefreshScope
public class ConfigTestRestController {
    @Value("${global.params.p1}") private String p1;
    @Value("${global.params.p2}") private String p2;
    @Autowired
    private CustomerConfigParams customerConfigParams;
    @GetMapping("/params")
    public Map<String,String> configTest() {
        return Map.of("x", p1, "y", p2);
    }

    @GetMapping("/customer_params")
    public CustomerConfigParams configTestCustomer() {
        return customerConfigParams;
    }
}
