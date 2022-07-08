package testzinio;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        tags = "@Test",
        features = "src/test/resources/features/",
        glue = {"testzinio/stepDefinition"},
        plugin = {"pretty"}
)
public class TestRunner {

}
