# Template guide for creating an RPA JAX-RS web service
##### Document and template created by Josh Sample
##### Document version 1.0 - 4/8/2022

Note: You may want to view this document in a Markdown viewer. I wish Eclipse had one installed but it doesn't, formating looks a lot better in a Markdown viewer.
___

## The goal of this README:
### by the end of this read me, you should have 
- A deployed callable web service configured to your projects needs.
- Swagger functionality defined and verified.
- Other steps created like creating an RTC component, Jenkins job set up, and UrbanCode Deploy configured.

This README assumes you already have created a repository workspace and the component for your project. If you haven't, you can refer to section 5 of this document. Once you've copied this component, RPAWebServiceTemplate, into your workspace, be sure to disconnect it by going to **Pending Changes > {your repository workspace} > right click RPAWebServiceTemplate component > Unload**. We don't want to deliver any changes to that component! We want to deliver changes you've made to this template to another component. This purely exists just for devs to copy into their own local workspace, make changes for their specific web service, then deliver to the appropriate component. The idea is that this will cut down on the time it takes to do an initial delivery to production, by only changing a few things you can get this up and callable in no time.

## 1. First things first: download a plugin
### **If you already have the plugin installed, you can skip to section 2.**
Download the **Buildship Gradle Integration 3.0** plugin in Eclipse/RAD. Go to Help > Eclipse Marketplace... and search for it. Our web services use Gradle. Gradle is a powerful build automation tool that lets us compile, package into WAR and EAR files, and even run tests. Check out the bottom of this document for a list of references, the documentation of Gradle is there.

You will also probably need to install a version of Gradle to your local machine. While you can use any version of Gradle you wish, the version of Gradle that Jenkins uses as of writing this is Gradle 5.4.1. Keep that in mind in case you use something that is compatible with newer versions of Gradle and your build is failing when running your Jenkins job. To switch to your local Gradle installation, follow these steps:
1. In Eclipse/RAD, click Window > Preferences
2. In the "type filter text" input box, type "Gradle"
3. Click on "Gradle" (not the "Experimental features" option below it) and under Gradle distribution click Local installation directory
4. Click "Browse..." and point to your local Gradle installation file location
5. Click Apply and OK

## 2. Time to edit files
Once you've got the Gradle plugin installed and you've got it pointing to your local installation, now we can start editing some files that will be needed for your project. What we'll be changing is basically changing names of certain variables and properties that are currently specific to the name **RPAWebServiceTemplate**. You'll want to change any instance of this name to ***what your project name is***. For example, let's say you're making a web service called **RPAEmailerService** (already exists), any instance of the String **RPAWebServiceTemplate** you'll want to change to **RPAEmailerService**. 
### **Before anything, I recommend editing the package root (the top level directory of all the files and folders, the project name) to your web service name. Eclipse/RAD may ask if you want to update references, you'll want to.**
___

First, let's make changes to our .gradle files located under our project root directory. I have comments in both of the files that explain what you should change and some important information regarding dependencies. Feel free to delete these comments once you read them, they aren't necessarily important beyond this template.
- Edit `settings.gradle`, change `rootProject.name` and `include`
- Edit `build.gradle`, specifically the `war` and `ear` sections of the build script

Once you edit those two files, next we'll want to edit our deployment descriptors. These are very necessary for the WAR and EAR files, basically they describe the classes, resources, and configuration of the web application and how the server uses them. Very important. We have deployment descriptors in two locations, let's edit the files under the META-INF folder first. 

___

To change the META-INF files, we'll want to expand out *src/main/application* and you should see the META-INF folder there. Expand that out and you'll see two files. We only need to edit `application.xml` though. No need to edit the `MANIFEST.MF` file.
- Open up `application.xml` and make changes to the `<web-uri>` tag and the `<context-root>` tag 

