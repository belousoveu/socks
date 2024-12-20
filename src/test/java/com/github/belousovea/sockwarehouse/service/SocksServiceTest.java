package com.github.belousovea.sockwarehouse.service;

import com.github.belousovea.sockwarehouse.TestData;
import com.github.belousovea.sockwarehouse.exception.IllegalRequestParameterException;
import com.github.belousovea.sockwarehouse.exception.NotEnoughGoodsException;
import com.github.belousovea.sockwarehouse.mapper.SocksMapper;
import com.github.belousovea.sockwarehouse.model.dto.SocksDto;
import com.github.belousovea.sockwarehouse.model.entity.Socks;
import com.github.belousovea.sockwarehouse.repository.SocksRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest
//@Import(SocksService.class)
class SocksServiceTest {

    @MockitoBean
    private ExcelService excelService;

    @Autowired
    private SocksMapper socksMapper;

    @Autowired
    private SocksRepository socksRepository;

    //    @Autowired
    @Autowired
    private SocksService socksService;


    @Container
    static PostgreSQLContainer<?> cont = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void properties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", cont::getJdbcUrl);
        registry.add("spring.datasource.username", cont::getUsername);
        registry.add("spring.datasource.password", cont::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop"); // Убедитесь, что схема создается
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
    }

    @BeforeAll
    static void beforeAll() {
        cont.start();
    }

    @BeforeEach
    void setUp() {
        socksRepository.deleteAll();
    }

    @Test
    void test_income() {
        SocksDto expected = TestData.createTestSocksDto("Red", 75, 50);

        socksService.income(expected);

        List<Socks> actualList = socksRepository.findAll();

        assertEquals(1, actualList.size());
        Socks actual = actualList.get(0);
        assertEquals(expected.getColor(), actual.getColor());
        assertEquals(expected.getQuantity(), actual.getQuantity());
        assertEquals(expected.getCottonContent(), actual.getCottonContent());
    }

    @Test
    void test_outcome_success() {
        List<Socks> sockslist = new ArrayList<>();
        sockslist.add(TestData.createTestSocks("Red", 75, 50));
        sockslist.add(TestData.createTestSocks("Blue", 75, 50));
        sockslist.add(TestData.createTestSocks("Red", 75, 50));
        sockslist.add(TestData.createTestSocks("Red", 65, 50));
        socksRepository.saveAll(sockslist);

        socksService.outcome(TestData.createTestSocksDto("Red", 75, 70));

        List<Socks> actualList = socksRepository.findAll(Sort.by("id"));

        assertEquals(4, actualList.size());
        assertEquals(0, actualList.get(0).getQuantity());
        assertEquals(50, actualList.get(1).getQuantity());
        assertEquals(30, actualList.get(2).getQuantity());
        assertEquals(50, actualList.get(3).getQuantity());

    }

    @Test
    void test_outcome_notEnoughQuantity() {
        List<Socks> sockslist = new ArrayList<>();
        sockslist.add(TestData.createTestSocks("Red", 75, 50));
        sockslist.add(TestData.createTestSocks("Blue", 75, 50));
        sockslist.add(TestData.createTestSocks("Red", 75, 50));
        sockslist.add(TestData.createTestSocks("Red", 65, 50));
        socksRepository.saveAll(sockslist);

        SocksDto dto = TestData.createTestSocksDto("Red", 75, 170);

        assertThrows(NotEnoughGoodsException.class, () -> socksService.outcome(dto));
    }

    @Test
    void countFilteredGoods() {
    }

    @Test
    void findFilteredGoods() {
    }

    @Test
    void test_update_success() {
        Socks socks = TestData.createTestSocks("Red", 75, 50);
        socksRepository.save(socks);
        long id = socks.getId();

        SocksDto dto = TestData.createTestSocksDto("Blue", 85, 100);

        socksService.update(id, dto);

        Socks actual = socksRepository.findById(id).get();

        assertNotNull(actual);
        assertEquals(dto.getColor(), actual.getColor());
        assertEquals(dto.getQuantity(), actual.getQuantity());
        assertEquals(dto.getCottonContent(), actual.getCottonContent());
    }

    @Test
    void test_update_wrongId() {
        Socks socks = TestData.createTestSocks("Red", 75, 50);
        socksRepository.save(socks);

        SocksDto dto = TestData.createTestSocksDto("Blue", 85, 100);

        assertThrows(IllegalRequestParameterException.class, () -> socksService.update(0L, dto));
    }


    @Test
    void test_batchInsert_success() {
        List<Socks> expected = new ArrayList<>();
        expected.add(TestData.createTestSocks("Red", 75, 50));
        expected.add(TestData.createTestSocks("Blue", 75, 50));
        when(excelService.getSocksList(any(MultipartFile.class))).thenReturn(expected);
        MultipartFile file = TestData.getMockExcelFile(TestData.getCorrectExcelData());

        socksService.batchInsert(file);

        List<Socks> actual = socksRepository.findAll();

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getColor(), actual.get(0).getColor());
        assertEquals(expected.get(0).getQuantity(), actual.get(0).getQuantity());

    }

}