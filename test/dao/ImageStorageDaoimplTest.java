package dao;

import com.omnie.model.KunderaEntityManageFactory;
import com.omnie.model.mongo.dao.ImageStorageDao;
import com.google.inject.Guice;
import org.junit.After;
import org.junit.Before;
import play.Application;
import play.ApplicationLoader;
import play.Environment;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.guice.GuiceApplicationLoader;
import play.test.Helpers;

import javax.inject.Inject;

/**
 * Created by harmeet on 29/12/16.
 */
public class ImageStorageDaoimplTest {

    @Inject
    private Application fakeApplication;
    @Inject
    private ImageStorageDao imageStorageDao;
    @Inject
    private KunderaEntityManageFactory entityManageFactory;

    @Before
    public void setup() {
        fakeApplication = Helpers.fakeApplication();

        GuiceApplicationBuilder builder =
                new GuiceApplicationLoader().builder(new ApplicationLoader.Context(Environment.simple()));
        Guice.createInjector(builder.applicationModule()).injectMembers(this);

        Helpers.start(fakeApplication);
    }

    @After
    public void destroy() {
        Helpers.stop(fakeApplication);
    }


}