The next deployment descriptors we need to edit are the files under the WEB-INF folder. Under *src/main/webapp* you'll see the WEB-INF folder.
- Edit `web.xml`, you'll see several instances of **RPAWebServiceTemplate**, change these to your project name.
  - Note: Under the `<security-constraint>` tag you will see instances of `/resources/Temp` under things like the `<description>` tag or the `<url-pattern>` tag. I will provide more information about what these paths are further in the README, but for now keep these in mind as you will want to change them once you define your own paths.

`web.xml` is an important file, you can define security constraints and servlets here. In this template, I have BASIC authentication defined and a Swagger servlet defined. More on those topics later in this README, but while we're editing it I felt it necessary to state this information now.

Since we're deploying our web applications to a WebSphere server, the two IBM xml files under WEB-INF are important. We only need to edit one of these though.
- Open `ibm-web-ext.xml` and edit the `<context-root>` tag to your project name.

The other IBM xml file, `ibm-web-bnd.xml` we won't have to edit. But it is important to note that the virtual host is defined there.
___
Now that we have the build files and the deployment descriptors edited, let's go and edit some of the actual code itself.

Under *src/main/java/net/abcbs/eae/jaxrs* you'll see 4 files. When further developing your web service, these are bound to get changed, editted, possibly deleted, and you'll probably add even more files to this package. But these 4 will at least be ground work for you to do an initial deployment to all server environments.
- Open up `Constants.java` and edit the `SYSTEM_NAME` variable to your projects name. These String constants in this file are used with the `IsSharedApplicationDataObject` variable in your resource file. That object can retrieve various database information. I left that variable there in case your web service will interact with databases, but if it won't then you don't necessarily need it. It can still be important to keep this constants file around since you may need constants throughout your process.
- In `JsonPayload.java` you won't need to change anything in this file, but I do want to note you may want to change/create a different object for your payload response depending on your projects needs once you start programming your web service. For example, in the **RPAEmailerService** web service I used a DTO (data transfer object) for my JSON response. But in the **RPAQueryProcessorService** web service I used a JsonPayload object not too different from this one here for all my responses. It all depends on what your project needs.
- In `TemplateApplication.java` you will want to edit this Java files name to be something more in line with your projects name. You'll want to keep it as `Application.java` but with a different first word. You will want to change line 15, the `classes.add(TemplateResource.class);` section to whatever you name your resources file. Which is discussed in the next bullet point.
  - Note: The `@ApplicationPath` annotation is a part of what builds your URL. `"resources"` has been used in all of our web services, so I suggest you don't change it. But it is noteworthy.
  - Note: You can also have this file defined as a servlet in `web.xml` as opposed to having it defined programmatically. For an example of this, check out the **References** section at the bottom of the document. RPAEmailerService has a servlet defined instead of the programmatic application file.
- In `TemplateResource.java` you'll want to rename this file to something more in line with your projects name, like the `Application` file that we just discussed. Once you rename it, be sure to go back to the `Application` file and edit line 15 to be your resource file name as previously mentioned in the last bullet point. Here in the resource file, this is where the bulk of your code will be. You've probably already noticed there is a lot here. Let's break it down so it's more digestable.
  - The `@Path` annotation on line 21 you'll probably want to change to something more specific for your application. This is something pretty important, this annotation defines a part of the URL for your web service. Once you define it, you'll want to update the `web.xml` in the security constraints so you can have your resources properly secured. Again, we use BASIC authentications for these web services, but potentially down the line we may update these to use something like an API refresh token, or OAuth. But currently all the web services use BASIC.
  - The Swagger annotations: there is a lot of them. Swagger is a pretty great tool, and we can have it do automatic documentation of our web services. There are a few annotations that we can use to further define and improve our documentation, in this template there are `@OpenAPIDefinition`, `@Server`, `@SecurityScheme`, `@Operation`, `@SecurityRequirement`, `@ApiResponse`, `@Content`, and `@Schema`. You'll want to change the `@Server` tag to your project name, not **RPAWebServiceTemplate**, but you don't necessarily need to change anything else right now, you may want to edit these once you start adding functionality to your web service. And I recommend adding Swagger annotations throughout your code once you start programming, as this will improve your Swagger documentation. Check out the **References** section to see the Swagger Core documentation, that's the implementation of Swagger we use.
  - The two `@GET` methods are going to be two endpoints you can hit. You don't necessarily need to change any code within these methods, but knowing how they work is important.

