package ua.etaxi.core.database;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import ua.etaxi.config.CoreConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Viktor on 11/10/2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreConfig.class)
@TransactionConfiguration(transactionManager = "hibernateTX", defaultRollback = false)
public abstract class DatabaseIntegrationTest {

    @Autowired
    @Qualifier("hibernateTX")
    protected PlatformTransactionManager transactionManager;

    @Autowired
    protected SessionFactory sessionFactory;


    @Before
    public void cleanDatabase() {
        List<String> tableNames = getTableNames();
        for(final String tableName : tableNames) {
            doInTransaction(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    Session session = sessionFactory.getCurrentSession();
                    String queryString = "DELETE FROM " + tableName;
                    Query query = session.createSQLQuery(queryString);
                    query.executeUpdate();
                }
            });
        }
    }

    private List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();

        // real tables
        tableNames.add("permissions");

        return tableNames;
    }

    protected void doInTransaction(TransactionCallbackWithoutResult callbackWithoutResult) {
        TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.execute(callbackWithoutResult);
    }

}
