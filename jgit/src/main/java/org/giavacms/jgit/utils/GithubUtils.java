package org.giavacms.jgit.utils;

import java.io.IOException;

import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

public class GithubUtils
{

   public static String createGithubRepository(String username, String password, String name, String description,
            String homePage, boolean isPublic, String organization, String team)
   {
      GHOrganization github;
      try
      {
         // github = GitHub.connectUsingPassword("fiorenzino@gmail.com", "d10b01a");
         // GHRepository repo = github.createRepository(
         // "ff1", "ff1repository",
         // "http://giavacms.org/", true/* public */);
         github = GitHub.connectUsingPassword(username, password).getOrganization(organization);
         GHRepository repo = github.createRepository(
                  name, description, homePage, team, isPublic);
         System.out.println("REPO: " + repo.getName() + " - url: " + repo.getUrl());
         System.out.println("REPO: " + repo.getDescription() + " - url: " + repo.getGitTransportUrl());
         System.out.println("REPO: " + repo.getDescription() + " - url: " + repo.gitHttpTransportUrl());
         return repo.gitHttpTransportUrl();
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return null;
   }
}