___
Now, one last thing I want to mention before we close off this section. Under *src/main/resources* you'll see a file called `openapi-configuration.json`. This file adds some metadata with the Swagger file generated from Swagger Core, our Swagger implementation we use. You'll want to edit the `title` and `description` parts of this file. The Swagger Core documentation you can view at **References** has further explanation of this configuration file.

Once you edit these files, you should be good to start building and testing out your web service on a server!
## 3. Local deployment 
So now that we've gotten all the files that we needed to update, we're good to try a local deployment out and test our web service. Here's what we need to do to get that going.
1. Start off by doing File > New > Enterprise Application Project (may need to click 'Other' and search for it). Name your project `{your project name}EAR`. This will create a project that will let you run it on a local server.
2. Next, right click your project and click Gradle > Refresh Gradle Project. This will run your build script and download your dependencies. If you've been getting errors even after making the above changes, this should get rid of those.
3. Now, on the Servers tab (if you don't have it, go to Window > Show View > Other > type in Servers and double click on it) right click your WebSphere Application Server and click 'Add and Remove...' and add your EAR to the server. You'll want to add the EAR you created in step one, the one created from step 2 is actually one generated by your build script. The reason you want to do the first step is so we can add that to Eclipse/RAD and be able to test everthing locally. Even though this isn't the EAR going to the actual application servers, it's a necessary step for us to test things out locally.

Once you do these steps, the EAR of your project should be deployed on your local server. The way you can test this out is by going to localhost:9080/{your project name}/resources/{your defined path in the resources file}. So for example, if doing the RPAWebServiceTemplate web service, it would be *localhost:9080/RPAWebServiceTemplate/resources/Temp*. This URL is generated by {server}/{project name}/{application path defined by either servlet or application file}/{path defined in resources}. I highly recommend downloading an API testing tool like Postman to test out your web service. 
## 4. The next steps...
From here, you have completed everything this template sets out to do! You've got a working Java web service. From here, you'll want to deliver your change set to a component. This part of the document assumes you already have a component created, a Jenkins job set up, and everything created and configured in UrbanCode Deploy. If not, we'll touch on that in the next section.

- To deliver your change sets to a component in RTC, you'll want to right click your project and go to Team > Share Project > Jazz Source Control > Expand out your repository workspace, which should have the component you want to deliver your changes in, click the component > Finish. This will deliver your change set into a fresh, new component and your project will be shared in RTC source control.
- For future change sets you want to deliver, you'll go to the Pending Changes tab. If you don't have it, go to Window > Show View > Other > type "Pending Changes" and you should be able to add that view. From there, in your repository workspace, you should see your component with a drop down arrow. Drop down, you'll see a Outgoing folder with a black arrow facing right. Right click the Outgoing folder, and select Check-in and deliver. This will let you deliver with a comment and associate a work item to your change set.

When you deliver your change sets to a component, a subsequent Jenkins job should kick off and build your project, and then send the artifacts created to UrbanCode Deploy where they will be deployed on the servers. While this README was focused on taking the web service template and configuring it for your project, I'll include brief explanations on how to do the other parts of getting an initial delivery in the next section.

## 5. Extra stuff
So far, this document has gone on with the assumption you've already accomplished the other parts of doing an initial deployment. Things like creating the RTC component, creating the Jenkins job, creating the component and application in UrbanCode Deploy, etc. But I thought it would still be useful to cover these topics, albeit in a brief manner. So this section will cover everthing else, not just changing the template. 
___
### Creating a Repository Workspace
Creating a repository workspace is the first of many steps taken when starting our development. To create a repository workspace, you'll first need to connect to the Jazz repository in Eclipse/RAD. This requires the RTC plugin. Installation instructions for the plugin are at our shared file directory, under Development/Java. When you have that installed, you should be able to log in and connect to the Enterprise Automation Engineering Project Area. From there, go to the Team Artifacts view (if you don't have it, go to Window > Show View > Other > type Team Artifacts and double click it). Expand out Source Control, and right click EAE General Development Stream. From there click New > Repository Workspace, and name it something like *{your user id} {project name} Workspace*. From here, don't worry about changing any options as you click through Next until you see the Components to Add section. Here, you'll be able to select the components you want your repository workspace to connect to. 

