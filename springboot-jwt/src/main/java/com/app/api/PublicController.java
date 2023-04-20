package com.app.api;

import com.app.api.model.BaseResponse;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class PublicController {

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    @RequiresAuthentication
    public BaseResponse join(@RequestParam("imei") String imei) {
        BaseResponse result = new BaseResponse();
        result.setSuccess(true);
        return result;
    }
}
