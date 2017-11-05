# In a nutshell
![Jenkins Editor Logo](https://github.com/de-jcup/eclipse-jenkins-editor/raw/master/jenkins-editor-plugin/html/images/jenkins-editor-logo.png)

Is a eclipse plugin to edit jenkins files named as `Jenkinsfile` . See https://jenkins.io/doc/book/pipeline/jenkinsfile/ for more information about syntax etc.

## Installation by eclipse marketplace
The editor is currently in a BETA phase and will become available at
https://marketplace.eclipse.org/content/jenkins-editor

## Features
- Syntax highlighting (customizable) + default colors for dark theme
- Outline + Quick outline (CTRL + o)
- Simple groovy script validation support
- Bracket switching (CTRL + p)
- Block commenting (CTRL + 7)
- Simple groovy syntax validation (local)
- Validate by Jenkins Linter (remote)
- ...

## More information
Please look at https://github.com/de-jcup/eclipse-jenkins-editor/wiki for more information

## Development
### Setup 
- Checkout EGradle projects in same Workspace (as dependency for plugin development)
- Checkout Jenkins Editor projects
### Build
- At the moment there exists a gradle setup, but it's not really used (maybe in future for tests)
  and can be ignored currently
- To build the editor plugin, please open "jenkins-editor-updatesite/site.xml"
  with eclipse site editor and build Jenkins editor feature by pressing "Build" button inside.
### Execute
- Simply start as Eclipse Application by a new launch configuration in eclipse 

## License
Jenkins Editor is under Apache 2.0 license (http://www.apache.org/licenses/LICENSE-2.0)

<a href="http://with-eclipse.github.io/" target="_blank">
<img alt="with-Eclipse logo" src="http://with-eclipse.github.io/with-eclipse-0.jpg" />
</a>