Another thing to note about repository workspaces, is that they are necessary for Jenkins to be able to detect code changes in the component. In the Jenkins jobs we create, we have a variable that is the repository workspace name that Jenkins detects code changes in. When setting up the Jenkins job we'll have to create this repository workspace. We follow the same steps, except when you create the new repository workspace you'll want the name of the repository to follow the naming convention in our Jenkins jobs. It's very important both the repository workspace name and the Jenkins job `rep.name` variable are identical. Once they are, you can click Next, and then Next until you're on the Read Access Permission page. You'll want to scope the repository workspace to Project Area, not Private. Once you create the repository workspace, you don't have to load in the Eclipse projects. Just create the repository workspace and you'll be finished.
___
### Creating the Component
In the Team Artifacts view, you can expand out the project area, expand out Source Control, right click EAE General Development Stream, and click Open. From here, you should see a "New" button. Click on that, and create your component. There isn't a strict naming convention here, but I recommend naming your component either the same as your project name, or naming it "{your project name} Component". You'll want this component in both the EAE General Stage Stream and the EAE Stage Stream. You can simply open these streams (right click them > Open) and click Add... > Component in another repository workspace or stream and select your component from the EAE General Development Stream.
___
### Creating Jenkins Job
You'll have to create your Jenkins job in two locations, under Claims and Financial Systems/EAE/Development and Claims and Financial Systems/EAE/Stage. My suggestion for creating your Jenkins jobs? Simply click New Item, name your job the same name as your project, and down at the bottom of the job you should see a Copy from section. Copy your job from **RPAEmailerService** and you'll be set. All you'll need to change is the `rep.name ` variable. Currently it's set to `EAE  - Build - Jenkins - dev - RPAEmailerService`, you'll want to change this to match the naming convention but instead of RPAEmailerService you'll want it to be your project name. This string is a repository workspace in RTC that Jenkins scans for changes. It's how we're able to automate the building and delivery of our projects. Refer to the above **Creating a Repository Workspace** section for instructions on creating a repository workspace for Jenkins.
___
### UrbanCode Deploy Configuration
In UrbanCode Deploy (UCD) you'll want to go to the Components tab and select Create Component. You'll want to use the IsShared Web Service Component Template, name your component after the naming convention used (EAE - {your project name} Component) and hit save. Then go to the Applications tab and click Create Application and click From Application Template. Select IsShared Web Application Template, name it following the naming convention (EAE - {your project name} Application). Hit next, select your component, and then hit next and save. From here, open up your application and open up the Development, Stage, and Production environments. In Configuration > Environment Properties you should see Component Properties, and a roleMappingString variable you can configure. Paste this string in to that box: `AuthenticatedUsers->Allow Access To everyone(no)->Allow Access To authenticated users(yes)-> ->CN=Robotic Process Automation,OU=ABCBS User Groups,dc=abcbs,dc=net|CN=EnterpriseAutomationEngineeringTeam,OU=Distribution Groups,OU=ABCBS User Groups,DC=ABCBS,DC=NET|CN=EnterpriseAutomationEngineering-PPM,OU=ServiceNow Assignment Groups,OU=ABCBS User Groups,DC=ABCBS,DC=NET`

