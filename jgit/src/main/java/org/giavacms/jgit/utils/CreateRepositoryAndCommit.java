package org.giavacms.jgit.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;

public class CreateRepositoryAndCommit
{

   public static String JBOSS7 = "jboss7";
   public static String JBOSSEAP = "jbosseap";
   public static String WILDFLY = "wildfly";

   static String BASE_FOLDER = "/home/fiorenzo/workspace-all/workspace-giavacms/creator/jgit/src/main/resources/";
   static String OPENSHIFT_FOLDER = "/.openshift";
   static String DEPLOYMENTS_FOLDER = "/deployments";
   static String README_FILE = "/README.md";

   private static String[] getFolders(String container)
   {
      Map<String, String[]> folders = new HashMap<String, String[]>();
      folders.put(JBOSS7, new String[] {
               BASE_FOLDER + JBOSS7 + OPENSHIFT_FOLDER,
               BASE_FOLDER + JBOSS7 + DEPLOYMENTS_FOLDER,
               BASE_FOLDER + JBOSS7 + README_FILE });
      folders.put(JBOSSEAP, new String[] {
               BASE_FOLDER + JBOSSEAP + OPENSHIFT_FOLDER,
               BASE_FOLDER + JBOSSEAP + DEPLOYMENTS_FOLDER,
               BASE_FOLDER + JBOSSEAP + README_FILE });
      folders.put(WILDFLY, new String[] {
               BASE_FOLDER + WILDFLY + OPENSHIFT_FOLDER,
               BASE_FOLDER + WILDFLY + DEPLOYMENTS_FOLDER,
               BASE_FOLDER + WILDFLY + README_FILE });

      if (folders.containsKey(container))
      {
         return folders.get(container);
      }
      return null;
   }

   public static boolean createProjectFolder(String where)
   {
      File whereFile = new File(where);
      if (whereFile.exists())
         whereFile.delete();
      whereFile.mkdir();
      return true;
   }

   public static boolean createCommitAndPush(String remoteRepository, String localRepository, String username,
            String repositoryName)
   {
      Repository localRepo = null;
      try
      {
         localRepo = new FileRepository(localRepository + "/.git");
         Git git = new Git(localRepo);
         localRepo.create();

         // git@github.com:fiorenzino/project1388055793926.git
         StoredConfig config = localRepo.getConfig();
         config.setString("remote", "origin", "pushurl", "git@github.com:" + username + "/" + repositoryName + ".git");
         config.save();
         git.add().addFilepattern(".").call();

         git.commit()
                  .setMessage("first commit")
                  .call();
         git.push()
                  .call();
         localRepo.close();
         return true;
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      catch (NoFilepatternException e)
      {
         e.printStackTrace();
      }
      catch (GitAPIException e)
      {
         e.printStackTrace();
      }

      return false;
   }

   public static boolean copyFolders(String where, String container)
   {
      String[] folders = getFolders(container);
      try
      {
         for (String folder : folders)
         {
            File file = new File(folder);
            if (file.isDirectory())
               FileUtils.copyDirectoryToDirectory(new File(folder), new File(where));
            else
            {
               FileUtils.copyFileToDirectory(file, new File(where));
            }
         }
         return true;
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return false;
   }

   public static void main(String[] args)
   {

   }

}
