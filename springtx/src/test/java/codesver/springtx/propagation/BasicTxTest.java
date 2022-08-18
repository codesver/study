package codesver.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config {
        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit() {
        log.info("Transaction start");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("Transaction commit start");
        txManager.commit(status);
        log.info("Transaction commit finish");
    }

    @Test
    void rollback() {
        log.info("Transaction start");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("Transaction rollback start");
        txManager.rollback(status);
        log.info("Transaction rollback finish");
    }

    @Test
    void doubleCommit() {
        log.info("Transaction A start");
        TransactionStatus statusA = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Transaction A commit start");
        txManager.commit(statusA);

        log.info("Transaction B start");
        TransactionStatus statusB = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Transaction B commit start");
        txManager.commit(statusB);

        log.info("Transaction double commit finish");
    }

    @Test
    void doubleCommitRollback() {
        log.info("Transaction A start");
        TransactionStatus statusA = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Transaction A commit start");
        txManager.commit(statusA);

        log.info("Transaction B start");
        TransactionStatus statusB = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Transaction B rollback start");
        txManager.rollback(statusB);

        log.info("Transaction double commit&rollback finish");
    }
}
