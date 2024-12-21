package com.github.belousovea.sockwarehouse.controller;

import com.github.belousovea.sockwarehouse.model.dto.SocksDto;
import com.github.belousovea.sockwarehouse.model.dto.SocksFilterDto;
import com.github.belousovea.sockwarehouse.service.GoodsService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
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
    public long sumFilteredSocks(@ModelAttribute SocksFilterDto socksFilterDto) {
        return goodsService.countFilteredGoods(socksFilterDto);
    }

    //В условиях этого эндпойнта нет, конечно, но не тогда непонятно, где должна быть реализована сортировка
    //Никакие другие эндпойнты, указанные в задании не предполагают возвращения коллекции.
    @GetMapping("/list")
    public Collection<SocksDto> findFilteredSocks(@ModelAttribute SocksFilterDto socksFilterDto) {
        return goodsService.findFilteredGoods(socksFilterDto);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @Valid @RequestBody SocksDto socksDto) {
        goodsService.update(id, socksDto);
    }

    @PostMapping(value = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void batchInsert(@RequestParam("file") MultipartFile file) {
        goodsService.batchInsert(file);
    }

}
