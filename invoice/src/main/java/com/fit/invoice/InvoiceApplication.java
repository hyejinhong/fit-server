package com.fit.invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class InvoiceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(InvoiceApplication.class);
		app.addListeners(new ApplicationPidFileWriter());
		app.run(args);
	}
}
