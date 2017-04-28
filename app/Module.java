import com.discoperi.model.mongo.dao.EmployeeDao;
import com.discoperi.model.mongo.dao.impl.EmployeeDaoImpl;
import com.discoperi.model.service.EmployeeService;
import com.discoperi.model.service.impl.EmployeeServiceImpl;
import com.google.inject.AbstractModule;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        bind(EmployeeService.class ).to( EmployeeServiceImpl.class );
        bind(EmployeeDao.class ).to( EmployeeDaoImpl.class );
    }

}
