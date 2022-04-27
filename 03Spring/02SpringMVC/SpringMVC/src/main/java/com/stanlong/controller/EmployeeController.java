package com.stanlong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmployeeController {

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String queryAll(){
        return "success";
    }

    @RequestMapping(value = "/employee/1", method = RequestMethod.DELETE)
    public String deleteById(){
        return "success";
    }

    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd(){
        return "success";
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public String save(){
        return "success";
    }

    @RequestMapping(value = "/employee/2", method = RequestMethod.GET)
    public String queryById(){
        return "success";
    }

    @RequestMapping(value = "/employee/3", method = RequestMethod.POST)
    public String update(){
        return "success";
    }
}
