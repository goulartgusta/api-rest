package br.com.anhembi.apiresttests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApiApplicationTestIntegration {

    @Test
    void main() {
        ApiApplication.main(new String[] {});
    }
}
