package ua.etaxi.core.database;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import ua.etaxi.core.domain.Permission;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Viktor on 11/10/2014.
 */
public class PermissionDAOImpl extends DatabaseIntegrationTest {

    @Autowired
    private EntityRepository entityRepository;


    @Test
    public void testCreate() {
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permission = new Permission();
                permission.setPermissionName("admin");
                entityRepository.create(permission);
                assertThat(permission.getPermissionId(), is(notNullValue()));
            }
        });
    }

}
