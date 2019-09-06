package cn.itsource.hrm.controller;

import cn.itsource.hrm.util.RedisUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class RedisController{
    @PostMapping
    public void set(@RequestParam("key")String key, @RequestParam("value")String value) {
        if (value.equals("[]"))
            RedisUtils.INSTANCE.getSource().setex(key,5*60, value);
        else
            RedisUtils.INSTANCE.set(key,value);
    }
    @GetMapping
    public String get(@RequestParam("key")String key) {
        return RedisUtils.INSTANCE.get(key);
    }
}