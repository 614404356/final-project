package com.dasi.graduation.finalproject.controller;

import com.dasi.graduation.finalproject.base.ResponseEnvelope;
import com.dasi.graduation.finalproject.entity.Food;
import com.dasi.graduation.finalproject.entity.Life;
import com.dasi.graduation.finalproject.entity.User;
import com.dasi.graduation.finalproject.entity.UserFood;
import com.dasi.graduation.finalproject.repository.FoodRepository;
import com.dasi.graduation.finalproject.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 13:23
 * @Description
 */
@RestController
@RequestMapping("food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping("/findFoodList")
    public ResponseEntity<ResponseEnvelope<List<Food>>> findFoodList(
            @PageableDefault( sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable){
        if (pageable == null){
            pageable = PageRequest.of(0, 12, new Sort(Sort.Direction.DESC, "id"));
        } else {
            if (pageable.getPageNumber() == 0) {
                pageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
            } else {
                pageable = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(),
                        pageable.getSort());
            }
        }
        Page<Food> foodList = foodService.getPageFood(pageable);
        List<Food> list = foodList.getContent();
        ResponseEnvelope<List<Food>> envelope = new ResponseEnvelope<>(list);
        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @GetMapping("findByType")
    public ResponseEntity<ResponseEnvelope<List<Food>>> findByType(@RequestParam String type){
        List<Food> foodList = foodService.findByType(type);
        ResponseEnvelope<List<Food>> envelope = new ResponseEnvelope<>(foodList);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @GetMapping("findByNameLike")
    public ResponseEntity<ResponseEnvelope<List<Food>>> findByNameLike(@RequestParam String name){
        List<Food> foodList = foodService.findByNameLike(name);
        ResponseEnvelope<List<Food>> envelope = new ResponseEnvelope<>(foodList);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @PostMapping("foodDelete")
    public ResponseEntity<ResponseEnvelope<Void>> foodDelete(@RequestParam String id){
        foodService.deleteOne(Long.valueOf(id));
        ResponseEnvelope<Void> envelope = new ResponseEnvelope<>();
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }

    @PostMapping("insert")
    public ResponseEntity<ResponseEnvelope<Food>> insert(Food food){
        foodRepository.save(food);
        ResponseEnvelope<Food> envelope = new ResponseEnvelope<>(food);
        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @PostMapping("/updatePhoto")
    public ResponseEntity<ResponseEnvelope<String>> updatePhoto(MultipartFile file) throws IOException {
        String photo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        System.out.println(file.getOriginalFilename());
        String name = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("_"));
        System.out.println(name);
        File dir = new File("C:/upload/" + photo);
        if(!dir.getParentFile().exists()){
            dir.getParentFile().mkdirs();//创建父级文件路径
            dir.createNewFile();//创建文件
        }
        file.transferTo(dir);
        Food food = foodRepository.findByName(name);
        foodRepository.updateOne(photo,name);
        ResponseEnvelope<String> envelope = new ResponseEnvelope(food);
        return new ResponseEntity<>(envelope,HttpStatus.OK);
    }
}
