package com.berinle.cflinuxfs3.barclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class BarClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarClientApplication.class, args);
	}

}

@RestController
class ServiceInstanceRestController {

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(
			@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

	@GetMapping("/{applicationName}")
	public String serviceUrl(@PathVariable String applicationName) {
		List<ServiceInstance> list = discoveryClient.getInstances(applicationName);
		if (list != null && list.size() > 0 ) {
			return String.valueOf(list.get(0).getUri());
		}
		return null;
	}

	@GetMapping("/test")
	public String test() {
		return "hello world";
	}
}