package com.course.controller;

import com.course.dao.PointDATA;
import com.course.pojo.PointObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestfulController {
    @Autowired
    private PointDATA pointDATA;

    @GetMapping(value = "/user")
    public List<PointObject> getUser(){
        return pointDATA.getAllPointObjects();
    }

    @GetMapping(value = "/user/query/{id}")
    public PointObject getUserByID(@PathVariable("id") Integer id){
        return pointDATA.getPointObjectById(id);
    }

    @PostMapping(value = "/user")
    public PointObject addUser(@RequestParam("id") int id,
                               @RequestParam("exchange") int exchange,
                               @RequestParam("grow") int grow,
                               @RequestParam("total") int total){
        PointObject pointObject = new PointObject();
        pointObject.setExchangeScore(exchange);
        pointObject.setGrowScore(grow);
        pointObject.setScoreTotal(total);
        pointObject.setId(id);
        pointDATA.setPoint(pointObject);
        return pointDATA.getPointObjectById(id);

    }

    @DeleteMapping(value="/user/{id}")
    public List<PointObject> deleteUserByID(@PathVariable("id") Integer id){
        // Note: PointDATA interface doesn't have delete method,
        // this would need to be implemented if needed
        return pointDATA.getAllPointObjects();
    }

    @PostMapping(value="/user/{id}")
    public PointObject updateUser(@PathVariable("id") Integer id,
                                  @RequestParam("grow") Integer grow,
                                  @RequestParam("exchange") Integer exchange,
                                  @RequestParam("total") Integer total){
        PointObject pointObject = pointDATA.getPointObjectById(id);
        pointObject.setId(id);
        pointObject.setScoreTotal(total);
        pointObject.setGrowScore(grow);
        pointObject.setExchangeScore(exchange);
        pointDATA.setPoint(pointObject);
        return pointDATA.getPointObjectById(id);
    }
}
