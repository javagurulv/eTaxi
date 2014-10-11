package ua.etaxi.core.database;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import ua.etaxi.core.domain.Permission;

import java.util.concurrent.atomic.AtomicLong;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Viktor on 11/10/2014.
 */
public class PermissionDAOImplTest extends DatabaseIntegrationTest {

    @Autowired
    private EntityRepository entityRepository;


    @Test
    public void testCreate() {
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permission = new Permission();
                permission.setPermissionName("admin");
                assertThat(permission.getPermissionId(), is(nullValue()));
                entityRepository.create(permission);
                assertThat(permission.getPermissionId(), is(notNullValue()));
            }
        });
    }

    @Test
    public void testGetById() {
        final AtomicLong permissionId = new AtomicLong();
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permission = new Permission();
                permission.setPermissionName("admin");
                entityRepository.create(permission);
                permissionId.set(permission.getPermissionId());
            }
        });
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permission = entityRepository.getById(Permission.class, permissionId.get());
                assertThat(permission.getPermissionName(), is("admin"));
            }
        });
    }

    @Test
    public void testGetById_EntityNotFound() {
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permission = entityRepository.getById(Permission.class, Long.MAX_VALUE);
                assertThat(permission, is(nullValue()));
            }
        });
    }

    @Test
    public void testGetRequired() {
        final AtomicLong permissionId = new AtomicLong();
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permission = new Permission();
                permission.setPermissionName("admin");
                entityRepository.create(permission);
                permissionId.set(permission.getPermissionId());
            }
        });
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permission = entityRepository.getRequired(Permission.class, permissionId.get());
                assertThat(permission.getPermissionName(), is("admin"));
            }
        });
    }

    @Test(expected = RuntimeException.class)
    public void testGetRequired_EntityNotFound() {
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                entityRepository.getRequired(Permission.class, Long.MAX_VALUE);
            }
        });
    }

}
