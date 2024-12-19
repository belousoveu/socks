package com.github.belousovea.sockwarehouse.controller;

import com.github.belousovea.sockwarehouse.model.dto.SocksDto;
import com.github.belousovea.sockwarehouse.model.dto.SocksFilterDto;
import com.github.belousovea.sockwarehouse.service.GoodsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/socks")
public class SocksController {

    private final GoodsService goodsService;

    public SocksController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }


    @PostMapping("/income")
    public void income(@RequestBody SocksDto socksDto) {
        goodsService.income(socksDto);
    }


    @PostMapping("/outcome")
    public void outcome(@RequestBody SocksDto socksDto) {
        goodsService.outcome(socksDto);
    }

    @GetMapping
    public int findFilteredSocks(@RequestBody SocksFilterDto socksFilterDto) {
        return goodsService.countFilteredGoods(socksFilterDto);
    }

    @PutMapping("/api/socks/{id}")
    public void update(@PathVariable long id, @RequestBody SocksDto socksDto) {
        goodsService.update(id, socksDto);
    }

    @PostMapping("/api/socks/batch")
    public void batchInsert(@RequestParam MultipartFile file) {
        goodsService.batchInsert(file);
    }

}
