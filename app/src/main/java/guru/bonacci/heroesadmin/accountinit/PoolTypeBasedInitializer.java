package guru.bonacci.heroesadmin.accountinit;

import java.math.BigDecimal;

import guru.bonacci.heroesadmin.domain.Pool;

public interface PoolTypeBasedInitializer {

  BigDecimal determineStartAmount(Pool pool);
}
