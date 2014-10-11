package ua.etaxi.core.database;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import ua.etaxi.core.domain.Permission;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static ua.etaxi.core.domain.builders.PermissionBuilder.createPermission;

/**
 * Created by Viktor on 11/10/2014.
 */
public class PermissionDAOImplTest extends DatabaseIntegrationTest {

    @Autowired
    private EntityRepository entityRepository;


    @Test
    public void testCreate() {
        savePermissionToDB("admin");
    }

    @Test
    public void testGetById() {
        final Permission permission = savePermissionToDB("admin");
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permissionFromDB = entityRepository.getById(Permission.class, permission.getPermissionId());
                assertThat(permissionFromDB.getPermissionName(), is("admin"));
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
        final Permission permission = savePermissionToDB("admin");
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permissionFromDB = entityRepository.getRequired(Permission.class, permission.getPermissionId());
                assertThat(permissionFromDB.getPermissionName(), is("admin"));
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

    @Test
    public void testUpdate() {
        final Permission permission = savePermissionToDB("admin");
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permissionFromDB = entityRepository.getRequired(Permission.class, permission.getPermissionId());
                permissionFromDB.setPermissionName("user");
            }
        });
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permissionFromDB = entityRepository.getRequired(Permission.class, permission.getPermissionId());
                assertThat(permissionFromDB.getPermissionName(), is("user"));
            }
        });
    }

    @Test
    public void testDelete() {
        final Permission permission = savePermissionToDB("admin");
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permissionFromDB = entityRepository.getRequired(Permission.class, permission.getPermissionId());
                entityRepository.delete(permissionFromDB);
            }
        });
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permissionFromDB = entityRepository.getById(Permission.class, permission.getPermissionId());
                assertThat(permissionFromDB, is(nullValue()));
            }
        });
    }

    private Permission savePermissionToDB(final String permissionName) {
        final AtomicReference<Permission> permissionRef = new AtomicReference<>();
        doInTransaction(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                Permission permission = createPermission().withName(permissionName).build();
                assertThat(permission.getPermissionId(), is(nullValue()));
                entityRepository.create(permission);
                assertThat(permission.getPermissionId(), is(notNullValue()));
                permissionRef.set(permission);
            }
        });
        return permissionRef.get();
    }

}
