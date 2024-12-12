/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integration;

import com.fioneer.homework.Application;
import com.fioneer.homework.model.Coverage;
import com.fioneer.homework.model.InsuranceProduct;
import com.fioneer.homework.model.Policy;
import com.fioneer.homework.repository.CoverageRepository;
import com.fioneer.homework.repository.InsuranceProductRepository;
import com.fioneer.homework.repository.PolicyRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author natalija
 */

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class PolicyIntegrationTest {
    
   @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private InsuranceProductRepository insuranceProductRepository;

    @Autowired
    private CoverageRepository coverageRepository;

    private InsuranceProduct savedProduct;
    private Coverage savedCoverage;

    @BeforeEach
    void setup() {
        policyRepository.deleteAll();
        coverageRepository.deleteAll();
        insuranceProductRepository.deleteAll();

        // Create a test InsuranceProduct
        InsuranceProduct product = new InsuranceProduct();
        product.setName("Test Product");
        product.setType("Health");
        savedProduct = insuranceProductRepository.save(product);

        // Create a test Coverage
        Coverage coverage = new Coverage();
        coverage.setName("Basic Coverage");
        coverage.setPremium(100.0);
        coverage.setBenefitAmount(5000.0);
        coverage.setInsuranceProduct(savedProduct);
        savedCoverage = coverageRepository.save(coverage);
    }

    @Test
    void testGetAllPolicies_Success() throws Exception {
        // Insert test data
        Policy policy1 = new Policy();
        policy1.setFirstName("John");
        policy1.setLastName("Doe");
        policy1.setTotalPremium(500.0);
        policy1.setInsuranceProduct(savedProduct);
        policyRepository.save(policy1);

        Policy policy2 = new Policy();
        policy2.setFirstName("Jane");
        policy2.setLastName("Smith");
        policy2.setTotalPremium(700.0);
        policy2.setInsuranceProduct(savedProduct);
        policyRepository.save(policy2);

        mockMvc.perform(get("/api/policies/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].firstName").value("John"))
            .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void testSearchPolicies_Success() throws Exception {
        // Insert test data
        Policy policy1 = new Policy();
        policy1.setFirstName("John");
        policy1.setLastName("Doe");
        policy1.setTotalPremium(500.0);
        policy1.setInsuranceProduct(savedProduct);
        policyRepository.save(policy1);

        Policy policy2 = new Policy();
        policy2.setFirstName("Jane");
        policy2.setLastName("Smith");
        policy2.setTotalPremium(700.0);
        policy2.setInsuranceProduct(savedProduct);
        policyRepository.save(policy2);

        mockMvc.perform(get("/api/policies/search")
                .param("firstName", "John"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    void testDeletePolicy_Success() throws Exception {
        // Insert test data
        Policy policy = new Policy();
        policy.setFirstName("John");
        policy.setLastName("Doe");
        policy.setTotalPremium(500.0);
        policy.setInsuranceProduct(savedProduct);
        Policy savedPolicy = policyRepository.save(policy);

        mockMvc.perform(delete("/api/policies/" + savedPolicy.getId()))
            .andExpect(status().isNoContent());

        // Verify the policy was deleted
        assertEquals(0, policyRepository.count());
    }
}
