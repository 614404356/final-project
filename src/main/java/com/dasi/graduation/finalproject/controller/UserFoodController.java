package com.dasi.graduation.finalproject.controller;

import com.dasi.graduation.finalproject.base.ResponseEnvelope;
import com.dasi.graduation.finalproject.entity.Food;
import com.dasi.graduation.finalproject.entity.UserFood;
import com.dasi.graduation.finalproject.repository.UserFoodRepository;
import com.dasi.graduation.finalproject.service.FoodService;
import com.dasi.graduation.finalproject.service.UserFoodService;
import com.dasi.graduation.finalproject.vo.UserFoodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.*;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 13:39
 * @Description
 */
@RestController
@RequestMapping("userFood")
public class UserFoodController {

    @Autowired
    private UserFoodService userFoodService;
    @Autowired
    private UserFoodRepository userFoodRepository;
    @Autowired
    private FoodService foodService;

    @PostMapping("insert")
    public ResponseEntity<ResponseEnvelope<Void>> insert(@RequestParam String phone,
                                                         @RequestParam String body,
                                                         @RequestParam String date){
        UserFood userFood = new UserFood();
        userFood.setBody(body);
        userFood.setPhone(phone);
        userFood.setDate(date);
        userFoodRepository.save(userFood);
        ResponseEnvelope<Void> envelope = new ResponseEnvelope<>();
        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @PostMapping("deleteOne")
    public ResponseEntity<ResponseEnvelope<Void>> deleteOne(@RequestParam String id){
        userFoodService.deleteOne(Long.valueOf(id));
        ResponseEnvelope<Void> envelope = new ResponseEnvelope<>();
        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @PostMapping("deleteAll")
    public ResponseEntity<ResponseEnvelope<Void>> deleteAll(@RequestParam String phone){
        userFoodRepository.deleteAll(phone);
        ResponseEnvelope<Void> envelope = new ResponseEnvelope<>();
        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @GetMapping("findByDateLike")
    public ResponseEntity<ResponseEnvelope<List<UserFoodVo>>> findByDateLike(@RequestParam String phone,
                                                                           @RequestParam String date){
        List<UserFood> userFoodList = userFoodService.findByPhoneAndDateLike(phone,date);
        List<UserFoodVo> newUserFoodList = new ArrayList<>();
        userFoodList.forEach(userFood -> {
            String[] idsString = userFood.getBody().split(",");
            Long[] idsLong = new Long[idsString.length];
            for (int i = 0; i < idsString.length; i++) {
                idsLong[i] = Long.valueOf(idsString[i]);
            }
            List<Long> idsList = Arrays.asList(idsLong);
            List<Food> foodList = foodService.findByIdIn(idsList);
            List<String> bodyText = new ArrayList<>();
            String body = "";
            UserFoodVo userFoodVo = new UserFoodVo();
            for (int i =0;i<foodList.size();i++){
                bodyText.add(foodList.get(i).getName());
                body = body + "," + foodList.get(i).getName();
            }
            userFoodVo.setBodyText(body);
            userFoodVo.setId(userFood.getId());
            userFoodVo.setDate(userFood.getDate());
            newUserFoodList.add(userFoodVo);
        });

        ResponseEnvelope<List<UserFoodVo>> envelope = new ResponseEnvelope<>(newUserFoodList);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

//    @GetMapping("getPageListLike")
//    public ResponseEntity<ResponseEnvelope<Page<UserFood>>> getPageListLike(
//            @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
//            @RequestParam Map<String, String> param) throws Exception {
//        if (pageable == null){
//            pageable = PageRequest.of(0, 10, new Sort(Sort.Direction.DESC, "id"));
//        } else {
//            if (pageable.getPageNumber() == 0) {
//                pageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
//            } else {
//                pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(),
//                        pageable.getSort());
//            }
//        }
//        Page<UserFood> page = userFoodService.getPageListLike(param, pageable);
//        ResponseEnvelope<Page<UserFood>> envelope = new ResponseEnvelope<>(page);
//        return new ResponseEntity<>(envelope, HttpStatus.OK);
//    }

    @GetMapping("findByPhone")
    public ResponseEntity<ResponseEnvelope<List<UserFoodVo>>> findByPhone(@RequestParam String phone){
        List<UserFood> userFoodList = userFoodService.findByPhone(phone);
        List<UserFoodVo> newUserFoodList = new ArrayList<>();
        userFoodList.forEach(userFood -> {
            String[] idsString = userFood.getBody().split(",");
            Long[] idsLong = new Long[idsString.length];
            for (int i = 0; i < idsString.length; i++) {
                idsLong[i] = Long.valueOf(idsString[i]);
            }
            List<Long> idsList = Arrays.asList(idsLong);
            List<Food> foodList = foodService.findByIdIn(idsList);
            List<String> bodyText = new ArrayList<>();
            String body = "";
            UserFoodVo userFoodVo = new UserFoodVo();
            for (int i =0;i<foodList.size();i++){
                bodyText.add(foodList.get(i).getName());
                body = body + "," + foodList.get(i).getName();
            }
            userFoodVo.setBodyText(body);
            userFoodVo.setId(userFood.getId());
            userFoodVo.setDate(userFood.getDate());
            newUserFoodList.add(userFoodVo);
        });
        ResponseEnvelope<List<UserFoodVo>> envelope = new ResponseEnvelope<>(newUserFoodList);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @GetMapping("findById")
    public ResponseEntity<ResponseEnvelope<Map<String,Double>>> findById(@RequestParam String id){
        UserFood userFood = userFoodRepository.getUserFoodOne(Long.valueOf(id));
        String[] ids = userFood.getBody().split(",");
        List<Long> idList = new ArrayList<>();
        for (String s : ids){
            idList.add(Long.valueOf(s));
        }
        System.out.println(idList);
        List<Food> foodList = foodService.findByIdIn(idList);
        Map<String,Double> map = new HashMap<>();
        Double power = 0D;
        Double protein = 0D;
        Double fat = 0D;
        for (Food f : foodList){
            power = power + Double.parseDouble(f.getPower());
            protein = protein + Double.parseDouble(f.getProtein());
            fat = fat + Double.parseDouble(f.getFat());
        }
        map.put("power",power);
        map.put("protein",protein);
        map.put("fat",fat);
        ResponseEnvelope<Map<String,Double>> envelope = new ResponseEnvelope<>(map);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }


    @GetMapping("getHistoryChart")
    public ResponseEntity<ResponseEnvelope<List<Map<String,Object>>>> getHistoryChart(@RequestParam String phone,@RequestParam String date) {
        List<UserFood> userFoodList = userFoodService.findByPhoneAndDateLike(phone,date);
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (UserFood userFood : userFoodList)  {
            String[] ids = userFood.getBody().split(",");
            List<Long> idList = new ArrayList<>();
            for (String s : ids){
                idList.add(Long.valueOf(s));
            }
            List<Food> foodList = foodService.findByIdIn(idList);
            Map<String,Object> map = new HashMap<>();
            Double power = 0D;
            Double protein = 0D;
            Double fat = 0D;
            for (Food f : foodList){
                power = power + Double.parseDouble(f.getPower());
                protein = protein + Double.parseDouble(f.getProtein());
                fat = fat + Double.parseDouble(f.getFat());
            }
            map.put("power",power);
            map.put("protein",protein);
            map.put("fat",fat);
            map.put("date",userFood.getDate());
            mapList.add(map);
        }
        ResponseEnvelope<List<Map<String,Object>>> envelope = new ResponseEnvelope<>(mapList);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }
}
