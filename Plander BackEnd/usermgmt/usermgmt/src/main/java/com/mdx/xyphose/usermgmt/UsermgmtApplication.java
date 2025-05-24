package com.mdx.xyphose.usermgmt;

import com.mdx.xyphose.usermgmt.entity.enums.Status;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class UsermgmtApplication {

	public static void main(String[] args) {

		SpringApplication.run(UsermgmtApplication.class, args);
	}



}
