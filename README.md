---
title: Git workshop
date: \today
fontsize: 11pt
monofont: Menlo
mainfont: Avenir
header-includes:
- \usepackage{pandoc-solarized}
- \input{beamer-includes}
---

## Introduction

Workshop goal: get good practices contributing code in team with git and GitHub:

- use core git commands,
- work on branches,
- submit pull requests for review,
- keep a clean history,

You will manipulate on a Scala.js playground.

## Setup your GitHub account

1. Create a GitHub account attached to your society.

2. Update your account avatar, it will be easier for your collaborators to
   identify you.

## Create an SSH key and attach it to your account

1. Create the key `~/.ssh/id_rsa_society` that will be attached to your account:

```bash
ssh-keygen -t rsa -b 4096 -C "mail@society.com"
```

2. Add `society.github.com` host to your ssh config:

```sshconfig
Host society.github.com
  HostName github.com
  IdentityFile ~/.ssh/id_rsa_society
```

3. Attach your public key `~/.ssh/id_rsa_society.pub` to your GitHub account
   from the GitHub interface.

## Setup your git configuration

You are configuring git to use your society email in the folder
`path-to-society-apps/`.

1. Create `path-to-society-apps/.gitconfig`:

```gitconfig
[user]
  email = mail@society.com
```

2. Include `path-to-society-apps/.gitconfig` in `.gitconfig`:

```gitconfig
[includeIf "gitdir:path-to-society-apps/"]
  path = path-to-society-apps/.gitconfig
```

## Fork the repository

1. Fork `zengularity/git-workshop` from the GitHub interface.

2. In the repository settings, under "Merge button" section, uncheck `merge commits` and `rebase merging`, 
   and keep only `squash commit` checked.

3. In branches settings, protect the master branch in the GitHub setting of
   your fork:

- from being forced-pushed,
- from being deleted,
- require 1 review before merging a branch into master.

4. In the collaborators settings, add the person sitting at your left. He will
   review your pull requests.

## Setup the playground

1. Clone the workshop repository with:

```bash
git clone
  git@society.github.com:your-username/git-workshop
```

