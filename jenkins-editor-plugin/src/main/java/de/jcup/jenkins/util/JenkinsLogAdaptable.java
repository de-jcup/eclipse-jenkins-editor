/*
 * Copyright 2017 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
 package de.jcup.jenkins.util;

/**
 * Own adaptable - we do not use egradle logadapter interface but our own
 * to have no dependency in gradle build to egradle. The only dependencies
 * to egradle exist in eclipse plugin build
 * @author albert
 *
 */
public interface JenkinsLogAdaptable {
	
	void logInfo(String info);

	void logWarning(String warning);

	void logError(String error, Throwable t);
}
