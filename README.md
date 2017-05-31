# JGit-Flow Maven Example

This example [Apache Maven](http://maven.apache.org) project shows use of the [JGit-Flow Maven Plugin](https://bitbucket.org/atlassian/jgit-flow/wiki/Home) from [Atlassian](https://www.atlassian.com).  We hope to use this project as a demonstration area for the plugin, documentation source and a place to play with things that could potentially break the plugin so that we can report bugs to make the plugin better.  It's a great plugin for those using Maven and [Git](https://git-scm.com) and we want to help promote use and make it more reliable.

## Setting up the POM

The plugin is extremely simple to use and you don't need to do much in order to get things running.  You will need your SCM data defined in your project's POM file and you will then just need to add the following to your build plugins:

```xml
<plugin>
  <groupId>external.atlassian.jgitflow</groupId>
  <artifactId>jgitflow-maven-plugin</artifactId>
  <version>1.0-m5.1</version>
  <configuration>
    <noDeploy>true</noDeploy>
    <squash>true</squash>
    <scmCommentPrefix>[RELEASE] </scmCommentPrefix>
  </configuration>
</plugin>
```

Here's a quick explanation of the options we put here:

| Option              | Description                                                                                                                                     |
| ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------- |
| noDeploy            | We set this to `true` so that the plugin does not initiate a `mvn deploy` as our CI server will handle that, this then only runs `mvn install`. |
| squash              | Requests that all commits be squashed into a single one before merging.                                                                         |
| scmCommentPrefix    | The prefix to use for all commit messages created in Git.                                                                                       |

Additional documentation for the parameters can be found in the [Common Parameters documentation](https://bitbucket.org/atlassian/jgit-flow/wiki/goals.wiki#!common-parameters) within each goal available for execution.  I only outlined the ones I think are important to set as all the other defaults in the configuration are things we do not deviate from on projects since they keep all of the projects similar and inline with the Git standards that already exist out there (e.g. we don't override the development branch and just leave it named *develop*).

## Performing a Release

### From the Development Branch

> You'll be prompted to enter some data, and that is all up to you.

Performing a release from development is extremely simple using this plugin.  We will only make the assumption here that all of your changes to include in the release have already been merged and pushed and that you will be performing the release in a new clone (think of it as something easily handled by a CI/CD server).  So here's the steps you need to perform:
 
1.  Clone the repository
2.  Checkout the *develop* branch
3.  Start the release (this creates a release branch from the development branch):
```bash
mvn jgitflow:release-start
```
4.  Make any last-minute changes you need (not recommended unless there's an emergency)
5.  Finalize the release (if you look at the Git log after this, you'll see the benefit of the *squash* parameter from before):
```bash
mvn jgitflow:release-finish
```
6.  Optionally squash some more (see the *More Squashing* documentation here) - this doesn't bother me, personally, so I don't do it
7.  Push the development branch, master branch and all the tags
```bash
git push origin develop
git push origin master
git push --tags
```

### More Squashing

Whenever you do a release, everything is rolled into multiple commits.  It may be in your interest to squash the commits even more than what the plugin already does for us.  This is pretty simple to do but requires some extra steps that you can easily automate if you need to.  Here's what we want to do in order to accomplish rolling everything into a single commit from a Git history perspective:

1.  Execute a rebase (the plugin makes 4, so we want to rebase based on 4 commits)
```bash
git rebase -i HEAD~4
```
2.  When your editor opens (or if you're scripting), replace the **last 3** (not the first one) *pick* text in the file with *squash*
3.  Continue with the rest of the deploy after the command completes
