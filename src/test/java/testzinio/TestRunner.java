/**
 * TestRunner.java.
 *
 * TestRunner Class run the test in the features folder.
 * The following parameters are configurable:
 *   - Tags: select the tag test that you want execute.
 *   - Features: select the folder where the features are.
 *   - Glue: select the folder where the StepDefinition file is.
 *
 * @author Jose Antonio Torre
 * @version 1.0
 */

package testzinio;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        tags = "@All",
        features = "src/test/resources/features/",
        glue = {"testzinio/stepDefinition"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)

/**
 * TestRunner Class
 */
public class TestRunner {

}