2. Install [SBT](https://www.scala-sbt.org/).

3. Inside the `playground` directory, launch the playground with:

```bash
sbt "~fastOptJS"
```

4. Open `playground/index.html` on your browser: file:///path/to/git-workshop/playground/index.html

This will display a minimal UI, to manage a To-Do list.

## Get repository information on the CLI

1. List the previous commits with:

```bash
git log # or git log --oneline
```

2. Show the details (diff) of the last commit with:

```bash
git show
```

3. List the local branches:

```bash
git branch
```

4. List all the branches (with remote):

```bash
git branch -a
```

## Add a new feature

You are going to add an arrow whose length is the number of todos.

## Create a branch

Create a branch and move into it with:

```bash
git checkout -b arrow
```

## Add a visualization arrow of the todos

First open the source of the view to be updated: `playground/src/main/scala/playground/view/TodoList.scala`

Above the input field which let you add todos (see `addTodo` in the code), update the template so a `<div>` is added, with an "arrow" `=====>` as textual content.

The goal is to have a number of `=` character in this "arrow" which corresponds to the number of todos (see the `todos` variables in the code).

## Update the TodoList component

1. Initially 2 todos: `==>`
2. A todo is added (3): `===>`
3. 2 todos are removed: `==>`

> See the `ul` element in the code (and the `todoItem` function) to figure out how the reactive list (`Rx`) is done.
> A possible code to add the expected `div` can be as bellow.

```html
<div>{ todos.map(_.map(_ => '=').mkString) }></div>
```

## When you’re done, stage your files

1. Look at the files you have modified with:

```bash
git status
```

2. Look at your unstaged modifications with:

```bash
git diff
```

3. Add files manually or by group with:

```bash
git add file-or-directory # git reset … to undo
```

4. Look at the files staged for the next commit with:

```bash
git status # The files added previously should be listed
```

5. Look at your staged modifications with:

```bash
git diff --staged
```

## Prepare a commit

1. Create a commit for your staged files with:

```bash
git commit
```

2. The command above open an editor which let you write the commit message,
   resume your work by beginning with an action verb, like this:

- Add a login banner on the home page
- Integrate the project detail page
- Fix focus on the input component

3. If you need to, add a description after a blank line.

> If you want to commit files which were already added, `git commit -a` can be used without calling `git add`.

## Push your commit to your repository

1. Push your commit to origin with:

```bash
git push origin arrow
```

2. In the command above, `origin` is the remote URI of your repository. You
   could have other remotes. Check your repository URI with:

```bash
git remote -v
```

## Rename a branch locally and remotely

A developer on the team informs you that you didn’t follow project’s branch
naming conventions:

- feature/user-login
- fix/server-routes
- refactor/todo-model

1. Rename your local branch with:

```bash
git branch -m arrow feature/arrow
```

2. Rename your remote branch with:

```bash
git push -d origin arrow # delete,
git push origin feature/arrow # then create
```

> Note: It's not recommanded to rename a branch after a Pull Request has already been created.

## Improve the arrow

Still on the `feature/arrow` branch, you want to improve the arrow so that it give the information of done todos.

The goal is to colorize in green the number of equals in the arrow corresponding to the number
of completed todos.

The previously added "arrow" `div` can be updated as bellow.

```html
<div>{ todos.map(_.map { item =>
  val color = if (item.isCompleted) "green" else "silver"

  <span style={s"color:$color"}>=</span>
}) }></div>
```

## Test the changes

In order to test this change, update the `val todos` in the code to set `isCompleted = true` for one of the initial items, and then check it's properly displayed in the UI.

Don’t add your files nor make a commit at this point.

## Unexpected demo

You forgot but you have a demo to present. You did not have time to test your
work and prefer to show the previous version. You’ll use the stash feature to
put your current work aside for the moment.

## Stash your files

1. Check that your stash is actually empty with:

```bash
git stash list
```

2. Stash your files with:

```bash
git stash
```

3. Look at the output of:

```bash
git status
```

4. Check that your stash has ben added with:

```bash
git stash list
```

5. Check that your arrow is all in black.

## Unstash your files

The demo was a success!

1. Unstash you files with:

```bash
git stash pop
```

2. Check that your last modified files are back available with:

```bash
git status
```

## Prepare a new commit and push it to origin

1. Let’s say you are using Mac OS X, create `.DS_Store` in the repository with:

```bash
touch .DS_Store
```

2. Using `git status` will indicate this newly created file is untracked.

3. Add the file and commit it.

```bash
git add .DS_Store
git commit
```

4. Push it to origin.

## Remove .DS_Store from your commit

Oops, you committed and pushed .DS_Store!

1. Undo the last commit with:

```bash
git reset --soft HEAD^ # or HEAD~1
```

2. At this point, `git status` indicates that the change about `.DS_Store` is back to be commited.

3. In order to full unstage `.DS_Store`:

```bash
git reset .DS_Store
```

4. Commit back with:

```bash
git commit -c ORIG_HEAD
```

5. Push it to origin with `--force`.

## Gitignore

You want to be sure `.DS_Store` is not committed the next time. You could add
it to either:

- the project `.gitignore`,
- or the global `~/.gitignore`.

Since `.DS_Store` is specific to Mac OS X users and not to the project, you are going to exclude it globally.

```bash
echo ".DS_Store" >> ~/.gitignore
git config --global core.excludesfile '~/.gitignore'
```

> Note: Ignore rules specific to some environment (e.g. according the IDE) could rather be specified in the `.git/info/exclude`

## Prepare a pull request

1. Create a pull request from `feature/arrow` in the GitHub interface. Because
   you have made a fork, the destination is `zengularity/git-workflow`, change
   it to your repository `username/git-workflow`.

2. Give it a title resuming your modification.

3. You can give more information in the description if necessary.

4. Add a *Quality Assurance* (QA) scenario in the description which indicate
   precisely the steps to check if your feature is valid or no.

```
**QA**

1. Go here and add this,
2. you should see that,
3. etc.
```

## Unfortunately, your collaborator is busy

Because you need 1 review, you can’t merge your pull request yet. So, you’re
gonna do another thing: refactor the TodoList component.

## Create a new branch

You’ll refactor changes from another branch, this will lead to another pull
request. This new branch will be based on master, and not on the current
feature branch you have been working on.

```bash
git checkout master
git checkout -b refactor/todo-list
```

> It's recommanded not to create branch based on other previously created feature or task branch.

## Refactor (1/2)

Rename the `val todos` to `items` in the `TodoList` component 
(see `playground/src/main/scala/playground/view/TodoList.scala`).

## End your day and push a WIP commit

At the end of the day, that’s interesting to push your code even if it’s still
WIP.

1. Stage your files on the refactoring (`git add ...`).

2. Create your WIP commit, you don’t bother with the naming.

```bash
git commit -m "WIP"
```

> As the file is already under SCM, `git commit -a -m "WIP"` could be used without prio `git add`

3. Push it to origin.

## Refactor (2/2)

Rename `isCompleted` to `isDone` in the `Todo` model.

## Melt you changes into the WIP commit

1. Stage your files on the refactoring (`git add ...`).

2. Check that the previous commit is the WIP commit before melting your staged
   changes into it.

```bash
git log -n 1 # Must show the WIP commit
```

3. Now that you’re sure that the previous commit is the WIP one, melt your
   changes into it, and modify the commit message too:

```bash
git commit --amend
```

This command can be used even if there is no actual staged modifications, in
order to modify the previous commit message.

## Push to origin and create a pull request

1. Check what `git status` tells you.

2. Try to push your changes to origin with:

```bash
git push origin refactor/todo-list
```

3. Because you have modified the branch history that had already been pushed, you have
   to force your modifications to origin with:

```bash
git push --force origin refactor/todo-list
```

Beware, a force commit is risky, and should not be used outside feature branches.

4. Create a pull request from the GitHub interface, and think about the title
   and the description, which should contains a QA. Don’t forget to target your
   fork and not `zengularity/git-workflow`.

## Review a pull request

Review the **refactor** pull request of the person on your right. Because you
are picky, submit a comment to replace `isDone` by `isFinished` and request
changes.

## Update your pull request

The person on your left requested changes on you pull request. Because you
agree on his comment:

1. Replace `isDone` by `isFinished`.
2. Stage your modified files.
3. Create a fix commit.
4. Push it to your branch.

You are creating a fix commit instead of amending the last one, so that the
reviewer will see only new changes and will not have to review everything back
again.

## Approve the pull request

The person on your right made the changes you requested, you can approve his
pull request.

## Merge your pull request with squash

Now that your pull request is approved:

1. Click on “Squash and merge”.

2. Prepare a well-formed commit message:

- The first line is:
    - a summary of your modification,
    - it begins with an action verb.
- The rest is a detail of your modifications:
    - it can contain a list of modifications begining with `-` or `*`,
    - it can link to un issue with its URL.

3. Merge the pull request and then delete the branch with the provided button.

4. Check that you can still access to every commit of the Pull Request in the GitHub UI (history is kept).

## Get the changes on master on your machine

1. Go to the master branch and get synchronized with origin/master with:

```bash
git checkout master
git pull
```

2. Check that your refactoring is in a single commit on your local master branch (`git log ...`).

3. Delete your feature branch with:

```bash
git branch -d refactor/todo-list
```

## Going back to the visualization arrow

You want to merge your `feature/arrow` branch.

Unfortunately, your feature now has conflicts with the master branch.
You have the choice between rebasing and merging.

## Rebasing vs. merging

You’ll use rebase instead of merge. Instead of having a merge commit, you move
the entire branch to begin on the tip of the branch you rebase from.

See more details [here](https://www.atlassian.com/git/tutorials/merging-vs-rebasing).

That means:

- It can be more complex than merging if you have multiple commits.
- You have to change the history :
   - you risk (when handling conflicts) to introduce unwanted changes while losing the original (valid) changes forever.
- That will lead to a cleaner history.
- You’ll avoid strange and repetitive merge conflicts in case you merge multiple times.

## Rebase and resolve the conflicts

1. Go to the feature branch.

```bash
git checkout ...
```

2. Get the remote changes and rebase from master with:

```bash
git fetch
git rebase origin/master
```

3. All the parts in conflict have been marked on your files, resolve them. You
   can use:

   - your raw editor,
   - your editor merge tool,
   - `git mergetool` (with vim, nvim, meld, …).

## Finalize the rebase

1. Add your modified files.

2. Continue the rebase with:

```bash
git rebase --continue
```

The rebase will continue to be applied on the next commits, but because you have
only one commit, the rebase is now done.

3. Check that the project compiles successfully, and that the visualization
   arrow still works.

4. Push force your branch to origin.

## Pull request validation

You now have time to check the **arrow** pull request of the person on your
right. Whenever your pull request is validated, merge it.

## Being faster with git config aliases

You can add git aliases in `.gitconfig`, for example:

```gitconfig
[alias]
  tree = log --graph --oneline
  p = push origin HEAD
```

## Read the manual

```bash
man git branch
man git rebase
…
```
