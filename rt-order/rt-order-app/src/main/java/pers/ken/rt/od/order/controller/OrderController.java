package pers.ken.rt.od.order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <code> OrderController </code>
 * <desc> OrderController </desc>
 * <b>Creation Time:</b> 2021/10/16 22:49.
 *
 * @author _Ken.Hu
 */
@RestController
public class OrderController {
    @GetMapping(value = "/test")
    public String test(){
        return "Order Successed";
    }
}
