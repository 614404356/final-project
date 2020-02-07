package com.dasi.graduation.finalproject.controller;

import com.dasi.graduation.finalproject.base.ResponseEnvelope;
import com.dasi.graduation.finalproject.entity.Food;
import com.dasi.graduation.finalproject.entity.Tips;
import com.dasi.graduation.finalproject.repository.TipsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangqi
 * @Date 2019/4/13 0013 17:55
 * @Description
 */
@RestController
@RequestMapping("tips")
public class TipsController {

    @Autowired
    private TipsRepository tipsRepository;

    @GetMapping("findTips")
    public ResponseEntity<ResponseEnvelope<List<Tips>>> findByType(){
        List<Tips> tipsList = tipsRepository.findAll();
        ResponseEnvelope<List<Tips>> envelope = new ResponseEnvelope<>(tipsList);
        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @GetMapping("findByType")
    public ResponseEntity<ResponseEnvelope<List<Tips>>> findByType(@RequestParam String type){
        List<Tips> tipsList = tipsRepository.findByType(type);
        ResponseEnvelope<List<Tips>> envelope = new ResponseEnvelope<>(tipsList);
        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @PostMapping("delete")
    public ResponseEntity<ResponseEnvelope<Void>> delete(@RequestParam String id){
        Tips tips = tipsRepository.findById(Long.valueOf(id));
        tipsRepository.delete(tips);
        ResponseEnvelope<Void> envelope = new ResponseEnvelope<>();
        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseEnvelope<Void>> insert(@RequestParam String title,@RequestParam String body,@RequestParam String type){
        Tips tips = new Tips();
        tips.setTitle(title);
        tips.setBody(body);
        tips.setType(type);
        tipsRepository.save(tips);
        ResponseEnvelope<Void> envelope = new ResponseEnvelope<>();
        return new ResponseEntity<>(envelope, HttpStatus.OK);
    }

}
