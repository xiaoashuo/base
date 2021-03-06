package com.lovecyy.controller;

import com.lovecyy.R;
import com.lovecyy.utils.MessageUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("locale")
public class LocaleController {

    @GetMapping("test")
    public R ok(String msg){
        String message = MessageUtils.getMessage(msg);
        return R.ok(message);
    }
}
