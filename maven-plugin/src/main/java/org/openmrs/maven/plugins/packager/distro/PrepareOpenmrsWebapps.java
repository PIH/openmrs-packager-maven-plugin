/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.maven.plugins.packager.distro;

import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.openmrs.maven.plugins.packager.Plugins;

/**
 * The purpose of this Mojo is to package up an OpenMRS distribution into a Zip artifact
 */
@Mojo(name = "assemble-openmrs-webapps", defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class PrepareOpenmrsWebapps extends AbstractPackagerDistroMojo {

	@Parameter(defaultValue = "openmrs_webapps")
	String openmrsWebappsDir;

	/**
	 * @throws MojoExecutionException if an error occurs
	 */
	public void execute() throws MojoExecutionException {
		getLog().info("Preparing OpenMRS Webapps");

		File targetDir = new File(artifactDir, openmrsWebappsDir);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}

		executeMojo(
				Plugins.MAVEN_DEPENDENCY_PLUGIN,
				goal("copy-dependencies"),
				configuration(
						element("excludeTransitive", "true"),
						element("useBaseVersion", "true"),
						element("outputDirectory", targetDir.getAbsolutePath()),
						element("includeArtifactIds", "openmrs-webapp")
				),
				getMavenExecutionEnvironment()
		);
	}
}