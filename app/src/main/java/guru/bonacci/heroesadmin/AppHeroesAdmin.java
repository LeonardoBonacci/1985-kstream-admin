package guru.bonacci.heroesadmin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class AppHeroesAdmin {

	public static void main(String[] args) {
		SpringApplication.run(AppHeroesAdmin.class, args);
	}
}
