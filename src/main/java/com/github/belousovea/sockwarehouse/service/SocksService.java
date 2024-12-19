package com.github.belousovea.sockwarehouse.service;

import com.github.belousovea.sockwarehouse.exception.*;
import com.github.belousovea.sockwarehouse.mapper.SocksMapper;
import com.github.belousovea.sockwarehouse.model.dto.SocksDto;
import com.github.belousovea.sockwarehouse.model.dto.SocksFilterDto;
import com.github.belousovea.sockwarehouse.model.entity.Socks;
import com.github.belousovea.sockwarehouse.repository.SocksRepository;
import com.github.belousovea.sockwarehouse.spec.SocksSpec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class SocksService implements GoodsService<SocksDto, SocksFilterDto> {

    private final SocksRepository socksRepository;
    private final SocksMapper socksMapper;

    private final int batchSize = 1000;

    public SocksService(SocksRepository socksRepository, SocksMapper socksMapper) {
        this.socksRepository = socksRepository;
        this.socksMapper = socksMapper;
    }


    @Override
    public void income(SocksDto dto) {
        Socks socks = socksMapper.toEntity(dto);
        socksRepository.save(socks);
    }

    @Override
    public void outcome(SocksDto dto) {
        List<Socks> socks = socksRepository.findByColorAndCottonContent(dto.getColor(), dto.getCottonContent());
        if (socks.stream().mapToInt(Socks::getQuantity).sum() < dto.getQuantity()) {
            throw new NotEnoughGoodsException(dto);
        }
        int quantity = dto.getQuantity();
        for (Socks sock : socks) {
            if (sock.getQuantity() < quantity) {
                quantity = sock.getQuantity();
                sock.setQuantity(0);
            } else {
                sock.setQuantity(sock.getQuantity() - quantity);
                break;
            }
        }

        batchSave(socks, batchSize);

    }

    @Override
    public long countFilteredGoods(SocksFilterDto filterDto) {

        return socksRepository.sumQuantity(getSocksSpecification(filterDto));
    }

    @Override
    public Collection<SocksDto> findFilteredGoods(SocksFilterDto filterDto) {
        return socksRepository.findAll(getSocksSpecification(filterDto)).stream().map(socksMapper::toDto).toList();
    }
    @Override
    public void update(long id, SocksDto dto) {
        if (socksRepository.existsById(id)) {
            socksRepository.save(socksMapper.toEntity(dto));
        } else {
            throw new IllegalRequestParameterException("Id", id);
        }

    }

    @Override
    public void batchInsert(MultipartFile file) {

    }



    private void batchSave(List<Socks> socks, int batchSize) {
        int size = socks.size();
        for (int i = 0; i < size; i += batchSize) {
            int toIndex = Math.min(size, i + batchSize);
            socksRepository.saveAll(socks.subList(i, toIndex));
        }
    }

    private Specification<Socks> getSocksSpecification(SocksFilterDto filterDto) {
        Specification<Socks> spec = Specification.where(SocksSpec.hasColor(filterDto.getColor()));

        if (filterDto.getOperator() != null
                && filterDto.getCottonContent() != null
                && filterDto.getCottonContentMin() != null
                && filterDto.getCottonContentMax() != null) {
            throw new TooManyFilterParametersException();
        }
        if (filterDto.isSortedByColor() && filterDto.isSortedByCottonContent()) {
            throw new TooManySortingParametersException();
        }

        if (filterDto.getOperator() != null && filterDto.getCottonContent() != null) {
            spec.and(SocksSpec.hasCottonContent(filterDto.getCottonContent(), filterDto.getOperator()));

        } else if (filterDto.getCottonContentMin() != null && filterDto.getCottonContentMax() != null) {
            if (filterDto.getCottonContentMin() <= filterDto.getCottonContentMax()) {
                spec.and(SocksSpec.hasCottonContentRange(filterDto.getCottonContentMin(), filterDto.getCottonContentMax()));
            } else {
                throw new IllegalFilterRangeException(filterDto.getCottonContentMin(), filterDto.getCottonContentMax());
            }
        } else {
            throw new IllegalFilterParametersException();
        }

        if (filterDto.isSortedByColor()) {
            spec.and(SocksSpec.sortByColor());
        }
        if (filterDto.isSortedByCottonContent()) {
            spec.and(SocksSpec.sortByCottonContent());
        }
        return spec;
    }
}
