package com.mdx.xyphose.usermgmt.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {

    public String startupMessage()
    {
        return "Application started ...";
    }
}
