package org.giavacms.webcreator.test;

import java.util.ArrayList;
import java.util.List;

import org.giavacms.jgit.utils.CreateRepositoryAndCommit;
import org.giavacms.jgit.utils.GithubUtils;
import org.giavacms.jmvn.utils.ProjectCreator;

public class GlobalTest
{
   public static void main(String[] args)
   {
      String username = "xxxx";
      String password = "xxx";
      String repositoryName = "project" + System.currentTimeMillis();
      String description = "description: " + repositoryName;
      String homePage = "http://giavacms.org";
      String organization = "xxx";
      String team = "xxx";
      boolean isPublic = true;
      String where = "/home/fiorenzo/workspace-all/workspace-giavacms/creator/web-creator/tmp/" + repositoryName + "/";
      String pathToPomFile = "/home/fiorenzo/workspace-all/workspace-giavacms/giavacms/pom.xml";
      String container = CreateRepositoryAndCommit.JBOSS7;

      List<String> deps = new ArrayList<>();
      deps.add("org.giavacms:banner");
      deps.add("org.giavacms:base");
      deps.add("org.giavacms:cache");
      deps.add("org.giavacms:catalogue");
      deps.add("org.giavacms:company");
      deps.add("org.giavacms:compatibility");
      deps.add("org.giavacms:contactus");
      deps.add("org.giavacms:customer");

      CreateRepositoryAndCommit.createProjectFolder(where);

      String remoteRepository = GithubUtils.createGithubRepository(username, password, repositoryName, description,
               homePage,
               isPublic, organization, team);

      CreateRepositoryAndCommit.copyFolders(where, container);
      ProjectCreator.create(deps, where, pathToPomFile, container, false);
      CreateRepositoryAndCommit.createCommitAndPush(remoteRepository, where, organization, repositoryName);

   }
}
