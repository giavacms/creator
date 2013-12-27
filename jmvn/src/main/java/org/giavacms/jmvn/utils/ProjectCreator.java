package org.giavacms.jmvn.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.exporter.ExplodedExporter;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.persistence10.PersistenceDescriptor;
import org.jboss.shrinkwrap.descriptor.api.persistence10.PersistenceUnit;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class ProjectCreator
{
   static String LIB_FOLDER = "lib/";
   static String DEPLOYMENTS_FOLDER = "deployments/";

   public static boolean create(List<String> deps, String where, String pathToPomFile, String container,
            boolean zipFolder)
   {
      enrichDeps(container, deps);

      where = where + DEPLOYMENTS_FOLDER;
      if (zipFolder)
         where = where + "ROOT.war";

      File whereFile = new File(where);
      if (whereFile.exists())
         whereFile.delete();
      if (!zipFolder)
         whereFile.mkdir();
      WebArchive war = ShrinkWrap.create(WebArchive.class, "ROOT.war");

      File[] files = null;
      if (pathToPomFile != null && !pathToPomFile.trim().isEmpty())
      {
         files = Maven.resolver().loadPomFromFile(pathToPomFile)
                  .resolve(deps).withTransitivity().asFile();
      }
      else
      {
         files = Maven.resolver()
                  .resolve(deps).withTransitivity().asFile();
      }

      for (File file : files)
      {
         war.addAsLibraries(file);
      }
      List<String> jars = getJar(files, deps);
      war.addAsWebInfResource("common/jboss-web.xml", "jboss-web.xml")
               .addAsWebInfResource("common/web.xml", "web.xml")
               .addAsResource(new StringAsset(createPersistence(jars)), "META-INF/persistence.xml");
      if (!zipFolder)
      {
         war.as(ExplodedExporter.class).exportExploded(
                  whereFile);
      }
      else
      {
         war.as(ZipExporter.class).exportTo(whereFile);
      }
      return true;
   }

   private static List<String> getJar(File[] files, List<String> deps)
   {
      List<String> jars = new ArrayList<>();
      for (File file : files)
      {
         if (file.getAbsolutePath().contains("/giavacms/"))
         {
            String name = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf("/") + 1);
            for (String dep : deps)
            {
               if (withJar(dep) && name.contains(dep.replace("org.giavacms:", "")))
               {
                  jars.add(name);
                  break;
               }
            }
         }
      }
      return jars;
   }

   private static String createPersistence(List<String> jars)
   {
      if (jars != null && !jars.isEmpty())
      {
         PersistenceUnit<PersistenceDescriptor> persistence = Descriptors.create(PersistenceDescriptor.class)
                  .createPersistenceUnit();
         persistence.getOrCreateProperties().createProperty().name("hibernate.dialect")
                  .value("org.hibernate.dialect.MySQL5InnoDBDialect");
         persistence.getOrCreateProperties().createProperty().name("hibernate.connection.useUnicode")
                  .value("true");
         persistence.getOrCreateProperties().createProperty().name("hibernate.connection.characterEncoding")
                  .value("UTF-8");
         persistence.getOrCreateProperties().createProperty().name("hibernate.hbm2ddl.auto")
                  .value("update");

         persistence.name("MysqlDS")
                  .jtaDataSource("java:jboss/datasources/MysqlDS");
         for (String jar : jars)
         {
            persistence.jarFile(LIB_FOLDER + jar);
         }
         return persistence.up().exportAsString();
      }
      return null;
   }

   private static boolean withJar(String moduleName)
   {

      List<String> jarsByModule = new ArrayList<>();
      jarsByModule.add("org.giavacms:banner");
      jarsByModule.add("org.giavacms:base");
      jarsByModule.add("org.giavacms:catalogue");
      jarsByModule.add("org.giavacms:company");

      jarsByModule.add("org.giavacms:contactus");
      jarsByModule.add("org.giavacms:customer");
      jarsByModule.add("org.giavacms:exhibition");
      jarsByModule.add("org.giavacms:faq");
      jarsByModule.add("org.giavacms:gallery");
      jarsByModule.add("org.giavacms:githubcontent");
      jarsByModule.add("org.giavacms:instagram");
      jarsByModule.add("org.giavacms:insurance-claim");
      jarsByModule.add("org.giavacms:message");

      jarsByModule.add("org.giavacms:paypal-rs");
      jarsByModule.add("org.giavacms:paypalweb");
      jarsByModule.add("org.giavacms:people");
      jarsByModule.add("org.giavacms:richcontent");
      jarsByModule.add("org.giavacms:scenario");
      if (jarsByModule.contains(moduleName))
         return true;
      return false;
   }

   private static void enrichDeps(String container, List<String> deps)
   {
      deps.add("org.giavacms.common:giavacms-common-base");
      deps.add("org.giavacms.common:giavacms-common-jpa");
      deps.add("org.giavacms.common:giavacms-common-rewrite");
      deps.add("org.giavacms.common:giavacms-common-web");
      deps.add("commons-lang:commons-lang");
      deps.add("commons-io:commons-io");
      deps.add("org.primefaces:primefaces");
      if (container != null && !container.isEmpty() && container.equals("JBOSS7"))
      {
         deps.add("org.giavacms:openshift-jboss7");
      }
   }

   public static void main(String[] args)
   {
      String pathToPomFile = "/home/fiorenzo/workspace-all/workspace-giavacms/giavacms/pom.xml";
      List<String> deps = new ArrayList<>();

      deps.add("org.giavacms:banner");
      deps.add("org.giavacms:base");
      deps.add("org.giavacms:cache");
      deps.add("org.giavacms:catalogue");
      deps.add("org.giavacms:company");
      deps.add("org.giavacms:compatibility");
      deps.add("org.giavacms:contactus");
      deps.add("org.giavacms:customer");

      create(deps, "/home/fiorenzo/workspace-all/workspace-giavacms/creator/jmvn/out/", pathToPomFile, "JBOSS7", true);
   }
}
