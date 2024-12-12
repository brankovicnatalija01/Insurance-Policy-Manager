package integration;

import com.fioneer.homework.Application;
import com.fioneer.homework.model.InsuranceProduct;
import com.fioneer.homework.repository.InsuranceProductRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author natalija
 */

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class InsuranceProductIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InsuranceProductRepository insuranceProductRepository;

    @BeforeEach
    void setup() {
        insuranceProductRepository.deleteAll();
    }

    @Test
    void testCreateProduct_Success() throws Exception {
        String productJson = """
            {
                "name": "Test Product",
                "type": "Health",
                "coverages": [
                    {
                        "name": "Basic Coverage",
                        "premium": 100.0,
                        "benefitAmount": 5000.0
                    }
                ]
            }
        """;

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Test Product"));

        // Verify that the product was saved
        assertEquals(1, insuranceProductRepository.count());
    }
    
    @Test
    void testGetAllProducts_Success() throws Exception {
        InsuranceProduct product1 = new InsuranceProduct();
        product1.setName("Test Product 1");
        product1.setType("Health");
        insuranceProductRepository.save(product1);

        InsuranceProduct product2 = new InsuranceProduct();
        product2.setName("Test Product 2");
        product2.setType("Car");
        insuranceProductRepository.save(product2);

        mockMvc.perform(get("/api/products/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("Test Product 1"))
            .andExpect(jsonPath("$[1].name").value("Test Product 2"));
    }

    @Test
    void testGetProductDetails_Success() throws Exception {
        InsuranceProduct product = new InsuranceProduct();
        product.setName("Test Product");
        product.setType("Health");
        InsuranceProduct savedProduct = insuranceProductRepository.save(product);

        mockMvc.perform(get("/api/products/" + savedProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        InsuranceProduct product = new InsuranceProduct();
        product.setName("Old Product Name");
        product.setType("Health");
        InsuranceProduct savedProduct = insuranceProductRepository.save(product);

        String updatedProductJson = """
            {
                "name": "Updated Product Name",
                "type": "Health"
            }
        """;

        mockMvc.perform(put("/api/products/" + savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedProductJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Product Name"));

        // Verify the update in the database
        InsuranceProduct updatedProduct = insuranceProductRepository.findById(savedProduct.getId()).orElse(null);
        assertNotNull(updatedProduct);
        assertEquals("Updated Product Name", updatedProduct.getName());
    }
    
    @Test
    void testDeleteProduct_Success() throws Exception {
        InsuranceProduct product = new InsuranceProduct();
        product.setName("Test Product");
        product.setType("Health");
        InsuranceProduct savedProduct = insuranceProductRepository.save(product);

        mockMvc.perform(delete("/api/products/" + savedProduct.getId()))
            .andExpect(status().isNoContent());

        // Verify that the product was deleted
        assertEquals(0, insuranceProductRepository.count());
    }
    
    @Test
    void testSearchProducts_Success() throws Exception {
        InsuranceProduct product1 = new InsuranceProduct();
        product1.setName("Health Product");
        product1.setType("Health");
        insuranceProductRepository.save(product1);

        InsuranceProduct product2 = new InsuranceProduct();
        product2.setName("Car Insurance");
        product2.setType("Car");
        insuranceProductRepository.save(product2);

        mockMvc.perform(get("/api/products/search").param("name", "Health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].name").value("Health Product"));
    }
    
}
