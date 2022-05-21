package guru.bonacci.heroesadmin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class AppHeroesAdmin {

	public static void main(String[] args) {
		SpringApplication.run(AppHeroesAdmin.class, args);
	}
}