This will allow the web service to be mapped with specific user groups. AuthenticatedUsers comes from the defined group in the `web.xml` file. This role mapping string should authenticate everyone in the EAE team, and all the RPAUSER accounts. To add these role mappings to the web service, you'll want to go to your application and Request Process > under process drop down select Deploy Application With Role Mappings > hit next > uncheck Only Deploy Changed Versions > select a component by clicking Add... > hit next > next > Submit Deployment. This will then deploy the application with your role mapping string. 
___
### Swagger Documentation
This section is dedicated to a brief overview of how we can use Swagger. So our Swagger implementation that we use is Swagger Core. Swagger Core provides automatic resolution for JAX-RS resources into an OpenAPI definition. This will create a JSON and YAML file that you can then view from our defined endpoint (this is defined in `web.xml`, that's the Swagger servlet) {server name}/{project name}/openapi. This will expose the OpenAPI definition in JSON format. For an example, you can check out the [RPAEmailerService]("https://isshareddev.abcbs.net/RPAEmailerService/openapi"). You can also view the OpenAPI definition in YAML format by going to {server name}/{project name}/openapi/openapi.yaml - [example with emailer service]("https://isshareddev.abcbs.net/RPAEmailerService/openapi/openapi.yaml"). We can further define and expand our documentation and our OpenAPI definition by adding Swagger annotations within our project, you can see and example of that in the template, and I touched on it in section 2. Personally, I think you should check out the Swagger Core annotations section in the Swagger Core documentation as that allows us to really fine tune and have really great and robust documenation. When we have our generated JSON or YAML file, we can view those files with a Swagger UI. While it would be lovely to have a Swagger UI available from our web service, Swagger Core only generates the JSON and YAML OpenAPI definition document. There are ways to incorporate the UI into a project, but none have been implemented for us as of writing this document. I highly encourage looking through the Swagger Core documentation linked in the references, this will provide further explanations and detail.

___
Whew! Okay. With all of that, you should finally have everything set up/configured/deployed/callable with your web service. You should now be good to deploy to all environments and call your base URL and get a response. And when you've got that, you should be able to start adding functionality! Any references or important external information I have in the References section. Happy coding!

##### By the way, once you have this README in your own project you can absolutely delete it. It is tailored for RPAWebServiceTemplate after all.
___
## References
- IsShared Server Environment URLs: 
  - Prod: https://isshared.abcbs.net
  - Stage: https://issharedstg.abcbs.net
  - Development: https://isshareddev.abcbs.net
- [Gradle Documentation]("https://docs.gradle.org/current/userguide/userguide.html")
- [IBM JAX-RS Documentation]("https://www.ibm.com/docs/en/was-nd/8.5.5?topic=services-developing-jax-rs-web-applications")
- [JAX-RS Tutorial YouTube Playlist]("https://www.youtube.com/playlist?list=PLqq-6Pq4lTTZh5U8RbdXq0WaYvZBz2rbn")
- [Swagger Core Documentation]("https://github.com/swagger-api/swagger-core/wiki/Swagger-2.X---Getting-started")
- [Rational Team Concert URL]("https://jazz.abcbs.net/")
- [Jenkins URL]("http://jenkins.abcbs.net:8080/")
- [UrbanCode Deploy URL]("https://ucdspapp01.abcbs.net:8443/")
- [UrbanCode Deploy Role Mapping String Documentation]("https://www.urbancode.com/plugindoc/websphere-application-server-deployment#map_users_and_groups_to_roles_for_application")
- For an example of a servlet definition instead of having an application file, check out [RPAEmailerService's web.xml]("https://jazz.abcbs.net/ccm/web/projects/Enterprise%20Automation%20Engineering%20Project%20Area#action=com.ibm.team.scm.browseElement&workspaceItemId=_vdKw8AJrEeumg6qX3NIWug&componentItemId=_j08WcOQAEeu5kvNo_IxKYQ&itemType=com.ibm.team.filesystem.FileItem&itemId=_AI2wQLWwEeyTse0y2n5HrQ")
- [Download Postman, an API testing tool that I use]("https://www.postman.com/downloads/")