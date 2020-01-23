package at.ac.tuwien.big.ame.somqm.server;

import at.ac.tuwien.big.ame.somqm.server.integration.BaseIntegrationTest;
import at.ac.tuwien.big.ame.somqm.server.unit.BaseUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {BaseUnitTest.class, BaseIntegrationTest.class})
public class ServerApplicationTests {

}
