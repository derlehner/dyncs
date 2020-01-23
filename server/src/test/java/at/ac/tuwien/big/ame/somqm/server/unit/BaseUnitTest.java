package at.ac.tuwien.big.ame.somqm.server.unit;

import at.ac.tuwien.big.ame.somqm.server.unit.dao.BaseDaoUnitTest;
import at.ac.tuwien.big.ame.somqm.server.unit.rest.BaseRestUnitTest;
import at.ac.tuwien.big.ame.somqm.server.unit.service.BaseServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {BaseDaoUnitTest.class, BaseServiceUnitTest.class, BaseRestUnitTest.class})
public abstract class BaseUnitTest {

}
