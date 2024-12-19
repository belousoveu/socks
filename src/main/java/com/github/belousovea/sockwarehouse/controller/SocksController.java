package com.github.belousovea.sockwarehouse.controller;

import com.github.belousovea.sockwarehouse.model.dto.SocksDto;
import com.github.belousovea.sockwarehouse.model.dto.SocksFilterDto;
import com.github.belousovea.sockwarehouse.service.GoodsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/api/socks")
public class SocksController {

    private final GoodsService<SocksDto, SocksFilterDto> goodsService;

    public SocksController(GoodsService<SocksDto, SocksFilterDto> goodsService) {
        this.goodsService = goodsService;
    }


    @PostMapping("/income")
    public void income(@Valid @RequestBody SocksDto socksDto) {
        goodsService.income(socksDto);
    }


    @PostMapping("/outcome")
    public void outcome(@Valid @RequestBody SocksDto socksDto) {
        goodsService.outcome(socksDto);
    }

    @GetMapping
    public long sumFilteredSocks(@Valid @RequestBody SocksFilterDto socksFilterDto) {
        return goodsService.countFilteredGoods(socksFilterDto);
    }

    @GetMapping("/list")
    public Collection<SocksDto> findFilteredSocks(@Valid @RequestBody SocksFilterDto socksFilterDto) {
        return goodsService.findFilteredGoods(socksFilterDto);
    }

    @PutMapping("/api/socks/{id}")
    public void update(@PathVariable long id, @Valid @RequestBody SocksDto socksDto) {
        goodsService.update(id, socksDto);
    }

    @PostMapping("/api/socks/batch")
    public void batchInsert(@RequestParam MultipartFile file) {
        goodsService.batchInsert(file);
    }

}
