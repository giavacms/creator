import org.giavacms.jgit.utils.CreateRepositoryAndCommit;
import org.giavacms.jgit.utils.GithubUtils;

public class TestCreateCopyFolders
{
   public static void main(String[] args)
   {
      String username = "fiorenzino";
      String password = "d10b01a";
      String repositoryName = "project" + System.currentTimeMillis();
      String description = "description: " + repositoryName;
      String homePage = "http://giavacms.org";
      String team = "giavacms";
      String organization = "giavacms";
      boolean isPublic = true;
      String where = "/home/fiorenzo/workspace-all/workspace-giavacms/creator/jgit/out/" + repositoryName;
      String remoteRepository = GithubUtils.createGithubRepository(username, password, repositoryName, description,
               homePage,
               isPublic, organization, team);
      String[] folders = new String[] {
               "/home/fiorenzo/workspace-all/workspace-giavacms/creator/jgit/src/main/resources/.openshift",
               "/home/fiorenzo/workspace-all/workspace-giavacms/creator/jgit/src/main/resources/deployments",
               "/home/fiorenzo/workspace-all/workspace-giavacms/creator/jgit/src/main/resources/README.md" };
      CreateRepositoryAndCommit.copyFolders(where, "jboss7");
      CreateRepositoryAndCommit.createCommitAndPush(remoteRepository, where, username, repositoryName);

   }
}
