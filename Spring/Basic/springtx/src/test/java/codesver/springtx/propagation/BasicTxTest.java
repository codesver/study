package codesver.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.*;

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

    @Test
    void innerCommit() {
        log.info("Outer transaction start");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Outer is new transaction={}", outer.isNewTransaction());

        log.info("Inner transaction start");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Inner is new transaction={}", inner.isNewTransaction());

        log.info("Inner transaction commit");
        txManager.commit(inner);

        log.info("Outer transaction commit");
        txManager.commit(outer);
    }

    @Test
    void outerRollback() {
        log.info("Outer transaction start");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Inner transaction start");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("Inner transaction commit");
        txManager.commit(inner);

        log.info("Outer transaction rollback");
        txManager.rollback(outer);
    }

    @Test
    void innerRollback() {
        log.info("Outer transaction start");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Inner transaction start");
        TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("Inner transaction rollback");
        txManager.rollback(inner); // rollback-only marking

        log.info("Outer transaction commit");
        assertThatThrownBy(() -> txManager.commit(outer)).isInstanceOf(UnexpectedRollbackException.class);
    }

    @Test
    void innerRollbackRequiresNew() {
        log.info("Outer transaction start");
        TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
        log.info("Outer is new transaction={}", outer.isNewTransaction());

        log.info("Inner transaction start");
        DefaultTransactionAttribute definition = new DefaultTransactionAttribute();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus inner = txManager.getTransaction(definition);
        log.info("Inner is new transaction={}", inner.isNewTransaction());

        log.info("Inner transaction rollback");
        txManager.rollback(inner);

        log.info("Outer transaction commit");
        txManager.commit(outer);
    }
}
