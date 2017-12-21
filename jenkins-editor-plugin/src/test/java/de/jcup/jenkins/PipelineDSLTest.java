package de.jcup.jenkins;

import org.junit.Test;

public class PipelineDSLTest {

	@Test
	public void plugin_will_find_all_icons_given_by_path_of_dsl() throws Exception {
		for (PipelineDSL dslEntry: PipelineDSL.values()){
			PluginResourceLoader.assertFileInPluginExists(dslEntry.getRelativePathInsidePlugin());
		}
	}

}
